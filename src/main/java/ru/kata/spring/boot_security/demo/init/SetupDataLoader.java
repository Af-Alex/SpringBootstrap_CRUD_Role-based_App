package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;


@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        Role adminRole = createRoleIfNotFound("ADMIN");
        Role userRole = createRoleIfNotFound("USER");

        Set<Role> allRoles = new HashSet<>();
        allRoles.add(adminRole);
        allRoles.add(userRole);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);



        User admin = new User();
        admin.setFirstName("Alex");
        admin.setLastName("Afanasev");
        admin.setAge((byte) 25);
        admin.setUsername("admin@123");
        admin.setPassword("admin");
        admin.setRoles(allRoles);

        User user = new User();
        user.setFirstName("Roman");
        user.setLastName("Kuzmin");
        user.setAge((byte) 44);
        user.setUsername("user@123");
        user.setPassword("user");
        user.setRoles(userRoles);

        userService.saveUser(admin);
        userService.saveUser(user);

        alreadySetup = true;
    }

    @Transactional
    Role createRoleIfNotFound(String name) {

        Role role = roleService.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleService.save(role);
        }
        return role;
    }
}
