package com.wayoup.server.core.service.dto;

import com.wayoup.server.core.data.entity.UserAccount;

/**
 * Created by Marco on 07/06/2015.
 */
public class AccountDTO extends UserDTO {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String username;

    public AccountDTO(UserAccount userAccount) {
        super(userAccount);
        this.firstName = userAccount.getFirstName();
        this.lastName = userAccount.getLastName();
        this.email = userAccount.getEmail();
        this.username = userAccount.getUsername();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}
