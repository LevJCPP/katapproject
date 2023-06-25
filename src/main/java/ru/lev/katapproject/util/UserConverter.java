package ru.lev.katapproject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.lev.katapproject.model.*;
import ru.lev.katapproject.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    private final RoleService roleService;

    @Autowired
    public UserConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    public User toUser(UserCreateDTO dto) {
        User user = toUser((UserDTO) dto);
        user.setPassword(dto.getPassword());
        return user;
    }

    public User toUser(UserUpdateDTO dto) {
        User user = toUser((UserDTO) dto);
        user.setId(dto.getId());
        user.setPassword(dto.getPassword());
        return user;
    }

    public User toUser(UserDTO dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setYearOfBirth(dto.getYearOfBirth());
        user.setUsername(dto.getUsername());
        user.setRoles(dto.getRoles().stream().map(roleService::loadByName).collect(Collectors.toList()));
        return user;
    }

    public UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setYearOfBirth(user.getYearOfBirth());
        dto.setUsername(user.getUsername());
        dto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return dto;
    }

    public List<UserResponseDTO> toDTO(List<User> userList) {
        return userList.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
