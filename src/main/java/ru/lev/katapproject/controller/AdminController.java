package ru.lev.katapproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.lev.katapproject.model.Role;
import ru.lev.katapproject.model.User;
import ru.lev.katapproject.model.UserDTO;
import ru.lev.katapproject.service.RoleService;
import ru.lev.katapproject.service.UserService;
import ru.lev.katapproject.util.UserConverter;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final UserConverter userConverter;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, UserConverter userConverter, RoleService roleService) {
        this.userService = userService;
        this.userConverter = userConverter;
        this.roleService = roleService;
    }

    @GetMapping()
    public ResponseEntity<List<Role>> getRoles() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<List<FieldError>> addUser(@RequestBody UserDTO dto, BindingResult bindingResult) {
        User user = userConverter.toUser(dto, bindingResult);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.NOT_ACCEPTABLE);
        }

        userService.save(user);
        return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<List<FieldError>> editUser(@RequestBody UserDTO dto, BindingResult bindingResult) {
        return addUser(dto, bindingResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
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
