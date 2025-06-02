package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/admin-page";
    }

    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getRoles());
        return "admin/user-form";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam("roles") List<Long> roleIds) {
        userService.addUserWithRoles(user, roleIds);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("roles", roleService.getRoles());
        return "admin/user-form";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam("roles") List<Long> roleIds) {
        userService.updateUserWithRoles(user, roleIds);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(userService.getById(id));
        return "redirect:/admin";
    }
}
