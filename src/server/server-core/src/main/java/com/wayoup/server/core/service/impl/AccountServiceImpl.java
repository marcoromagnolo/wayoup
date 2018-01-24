package com.wayoup.server.core.service.impl;

import com.orientechnologies.orient.core.security.OSecurityManager;
import com.wayoup.server.core.component.Mail;
import com.wayoup.server.core.data.entity.UserAccount;
import com.wayoup.server.core.data.entity.UserAccountRecovery;
import com.wayoup.server.core.data.exception.DataException;
import com.wayoup.server.core.data.repository.AccountRepo;
import com.wayoup.server.core.service.AccountService;
import com.wayoup.server.core.service.dto.AccountDTO;
import com.wayoup.server.core.service.dto.LoginDTO;
import com.wayoup.server.core.service.dto.RegistrationDTO;
import com.wayoup.server.core.service.dto.SessionDTO;
import com.wayoup.server.core.service.exception.AuthorizationException;
import com.wayoup.server.core.service.exception.DbException;
import com.wayoup.server.core.service.exception.GenericException;
import com.wayoup.server.core.service.exception.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wayoup.server.core.enums.ErrorType.*;

/**
 * Created by Marco Romagnolo on 24/03/2015.
 */
@Service("accountService")
public class AccountServiceImpl extends AbstractService implements AccountService {

    private static final Logger logger = Logger.getLogger(AccountServiceImpl.class.getName());

    @Autowired
    private AccountRepo<UserAccount> accountRepo;

    private static final Random RANDOM = new SecureRandom();

    @Autowired
    private Mail mail;

    @Autowired
    private ResourceBundleMessageSource i18n;

    @Value("${service.login.username.maxLenght}")
    private int usernameMaxLenght;

    @Value("${service.login.username.minLenght}")
    private int usernameMinLenght;

    @Value("${service.login.username.pattern}")
    private String usernamePattern;

    @Value("${service.login.password.maxLenght}")
    private int passwordMaxLenght;

    @Value("${service.login.password.minLenght}")
    private int passwordMinLenght;

    @Value("${service.login.password.pattern}")
    private String passwordPattern;

    @Value("${service.login.token.maxLenght}")
    private int tokenMaxLenght;

    @Value("${service.recovery.email.maxLenght}")
    private int emailMaxLenght;

    @Value("${service.recovery.interval}")
    private int recoveryInterval;

    @Value("${service.recovery.tempPassword.lenght}")
    private int temporaryPasswordLenght;

    @Value("${service.recovery.tempPassword.chars}")
    private String recoveryPasswordChars;

    @Value("${service.sendEmail}")
    private boolean sendEmail;

    @Value("${url}")
    private String url;

    @Value("${service.confirm.code.lenght}")
    private int confirmCodeLenght;

    @Value("${service.confirm.code.chars}")
    private String confirmCodeCars;

    @Override
    public LoginDTO login(String username, String password, boolean expiry, String remoteAddress, String headers) throws InternalException, GenericException {
        try {
            if (username == null) throw new GenericException(ERROR_USERNAME_NOTNULL);
            if (password == null) throw new GenericException(ERROR_PASSWORD_NOTNULL);
            UserAccount user = accountRepo.findByUsername(username);
            if (user == null) {
                throw new GenericException(ERROR_USERNAME_PASSWORD_INCORRECT);
            }
            if (user.getConfirmDate() == null) {
                throw new GenericException(ERROR_ACCOUNT_EMAIL_NOTCONFIRMED);
            }
            //Account locked out
            if (user.isBlocked()) {
                throw new GenericException(ERROR_ACCOUNT_LOCKED);
            }
            if (!hash(password).equals(user.getPassword())) {
                throw new GenericException(ERROR_USERNAME_PASSWORD_INCORRECT);
            }
            SessionDTO sessionDTO = createSession(user, expiry, remoteAddress, headers);
            return new LoginDTO(sessionDTO, new AccountDTO(user));
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            accountRepo.close();
        }
    }

    @Override
    public void logout(String username, String token, String remoteAddress, String headers) throws AuthorizationException, InternalException, GenericException {
        try {
            UserAccount user = accountRepo.findByUsername(username);
            destroySession(user, token, remoteAddress, headers);
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            accountRepo.close();
        }
    }

    @Override
    public void recovery(String email, Locale locale) throws InternalException, GenericException {
        try {
            if (email == null) {
                throw new GenericException(ERROR_EMAIL_NOTNULL);
            }
            if (email.isEmpty()) {
                throw new GenericException(ERROR_EMAIL_REQUIRED);
            }
            if (email.length() > emailMaxLenght) {
                throw new GenericException(ERROR_EMAIL_MAXLENGHT);
            }
            UserAccount user = accountRepo.findByEmail(email);
            if (user == null) {
                throw new GenericException(ERROR_ACCOUNT_EMAIL_NOTEXISTS);
            }
            UserAccountRecovery recovery = user.getRecovery();
            if (recovery != null) {
                throw new GenericException(ERROR_ALREADY_RECOVERY_REQUEST);
            }
            //Set Recovery
            Date now = new Date();
            recovery = new UserAccountRecovery();
            recovery.setValidDate(new Date(now.getTime() + recoveryInterval));
            recovery.setTempPassword(generateTempPassword());
            recovery.setCreateDate(now);
            user.setRecovery(recovery);
            accountRepo.save(user);
            //Send Mail
            if (sendEmail) {
                String subject = i18n.getMessage("smtp.mail.recovery.subject", null, locale);
                String tempPassword = recovery.getTempPassword();
                String urlReset = url + "/reset";
                String body = i18n.getMessage("smtp.mail.recovery.body",
                        new Object[]{tempPassword, urlReset}, locale);
                try {
                    mail.send(user.getEmail(), subject, body);
                } catch (MailException e) {
                    logger.log(Level.SEVERE, e.getMessage(), e);
                    throw new GenericException(ERROR_SEND_MAIL_ERROR);
                }
            }
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            accountRepo.close();
        }
    }

    @Override
    public void reset(String username, String tempPassword, String password, String remoteAddr, String headers) throws InternalException, GenericException {
        try {
            if (username == null) throw new GenericException(ERROR_USERNAME_NOTNULL);
            if (password == null) throw new GenericException(ERROR_PASSWORD_NOTNULL);
            if (tempPassword == null) throw new GenericException(ERROR_TEMPPASSWORD_NOTNULL);
            // Username check
            Pattern pattern = Pattern.compile(usernamePattern);
            Matcher matcher = pattern.matcher(username);
            if (!matcher.matches()) {
                throw new GenericException(ERROR_USERNAME_REGEX);
            }
            if (username.length() > usernameMaxLenght) {
                throw new GenericException(ERROR_USERNAME_MAXLENGHT);
            }
            // Password check
            pattern = Pattern.compile(passwordPattern);
            matcher = pattern.matcher(password);
            if (!matcher.matches()) {
                throw new GenericException(ERROR_PASSWORD_REGEX);
            }
            if (password.length() > passwordMaxLenght) {
                throw new GenericException(ERROR_PASSWORD_MAXLENGHT);
            }
            // Temporary Password check
            pattern = Pattern.compile(passwordPattern);
            matcher = pattern.matcher(password);
            if (!matcher.matches()) {
                throw new GenericException(ERROR_TEMPPASSWORD_REGEX);
            }
            if (tempPassword.length() > temporaryPasswordLenght) {
                throw new GenericException(ERROR_TEMPPASSWORD_MAXLENGHT);
            }
            //Find By Username
            UserAccount user = accountRepo.findByUsername(username);
            if (user == null) {
                throw new GenericException(ERROR_USERNAME_PASSWORD_INCORRECT);
            }
            //Account locked out
            if (user.isBlocked()) {
                throw new GenericException(ERROR_ACCOUNT_LOCKED);
            }
            if (user.getRecovery() == null) {
                throw new GenericException(ERROR_NO_RECOVERY_REQUEST);
            }
            if (user.getRecovery().getValidDate().before(new Date())) {
                user.setRecovery(null);
                accountRepo.save(user);
                throw new GenericException(ERROR_TEMPPASSWORD_EXPIRED);
            }
            if (!user.getRecovery().getTempPassword().equals(tempPassword)) {
                throw new GenericException(ERROR_USERNAME_PASSWORD_INCORRECT);
            }
            user.setRecovery(null);
            user.setPassword(hash(password));
            accountRepo.save(user);
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            accountRepo.close();
        }
    }

    @Override
    public RegistrationDTO register(Locale locale, String firstName, String lastName, String email, String username, String password) throws InternalException, GenericException {
        logger.log(Level.FINE, "locale:" + locale + ", firstName:" + firstName + ", lastName:" + lastName + ", email:"
                + email + ", username:" + username + ", password:" + password);
        try {
            if (firstName == null) {
                throw new GenericException(ERROR_FIRSTNAME_NOTNULL);
            }
            if (lastName == null) {
                throw new GenericException(ERROR_LASTNAME_NOTNULL);
            }
            if (email == null) {
                throw new GenericException(ERROR_EMAIL_NOTNULL);
            }
            if (username == null) {
                throw new GenericException(ERROR_USERNAME_NOTNULL);
            }
            if (password == null) {
                throw new GenericException(ERROR_PASSWORD_NOTNULL);
            }
            if (accountRepo.findByEmail(email) != null) {
                throw new GenericException(ERROR_EMAIL_EXISTS);
            }
            if (accountRepo.findByUsername(username) != null) {
                throw new GenericException(ERROR_USERNAME_EXISTS);
            }
            UserAccount user = new UserAccount();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(hash(password));
            user.setConfirmCode(generateConfirmCode());
            user.setConfirmDate(null);
            user.setBlocked(false);
            user.setCreateDate(new Date());
            accountRepo.save(user);
            if (sendEmail) {
                // Send Email
                String subject = i18n.getMessage("smtp.mail.confirm.subject", null, locale);
                String body = i18n.getMessage("smtp.mail.confirm.body",
                        new Object[]{user.getFirstName(), user.getConfirmCode(), user.getEmail(), url}, locale);
                mail.send(user.getEmail(), subject, body);
            }
            return new RegistrationDTO(user);
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            accountRepo.close();
        }
    }

    @Override
    public void confirm(String email, String code) throws GenericException, InternalException {
        try {
            if (email == null) {
                throw new GenericException(ERROR_EMAIL_NOTNULL);
            }
            if (code == null) {
                throw new GenericException(ERROR_CODE_NOTNULL);
            }
            UserAccount user = accountRepo.findByEmail(email);
            if (user == null) {
                throw new GenericException(ERROR_ACCOUNT_NOTEXISTS);
            }
            if (code.equals(user.getConfirmCode())) {
                user.setConfirmDate(new Date());
                accountRepo.save(user);
            } else {
                throw new GenericException(ERROR_ACCOUNT_NOTCONFIRMED);
            }
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            accountRepo.close();
        }
    }

    @Override
    public void verifyEmail(String email) throws InternalException, GenericException {
        try {
            if (accountRepo.findByEmail(email) != null) {
                throw new GenericException(ERROR_EMAIL_EXISTS);
            }
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        }
    }

    @Override
    public void verifyUsername(String username) throws InternalException, GenericException {
        try {
            if (accountRepo.findByUsername(username) != null) {
                throw new GenericException(ERROR_USERNAME_EXISTS);
            }
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        }
    }

    private String generateConfirmCode() {
        String code = "";
        for (int i=0; i< confirmCodeLenght; i++)
        {
            int index = (int)(RANDOM.nextDouble()*confirmCodeCars.length());
            code += confirmCodeCars.substring(index, index + 1);
        }
        return code;
    }

    private String generateTempPassword() {
        String password = "";
        for (int i=0; i< temporaryPasswordLenght; i++)
        {
            int index = (int)(RANDOM.nextDouble()*recoveryPasswordChars.length());
            password += recoveryPasswordChars.substring(index, index + 1);
        }
        return password;
    }

    private String hash(String password) throws GenericException{
        try {
            return OSecurityManager.digest2String(password, "SHA-256");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new GenericException(ERROR_PASSWORD_HASH);
        }
    }

}
