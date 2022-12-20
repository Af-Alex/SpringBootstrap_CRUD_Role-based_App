package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImp implements RoleService {

    private final RoleDao roleDao;

    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    @Transactional
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    @Transactional
    public void setUserRoles(User user){
        user.setRoles(user.getRoles().stream()
                              .map(this::getRole).collect(Collectors.toSet()));
    }
    @Override
    public Role getRole(Role role){
        return roleDao.getById(role.getId());
    }

    @Override
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }
}
