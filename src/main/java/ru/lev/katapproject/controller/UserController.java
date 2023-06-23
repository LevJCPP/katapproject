package ru.lev.katapproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lev.katapproject.model.User;
import ru.lev.katapproject.model.UserDTO;
import ru.lev.katapproject.service.UserService;
import ru.lev.katapproject.util.UserConverter;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;

    @Autowired
    public UserController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping("/user")
    public UserDTO getAuthUser(Principal principal) {
        return userConverter.toDTO(userService.findByUsername(principal.getName()).orElse(new User()));
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userConverter.toDTO(userService.findAll());
    }

    @GetMapping("/user/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userConverter.toDTO(userService.findById(id));
    }
}
