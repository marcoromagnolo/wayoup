package com.wayoup.server.core.data.repository;

import com.wayoup.server.core.data.exception.DataException;

import java.util.List;

/**
 * Created by Marco on 03/06/2015.
 */
public interface OrientRepo<T> {

    void begin() throws DataException;

    void commit() throws DataException;

    void rollback() throws DataException;

    void close();

    T save(T object) throws DataException;

    void delete(T object) throws DataException;

    List<Object> findAll() throws DataException;

    T find(String id) throws DataException;

    T toEntity(Object object) throws DataException;

}
