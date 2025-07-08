package ru.kata.spring.boot_security.demo.Initialization;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class InitUsers {

    private final UserService userService;
    private final RoleService roleService;

    public InitUsers(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
        if (roleService.findByName("ROLE_ADMIN").isEmpty()) {
            roleService.addRole(new Role("ROLE_ADMIN"));
        }
        if (roleService.findByName("ROLE_USER").isEmpty()) {
            roleService.addRole(new Role("ROLE_USER"));
        }

        Optional<Role> roleAdmin = roleService.findByName("ROLE_ADMIN");
        Optional<Role> roleUser = roleService.findByName("ROLE_USER");

        if (userService.countUsers() == 0) {
            Set<Role> adminRoles = new HashSet<>();
            roleAdmin.ifPresent(adminRoles::add);
            roleUser.ifPresent(adminRoles::add);

            Set<Role> userRoles = new HashSet<>();
            roleUser.ifPresent(userRoles::add);

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setFirstName("Jon");
            admin.setLastName("Admin");
            admin.setEmail("admin@example.com");
            admin.setRoles(adminRoles);
            userService.addUser(admin);

            User user1 = new User();
            user1.setUsername("will");
            user1.setPassword("will");
            user1.setFirstName("Will");
            user1.setLastName("Silver");
            user1.setEmail("will@example.com");
            user1.setRoles(adminRoles);
            userService.addUser(user1);

            User user2 = new User();
            user2.setUsername("rob");
            user2.setPassword("rob");
            user2.setFirstName("Rob");
            user2.setLastName("Diamond");
            user2.setEmail("rob@example.com");
            user2.setRoles(userRoles);
            userService.addUser(user2);

            System.out.println("Test users done!");
        }
    }
}


