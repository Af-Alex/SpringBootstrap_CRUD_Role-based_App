package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/")
@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("user")
    public String showUser(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("currentUser", userService.findByUsername(userDetails.getUsername()));
        model.addAttribute("userDetails", userDetails);
        return "user";
    }
}
