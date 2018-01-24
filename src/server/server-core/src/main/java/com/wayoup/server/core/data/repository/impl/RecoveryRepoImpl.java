package com.wayoup.server.core.data.repository.impl;

import com.wayoup.server.core.data.repository.impl.AbstractOrientRepo;
import com.wayoup.server.core.data.entity.UserAccountRecovery;
import com.wayoup.server.core.data.repository.RecoveryRepo;
import org.springframework.stereotype.Repository;

/**
 * Created by Marco on 03/06/2015.
 */
@Repository("recoveryRepo")
public class RecoveryRepoImpl extends AbstractOrientRepo<UserAccountRecovery> implements RecoveryRepo<UserAccountRecovery> {

}
