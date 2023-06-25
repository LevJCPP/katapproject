package ru.lev.katapproject.model;

public class UserCreateDTO extends UserDTO {

    private String password;

    public UserCreateDTO() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
