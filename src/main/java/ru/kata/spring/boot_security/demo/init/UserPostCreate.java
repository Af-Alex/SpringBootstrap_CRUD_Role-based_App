package ru.kata.spring.boot_security.demo.init;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@Component
public class UserPostCreate {

    private final UserService userService;
    private final RoleService roleService;
    private final RoleDao roleDao;

    public UserPostCreate(UserService userService, RoleService roleService,
                          RoleDao roleDao) {
        this.userService = userService;
        this.roleService = roleService;
        this.roleDao = roleDao;
    }

    @PostConstruct
    private void postConstruct() {
        roleService.save(new Role(1L, "ADMIN"));
        roleService.save(new Role(2L, "USER"));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleService.findAll().get(1));
        Set<Role> allRoles = new HashSet<>(roleService.findAll());
        userService.saveUser(new User("Alex", "Afanasev",
                             (byte) 25, "alexaf@mail.ru", "admin",
                                      allRoles));
        userService.saveUser(new User("Roman", "Kuzmin",
                                      (byte) 44, "roman@gmail.com", "user",
                                      userRoles));

    }
}
