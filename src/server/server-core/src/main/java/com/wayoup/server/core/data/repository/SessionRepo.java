package com.wayoup.server.core.data.repository;

import com.wayoup.server.core.data.entity.User;
import com.wayoup.server.core.data.exception.DataException;

import java.util.List;

/**
 * Created by Marco Romagnolo on 24/03/2015.
 */
public interface SessionRepo<T> extends OrientRepo<T> {

    List<T> findByUser(User user) throws DataException;

    void deleteByToken(String token) throws DataException;
}
