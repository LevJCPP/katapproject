package ru.lev.katapproject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import ru.lev.katapproject.model.Role;
import ru.lev.katapproject.model.User;
import ru.lev.katapproject.model.UserDTO;
import ru.lev.katapproject.service.RoleService;
import ru.lev.katapproject.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator userValidator;

    @Autowired
    public UserConverter(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    public User toUser(UserDTO dto, BindingResult bindingResult) {
        User user = (dto.getId() != null) ? userService.findById(dto.getId()) : new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setYearOfBirth(dto.getYearOfBirth());
        user.setUsername(dto.getUsername());
        user.setRoles(dto.getRoles().stream().map(roleService::findByName).collect(Collectors.toList()));

        if (!dto.getPassword().isEmpty()) {
            user.setPassword(dto.getPassword());
        }
        userValidator.validate(user, bindingResult);
        return user;
    }

    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setYearOfBirth(user.getYearOfBirth());
        dto.setUsername(user.getUsername());
        dto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return dto;
    }

    public List<UserDTO> toDTO(List<User> userList) {
        return userList.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
