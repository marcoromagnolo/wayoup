package com.wayoup.server.core.service;

import com.wayoup.server.core.service.dto.UserDTO;
import com.wayoup.server.core.service.exception.AuthorizationException;
import com.wayoup.server.core.service.exception.DbException;
import com.wayoup.server.core.service.exception.GenericException;
import com.wayoup.server.core.service.exception.InternalException;

import java.util.Date;
import java.util.List;

/**
 * Created by Marco Romagnolo on 21/03/2015.
 */
public interface UserService {

    public List<UserDTO> getUsers(String token) throws AuthorizationException, InternalException;

    UserDTO getUser(String userId, String token) throws GenericException, AuthorizationException, InternalException;

    void saveUser(String userId, String token, String firstName, String lastName, String email, Date birthday, String phone) throws GenericException, AuthorizationException, InternalException;

    void changePassword(String userId, String token, String password) throws GenericException, AuthorizationException, InternalException;
}
