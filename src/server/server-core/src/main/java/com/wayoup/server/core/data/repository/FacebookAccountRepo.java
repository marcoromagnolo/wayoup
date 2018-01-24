package com.wayoup.server.core.data.repository;

import com.wayoup.server.core.data.entity.UserFacebook;
import com.wayoup.server.core.data.exception.DataException;

/**
 * Created by Marco on 06/06/2015.
 */
public interface FacebookAccountRepo<T> extends OrientRepo<T> {

    UserFacebook findByExtId(String id) throws DataException;

}
