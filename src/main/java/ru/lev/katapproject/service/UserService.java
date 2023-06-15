package ru.lev.katapproject.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.lev.katapproject.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    void save(User user);

    User findById(Long id);

    List<User> findAll();

    Optional<User> findByUsername(String username);

    void deleteById(Long id);

    @Override
    User loadUserByUsername(String username);
}
