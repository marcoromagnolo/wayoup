package com.wayoup.server.core.service.dto;

/**
 * Created by Marco on 07/06/2015.
 */
public class LoginDTO {

    private SessionDTO session;
    private AccountDTO account;
    private ExtAccountDTO extAccount;

    public LoginDTO(SessionDTO sessionDTO, AccountDTO accountDTO) {
        this.session = sessionDTO;
        this.account = accountDTO;
    }

    public LoginDTO(SessionDTO sessionDTO, ExtAccountDTO extAccountDTO) {
        this.session = sessionDTO;
        this.extAccount = extAccountDTO;
    }

    public SessionDTO getSession() {
        return session;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public ExtAccountDTO getExtAccount() {
        return extAccount;
    }
}
