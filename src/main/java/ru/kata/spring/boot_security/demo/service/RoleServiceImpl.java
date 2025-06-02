package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleDao;

import java.util.List;
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
    public Role findById(Long id) {
        return roleDao.findById(id);
    }

    @Override
    @Transactional
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }

    @Override
    public void addRole(Role role) {
    roleDao.addRole(role);
    }
}
