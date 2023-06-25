package ru.lev.katapproject.service;

import ru.lev.katapproject.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    void save(Role role);

    Optional<Role> findByName(String name);

    Role loadByName(String name);

    List<Role> findAll();
}
