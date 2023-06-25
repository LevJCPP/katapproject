package ru.lev.katapproject.service;

import ru.lev.katapproject.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void create(User user);

    User findById(Long id);

    List<User> findAll();

    Optional<User> findByUsername(String username);

    void update(User user);

    void deleteById(Long id);
}
