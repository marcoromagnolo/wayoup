package com.wayoup.server.core.data.repository;

import com.wayoup.server.core.data.entity.UserTwitter;
import com.wayoup.server.core.data.exception.DataException;

/**
 * Created by Marco on 06/06/2015.
 */
public interface TwitterAccountRepo<T> extends OrientRepo<T> {

    UserTwitter findByExtId(String id) throws DataException;

}
