package ru.lev.katapproject.config;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public DataInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void dataInit() {
        if (roleService.findByName("Admin").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("Admin");
            roleService.save(adminRole);
        }
        if (userService.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setFirstName("admin first");
            admin.setLastName("admin last");
            admin.setYearOfBirth(1950);
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setRoles(List.of(roleService.loadByName("Admin")));
            userService.create(admin);
        }
        if (roleService.findByName("User").isEmpty()) {
            Role userRole = new Role();
            userRole.setName("User");
            roleService.save(userRole);
        }
        if (userService.findByUsername("user").isEmpty()) {
            User user = new User();
            user.setFirstName("user first");
            user.setLastName("user last");
            user.setYearOfBirth(2000);
            user.setUsername("user");
            user.setPassword("user");
            user.setRoles(List.of(roleService.loadByName("User")));
            userService.create(user);
        }
    }
}
