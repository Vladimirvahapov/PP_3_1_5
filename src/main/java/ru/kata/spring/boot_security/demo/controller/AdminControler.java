package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
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
    public String getAllUsers(ModelMap model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user", userService.findByUsername(userDetails.getUsername()));
        model.addAttribute("allusers", userService.findAllWithRoles());
        model.addAttribute("allroles", roleService.getAllRoles());
        model.addAttribute("newUser", new User());
        return "/users-list";
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
    public String update(@ModelAttribute("editedUser") User user, @RequestParam(name = "roles", required = false) List<Integer> roleId) {
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
