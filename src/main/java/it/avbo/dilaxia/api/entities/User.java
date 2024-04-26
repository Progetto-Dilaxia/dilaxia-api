package it.avbo.dilaxia.api.entities;

import it.avbo.dilaxia.api.entities.enums.UserRole;

import java.sql.Date;

public class User {
    private final String username;
    private final String email;
    private final char sex;
    private final Date birthday;
    private final UserRole role;
    private final byte[] passwordHash;
    private final byte[] salt;

    public User(String username, String email, char sex, Date birthday, UserRole role, byte[] passwordHash, byte[] salt) {
        this.username = username;
        this.email = email;
        this.sex = sex;
        this.birthday = birthday;
        this.role = role;
        this.passwordHash = passwordHash;
        this.salt = salt;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public char getSex() {
        return sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public UserRole getRole() {
        return role;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public byte[] getSalt() {
        return salt;
    }
}
