package com.wayoup.server.core.data.repository.impl;

import com.wayoup.server.core.data.entity.User;
import com.wayoup.server.core.data.repository.UserRepo;
import com.wayoup.server.core.data.repository.impl.AbstractOrientRepo;
import org.springframework.stereotype.Repository;

/**
 * Created by Marco on 03/06/2015.
 */
@Repository("userRepo")
public class UserRepoImpl extends AbstractOrientRepo<User> implements UserRepo<User> {

}
