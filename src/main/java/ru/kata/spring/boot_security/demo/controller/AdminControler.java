package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminControler {

    private final UserService userService;
    private RoleService roleService;

    public AdminControler(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String getAllUsers(ModelMap model) {
        model.addAttribute("allusers", userService.getAllUsers());
        return "/users-list";
    }

    @GetMapping(value = "/user-create")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "/user-create";
    }

    @GetMapping(value = "/user-update")
    public String editUser(@RequestParam("userId") Integer userId, Model model) {
        model.addAttribute("user", userService.getUserById(userId));
        model.addAttribute("roles", roleService.getAllRoles());
        return "/user-update";
    }

    @PostMapping(value = "/createUser")
    public String create(@ModelAttribute("user") User user, @RequestParam(name = "roles", required = false) List<Integer> roleId) {
        Set<Role> roleSet = new HashSet<>();
        if (roleId != null) {
            for (Integer role : roleId) {
                roleSet.add(roleService.findRoleById(role));
            }
            user.setUserRoles(roleSet);
        }
        userService.createUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/updateUser")
    public String update(@ModelAttribute("user") User user, @RequestParam(name = "roles", required = false) List<Integer> roleId) {
        Set<Role> roleSet = new HashSet<>();
        if (roleId != null) {
            for (Integer role : roleId) {
                roleSet.add(roleService.findRoleById(role));
            }
            user.setUserRoles(roleSet);
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/delete")
    public String delete(@RequestParam("userId") Integer userId) {
        userService.deleteUserById(userId);
        return "redirect:/admin";
    }
}
