package com.wayoup.server.core.data.repository.impl;

import com.orientechnologies.common.exception.OException;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.wayoup.server.core.data.entity.UserExtAccount;
import com.wayoup.server.core.data.entity.UserFacebook;
import com.wayoup.server.core.data.entity.UserTwitter;
import com.wayoup.server.core.data.exception.DataException;
import com.wayoup.server.core.data.exception.TooManyResultsException;
import com.wayoup.server.core.data.repository.FacebookAccountRepo;
import com.wayoup.server.core.data.repository.TwitterAccountRepo;
import com.wayoup.server.core.enums.ExtAccountType;
import com.wayoup.server.core.service.AccountService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Marco on 06/06/2015.
 */
@Repository("twitterAccountRepo")
public class TwitterAccountRepoImpl extends AbstractOrientRepo<UserTwitter> implements TwitterAccountRepo<UserTwitter> {

    private static final Logger logger = Logger.getLogger(AccountService.class.getName());

    @Override
    public UserTwitter findByExtId(String extId) throws DataException {
        try {
            String query = "select from " + UserTwitter.class.getSimpleName() + " where extId=? limit 1";
            List<ODocument> list = db.query(new OSQLSynchQuery<ODocument>(query), extId);
            if (list.size() > 1) {
                throw new TooManyResultsException();
            }
            return list.isEmpty() ? null : toEntity(list.get(0));
        } catch (OException e) {
            throw new DataException(e);
        }
    }

}
