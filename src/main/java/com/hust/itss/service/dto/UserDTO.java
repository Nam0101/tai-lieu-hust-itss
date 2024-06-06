package com.hust.itss.service.dto;

import com.hust.itss.domain.User;
import java.io.Serializable;

/**
 * A DTO representing a user, with only the public attributes.
 */
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String login;

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(User user) {
        this.id = user.getId();
        // Customize it here if you need, or not, firstName/lastName/etc
        this.login = user.getLogin();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDTO{" +
            "id='" + id + '\'' +
            ", login='" + login + '\'' +
            "}";
    }

    public Long getId() {
        return this.id;
    }

    public String getLogin() {
        return this.login;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
