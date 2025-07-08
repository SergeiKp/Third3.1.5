package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleDao {
    List<Role> getRoles();
    Optional<Role> findById(Long id);
    Optional<Role> findByName(String name);
    void addRole(Role role);
}
