package ru.lev.katapproject.model;

public class UserResponseDTO extends UserDTO {

    private Long id;

    public UserResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
