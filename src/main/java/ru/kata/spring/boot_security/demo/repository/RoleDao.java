package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleDao {
    List<Role> getRoles();
    Role findById(Long id);
    Role findByName(String name);
    void addRole(Role role);
}
