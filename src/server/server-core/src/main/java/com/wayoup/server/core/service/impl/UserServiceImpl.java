package com.wayoup.server.core.service.impl;

import com.wayoup.server.core.data.entity.User;
import com.wayoup.server.core.data.entity.UserAccount;
import com.wayoup.server.core.data.exception.DataException;
import com.wayoup.server.core.data.repository.AccountRepo;
import com.wayoup.server.core.data.repository.UserRepo;
import com.wayoup.server.core.service.UserService;
import com.wayoup.server.core.service.dto.UserDTO;
import com.wayoup.server.core.service.exception.AuthorizationException;
import com.wayoup.server.core.service.exception.DbException;
import com.wayoup.server.core.service.exception.GenericException;
import com.wayoup.server.core.service.exception.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.wayoup.server.core.enums.ErrorType.ERROR_USERID_NUMBER_FORMAT;

/**
 * Created by Marco Romagnolo on 21/03/2015.
 */
@Service("userService")
public class UserServiceImpl extends AbstractService implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired
    private UserRepo<User> userRepo;

    @Autowired
    private AccountRepo<UserAccount> accountRepo;

    @Override
    public List<UserDTO> getUsers(String token) throws AuthorizationException, InternalException {
        try {
            getSession(token);
            List<Object> users = userRepo.findAll();
            List<UserDTO> list = new ArrayList<>();
            for (Object object : users) {
                User user = userRepo.toEntity(object);
                list.add(new UserDTO(user));
            }
            return list;
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            userRepo.close();
        }
    }

    @Override
    public UserDTO getUser(String userId, String token) throws GenericException, AuthorizationException, InternalException {
        try {
            getSession(token);
            User user = userRepo.find(userId);
            return new UserDTO(user);
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new GenericException(ERROR_USERID_NUMBER_FORMAT);
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            userRepo.close();
        }
    }

    @Override
    public void saveUser(String userId, String token, String firstName, String lastName, String email, Date birthday, String phone) throws GenericException, AuthorizationException, InternalException {
        try {
            getSession(token);
            UserAccount user = accountRepo.find(userId);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setBirthday(birthday);
            user.setPhone(phone);
            accountRepo.save(user);
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new GenericException(ERROR_USERID_NUMBER_FORMAT);
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            accountRepo.close();
        }
    }

    @Override
    public void changePassword(String userId, String token, String password) throws GenericException, AuthorizationException, InternalException {
        try {
            getSession(token);
            UserAccount user = accountRepo.find(userId);
            user.setPassword(password);
            userRepo.save(user);
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new GenericException(ERROR_USERID_NUMBER_FORMAT);
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            accountRepo.close();
        }
    }


}
