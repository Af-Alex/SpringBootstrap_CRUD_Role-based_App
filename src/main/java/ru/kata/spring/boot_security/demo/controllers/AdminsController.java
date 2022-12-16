package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping( "/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminsController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminsController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showUsersList(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("usersList", userService.findAllUsers());
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("roles", userDetails.getAuthorities());
        return "admin";
    }

    @GetMapping("/new")
    public String showNewUserForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", new User());
        return "/new";
    }

    @PostMapping ("/new")
    public String saveNewUser(@ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/new";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping ("/edit/{id}")
    public String updateUser(@ModelAttribute("user") User user, BindingResult result) {

        if (result.hasErrors()) {
            return "redirect:/edit/{id}";
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
