package ru.lev.katapproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.lev.katapproject.model.*;
import ru.lev.katapproject.service.RoleService;
import ru.lev.katapproject.service.UserService;
import ru.lev.katapproject.util.UserConverter;
import ru.lev.katapproject.util.UserValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final UserConverter userConverter;
    private final UserValidator userValidator;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, UserConverter userConverter, UserValidator userValidator, RoleService roleService) {
        this.userService = userService;
        this.userConverter = userConverter;
        this.userValidator = userValidator;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getRoles() {
        return new ResponseEntity<>(roleService.findAll().stream().map(Role::getName).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<FieldError>> addUser(@RequestBody UserCreateDTO dto, BindingResult bindingResult) {
        User user = userConverter.toUser(dto);
        userValidator.validateCreate(user, bindingResult);
        userService.create(user);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<List<FieldError>> editUser(@RequestBody UserUpdateDTO dto, BindingResult bindingResult) {
        User user = userConverter.toUser(dto);
        userValidator.validateUpdate(user, bindingResult);
        userService.update(user);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/users")
    public List<UserResponseDTO> getAllUsers() {
        return userConverter.toDTO(userService.findAll());
    }

    @GetMapping("/user/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return userConverter.toDTO(userService.findById(id));
    }
}
