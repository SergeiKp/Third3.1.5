package ru.kata.spring.boot_security.demo.Initialization;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


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
        if (roleService.findByName("ROLE_ADMIN") == null) {
            roleService.addRole(new Role("ROLE_ADMIN"));
        }
        if (roleService.findByName("ROLE_USER") == null) {
            roleService.addRole(new Role("ROLE_USER"));
        }

        Role roleUser = roleService.findByName("ROLE_USER");
        Role roleAdmin = roleService.findByName("ROLE_ADMIN");

        if (userService.countUsers() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setFirstName("Jon");
            admin.setLastName("Admin");
            admin.setEmail("admin@example.com");
            admin.setRoles(Set.of(roleAdmin, roleUser));
            userService.addUser(admin);

            User user1 = new User();
            user1.setUsername("will");
            user1.setPassword("will");
            user1.setFirstName("Will");
            user1.setLastName("Silver");
            user1.setEmail("will@example.com");
            user1.setRoles(Set.of(roleAdmin,roleUser));
            userService.addUser(user1);

            User user2 = new User();
            user2.setUsername("rob");
            user2.setPassword("rob");
            user2.setFirstName("Rob");
            user2.setLastName("Diamond");
            user2.setEmail("rob@example.com");
            user2.setRoles(Set.of(roleUser));
            userService.addUser(user2);

            System.out.println("Test users done!");
        }
    }
}


