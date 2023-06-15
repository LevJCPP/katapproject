package ru.lev.katapproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.lev.katapproject.service.RoleService;
import ru.lev.katapproject.service.UserService;

import java.security.Principal;

@Controller
public class LoginController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public LoginController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String homePage(Model model, Principal authUser) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("authUser", userService.findByUsername(authUser.getName()).orElse(null));
        return "home";
    }
}
