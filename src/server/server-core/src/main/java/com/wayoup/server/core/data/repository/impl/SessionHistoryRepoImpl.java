package com.wayoup.server.core.data.repository.impl;

import com.orientechnologies.common.exception.OException;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.wayoup.server.core.data.entity.Session;
import com.wayoup.server.core.data.entity.SessionHistory;
import com.wayoup.server.core.data.exception.DataException;
import com.wayoup.server.core.data.exception.TooManyResultsException;
import com.wayoup.server.core.data.repository.impl.AbstractOrientRepo;
import com.wayoup.server.core.data.repository.SessionHistoryRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Marco on 03/06/2015.
 */
@Repository("sessionHistoryRepo")
public class SessionHistoryRepoImpl  extends AbstractOrientRepo<SessionHistory> implements SessionHistoryRepo<SessionHistory> {

    @Override
    public SessionHistory findByToken(String token) throws DataException {
        try {
            String query = "select from " + SessionHistory.class.getSimpleName() + " where token=?";
            List<ODocument> list = db.query(new OSQLSynchQuery<ODocument>(query), token);
            if (list.size()>1) {
                throw new TooManyResultsException();
            }
            return list.isEmpty() ? null : toEntity(list.get(0));
        } catch (OException e) {
            throw new DataException(e);
        }
    }
}
