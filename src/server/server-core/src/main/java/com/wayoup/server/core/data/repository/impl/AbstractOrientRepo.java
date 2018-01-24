package com.wayoup.server.core.data.repository.impl;

import com.orientechnologies.common.exception.OException;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.wayoup.server.core.component.OrientDb;
import com.wayoup.server.core.data.entity.AbstractEntity;
import com.wayoup.server.core.data.exception.DataException;
import com.wayoup.server.core.data.repository.OrientRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Marco on 03/06/2015.
 */
@Component
public abstract class AbstractOrientRepo<T> implements OrientRepo<T> {

    private static final Logger logger = Logger.getLogger(AbstractOrientRepo.class.getName());

    @Value("${db.address}")
    private String address;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;

    private OrientGraphFactory factory;
    protected ODatabaseDocumentTx db;
    protected OrientGraphNoTx graph;
    protected OrientGraph txGraph;
    private Class<T> clazz;
    private String className;

    @PostConstruct
    public void init() {
        factory = OrientDb.getFactory(address, username, password);
        db = factory.getDatabase(false, true);
        graph = factory.getNoTx();
        txGraph = factory.getTx();
        clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        className = clazz.getSimpleName();
    }

    @Override
    public void begin() throws DataException {
        try {
            db.begin();
        } catch (OException e) {
            throw new DataException(e);
        }
    }

    @Override
    public void commit() throws DataException {
        try {
            db.commit();
        } catch (OException e) {
            throw new DataException(e);
        }
    }

    @Override
    public void rollback() throws DataException {
        try{
            db.rollback();
        } catch (OException e) {
            throw new DataException(e);
        }
    }

    @Override
    public void close() {
        factory.close();
    }

    @Override
    public T save(T entity) throws DataException {
        try {
            ODocument doc = toODocument(entity);
            return toEntity(db.save(doc));
        } catch (OException e) {
            throw new DataException(e);
        }
    }

    @Override
    public void delete(T entity) throws DataException {
        try {
            String id = ((AbstractEntity) entity).getId();
            db.delete(new ORecordId(id));
        } catch (OException e) {
            throw new DataException(e);
        }
    }

    @Override
    public List<Object> findAll() throws DataException {
        try {
            String query = "select from " + className;
            return db.query(new OSQLSynchQuery<T>(query));
        } catch (OException e) {
            throw new DataException(e);
        }
    }

    @Override
    public T find(String id) throws DataException {
        return toEntity(get(id));
    }

    private ODocument get(String id) {
        OIdentifiable rec = db.load(new ORecordId(id));
        return rec==null ? null : (ODocument) rec.getRecord();
    }

    private Class<?> getEntityClass(ODocument object) throws ClassNotFoundException {
        return Class.forName("com.wayoup.server.core.data.entity." + object.getClassName());
    }

    public T toEntity(Object object) throws DataException {
        return toEntity(object, clazz);
    }

    private <E> E toEntity(Object object, Class<E> clazz) throws DataException {
        ODocument doc = (ODocument) object;
        E entity;
        try {
            entity = clazz.newInstance();
            Class<? super E> superClass = clazz;
            while (superClass!=Object.class) {
                if (superClass==AbstractEntity.class) {
                    Field id = superClass.getDeclaredField("id");
                    id.setAccessible(true);
                    id.set(entity, doc.getIdentity().toString());
                    Field version = superClass.getDeclaredField("version");
                    version.setAccessible(true);
                    version.set(entity, doc.getVersion());
                } else {
                    for (Field field : superClass.getDeclaredFields()) {
                        Object value = doc.field(field.getName());
                        if (value == null) continue;
                        field.setAccessible(true);
                        if (value instanceof ODocument) {
                            field.set(entity, toEntity(value, getEntityClass((ODocument) value)));
                        } else if (value instanceof List) {
                            List<Object> list = new ArrayList<>();
                            for (Object obj : list) {
                                list.add(toEntity(obj, getEntityClass((ODocument) obj)));
                            }
                            field.set(entity, list);
                        } else if (value instanceof Set) {
                            Set<Object> set = new HashSet<>();
                            for (Object obj : set) {
                                set.add(toEntity(obj, getEntityClass((ODocument) obj)));
                            }
                            field.set(entity, set);
                        } else if (value instanceof Map) {
                            Map<String, Object> map = new HashMap<>();
                            for (Map.Entry<String, Object> entry : map.entrySet()) {
                                map.put(entry.getKey(), toEntity(entry.getValue(), getEntityClass((ODocument) entry.getValue())));
                            }
                            field.set(entity, map);
                        } else {
                            field.set(entity, value);
                        }
                    }
                }
                superClass = superClass.getSuperclass();
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchFieldException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataException(e);
        }
        return entity;
    }

    private ODocument toODocument(Object entity) throws DataException {
        ODocument doc;
        String className = entity.getClass().getSimpleName();
        try {
            String id = ((AbstractEntity) entity).getId();
            doc = id==null ? new ODocument(className): get(id);
            List<Field[]> groupFields = new ArrayList<>();
            Class<?> clazz = entity.getClass();
            while (clazz!=Object.class) {
                groupFields.add(clazz.getDeclaredFields());
                clazz = clazz.getSuperclass();
            }
            for (Field[] fields : groupFields) {
                for (Field field : fields) {
                    String key = field.getName();
                    if (key.equals("id") || key.equals("version")) continue;
                    field.setAccessible(true);
                    Object value = field.get(entity);
                    if (value instanceof AbstractEntity) {
                        doc.field(key, toODocument(value));
                    } else if (value instanceof List) {
                        List<ODocument> list = new ArrayList<>();
                        for (AbstractEntity e : (List<AbstractEntity>) value) {
                            list.add(toODocument(e));
                        }
                        doc.field(key, list);
                    } else if (value instanceof Set) {
                        Set<ODocument> set = new HashSet<>();
                        for (AbstractEntity e : (Set<AbstractEntity>) value) {
                            set.add(toODocument(e));
                        }
                        doc.field(key, set);
                    } else if (value instanceof Map) {
                        Map<String, ODocument> map = new HashMap<>();
                        Set<Map.Entry<String, AbstractEntity>> entrySet = ((Map<String, AbstractEntity>) value).entrySet();
                        for (Map.Entry<String, AbstractEntity> entry : entrySet) {
                            map.put(entry.getKey(), toODocument(entry.getValue()));
                        }
                        doc.field(key, map);
                    } else {
                        doc.field(key, value);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataException(e);
        }
        return doc;
    }

}
