package com.wayoup.server.core.data.repository.impl;

import com.orientechnologies.common.exception.OException;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.wayoup.server.core.data.entity.Session;
import com.wayoup.server.core.data.entity.User;
import com.wayoup.server.core.data.entity.UserAccount;
import com.wayoup.server.core.data.exception.DataException;
import com.wayoup.server.core.data.repository.SessionRepo;
import com.wayoup.server.core.data.repository.impl.AbstractOrientRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marco on 03/06/2015.
 */
@Repository("sessionRepo")
public class SessionRepoImpl extends AbstractOrientRepo<Session> implements SessionRepo<Session> {

    @Override
    public List<Session> findByUser(User user) throws DataException {
        try {
            String query = "select from " + Session.class.getSimpleName() + " where user=?";
            List<Session> list = db.query(new OSQLSynchQuery<ODocument>(query), user);
            return list;
        } catch (OException e) {
            throw new DataException(e);
        }
    }

    @Override
    public void deleteByToken(String token) throws DataException {
        try {
            String query = "delete from " + Session.class.getSimpleName() + " where token=?";
            db.command(new OCommandSQL(query)).execute(token);
        } catch (OException e) {
            throw new DataException(e);
        }
    }
}
