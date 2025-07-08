package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleDao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService{
    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public List<Role> getRoles() {
        return roleDao.getRoles();
    }

    @Override
    @Transactional
    public Optional<Role> findById(Long id) {
        return roleDao.findById(id);
    }

    @Override
    @Transactional
    public Optional<Role> findByName(String name) {
        return roleDao.findByName(name);
    }

    @Override
    public void addRole(Role role) {
    roleDao.addRole(role);
    }


    @Override
    public Set<Role> resolveRoles(Set<Role> roles) {
        return roles.stream()
                .map(r -> findById(r.getId()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}
