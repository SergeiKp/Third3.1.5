package ru.kata.spring.boot_security.demo.repository;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
   void addUser(User user);
   void updateUser(User user);
   void deleteUser(User user);
   List<User> getAllUsers();
   User getById(long id);
   long countUsers();
   User findByUsername(String username);
}
