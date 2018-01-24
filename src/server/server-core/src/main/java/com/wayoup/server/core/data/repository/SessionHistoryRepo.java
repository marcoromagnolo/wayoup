package com.wayoup.server.core.data.repository;

import com.wayoup.server.core.data.exception.DataException;

import java.util.Date;

/**
 * Created by Marco Romagnolo on 20/03/2015.
 */
public interface SessionHistoryRepo<T> extends OrientRepo<T> {

    T findByToken(String token) throws DataException;

}
