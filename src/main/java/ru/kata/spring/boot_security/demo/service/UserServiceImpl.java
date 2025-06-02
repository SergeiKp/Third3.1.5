package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserDao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getById(long id) {
        return userDao.getById(id);
    }

    @Override
    public long countUsers() {
        return userDao.countUsers();
    }

    @Override
    public User findUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        user.getRoles().size();
        return user;
    }

    @Override
    @Transactional
    public void addUserWithRoles(User user, List<Long> roleIds) {
        Set<Role> roles = getRolesFromIds(roleIds);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
    }

    @Override
    @Transactional
    public void updateUserWithRoles(User user, List<Long> roleIds) {
        Set<Role> roles = getRolesFromIds(roleIds);
        user.setRoles(roles);

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            User existingUser = userDao.getById(user.getId());
            user.setPassword(existingUser.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userDao.updateUser(user);
    }



    private Set<Role> getRolesFromIds(List<Long> roleIds) {
        Set<Role> roles = new HashSet<>();
        for (Long id : roleIds) {
            Role role = roleService.findById(id);
            if (role != null) roles.add(role);
        }
        return roles;
    }
}
