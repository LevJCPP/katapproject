package ru.lev.katapproject.service;

import ru.lev.katapproject.model.Role;

import java.util.List;

public interface RoleService {

    void save(Role role);

    Role findByName(String name);

    List<Role> findAll();
}
