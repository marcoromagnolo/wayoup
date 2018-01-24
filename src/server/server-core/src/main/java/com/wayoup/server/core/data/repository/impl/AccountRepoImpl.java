package com.wayoup.server.core.data.repository.impl;

import com.orientechnologies.common.exception.OException;
import com.orientechnologies.orient.core.query.OQuery;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.wayoup.server.core.data.exception.DataException;
import com.wayoup.server.core.data.exception.TooManyResultsException;
import com.wayoup.server.core.data.repository.AccountRepo;
import com.wayoup.server.core.data.repository.impl.AbstractOrientRepo;
import com.wayoup.server.core.data.entity.UserAccount;
import com.wayoup.server.core.service.AccountService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Marco on 06/06/2015.
 */
@Repository("accountRepo")
public class AccountRepoImpl extends AbstractOrientRepo<UserAccount> implements AccountRepo<UserAccount> {

    private static final Logger logger = Logger.getLogger(AccountService.class.getName());

    @Override
    public UserAccount findByUsername(String username) throws DataException {
        try {
            String query = "select from " + UserAccount.class.getSimpleName() + " where username=? limit 1";
            List<ODocument> list = db.query(new OSQLSynchQuery<ODocument>(query), username);
            if (list.size()>1) {
                throw new TooManyResultsException();
            }
            return list.isEmpty() ? null : toEntity(list.get(0));
        } catch (OException e) {
            throw new DataException(e);
        }
    }

    @Override
    public UserAccount findByEmail(String email) throws DataException {
        try {
            String query = "select from " + UserAccount.class.getSimpleName() + " where email=? limit 1";
            List<ODocument> list = db.query(new OSQLSynchQuery<ODocument>(query), email);
            if (list.size() > 1) {
                throw new TooManyResultsException();
            }
            return list.isEmpty() ? null : toEntity(list.get(0));
        } catch (OException e) {
            throw new DataException(e);
        }
    }

}
