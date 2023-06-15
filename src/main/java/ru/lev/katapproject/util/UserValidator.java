package ru.lev.katapproject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.lev.katapproject.model.User;
import ru.lev.katapproject.service.UserService;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if ((user.getFirstName() == null) || user.getFirstName().isEmpty()) {
            errors.rejectValue("firstName", "", "Required field");
        }

        if ((user.getLastName() == null) || user.getLastName().isEmpty()) {
            errors.rejectValue("lastName", "", "Required field");
        }

        if ((user.getYearOfBirth() == null) || ((user.getYearOfBirth() < 1923) || (user.getYearOfBirth() > 2005))) {
            errors.rejectValue("yearOfBirth", "", "Invalid year of birth");
        }

        if ((user.getUsername() == null) || user.getUsername().isEmpty()) {
            errors.rejectValue("username", "", "Required field");
        } else if (user.getUsername().length() < 4) {
            errors.rejectValue("username", "", "Must be at least 4 characters long");
        } else {
            User savedUser = userService.findByUsername(user.getUsername()).orElse(null);
            if ((savedUser != null) && !savedUser.getId().equals(user.getId())) {
                errors.rejectValue("username", "", "Such username is already taken");
            }
        }

        if ((user.getPassword() == null) || user.getPassword().isEmpty()) {
            errors.rejectValue("password", "", "Required field");
        } else if (user.getPassword().length() < 4) {
            errors.rejectValue("password", "", "Must be at least 4 characters long");
        }

        if ((user.getRoles() == null) || user.getRoles().isEmpty()) {
            errors.rejectValue("roles", "", "Required field");
        }
    }
}
