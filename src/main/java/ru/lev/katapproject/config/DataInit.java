package ru.lev.katapproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.lev.katapproject.model.Role;
import ru.lev.katapproject.model.User;
import ru.lev.katapproject.service.RoleService;
import ru.lev.katapproject.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class DataInit {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInit(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void dataInit() {
        Role adminRole = new Role();
        if (roleService.findByName("Admin") == null) {
            adminRole.setName("Admin");
            roleService.save(adminRole);

            if (userService.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setFirstName("admin first");
                admin.setLastName("admin last");
                admin.setYearOfBirth(1950);
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRoles(List.of(adminRole));
                userService.save(admin);
            }
        }
        Role userRole = new Role();
        if (roleService.findByName("User") == null) {
            userRole.setName("User");
            roleService.save(userRole);

            if (userService.findByUsername("user").isEmpty()) {
                User user = new User();
                user.setFirstName("user first");
                user.setLastName("user last");
                user.setYearOfBirth(2000);
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user"));
                user.setRoles(List.of(userRole));
                userService.save(user);
            }
        }
    }
}
