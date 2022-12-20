package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    void saveUser(User user);

    void updateUser(User updatedUser);

    void deleteById(Long id);

    User findByUsername(String username);
}
