package ru.lev.katapproject.model;

public class UserUpdateDTO extends UserDTO {

    private Long id;
    private String password;

    public UserUpdateDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
