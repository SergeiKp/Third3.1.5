package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleService {
    List<Role> getRoles();
    Optional<Role> findById(Long id);
    Optional<Role> findByName(String name);
    void addRole(Role role);
    Set<Role> resolveRoles(Set<Role> roles);
}
