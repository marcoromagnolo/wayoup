package com.wayoup.server.core.data.repository;

import com.wayoup.server.core.data.entity.UserAccount;
import com.wayoup.server.core.data.exception.DataException;

/**
 * Created by Marco on 06/06/2015.
 */
public interface AccountRepo<T> extends OrientRepo<T> {

    UserAccount findByUsername(String username) throws DataException;

    UserAccount findByEmail(String email) throws DataException;

}
