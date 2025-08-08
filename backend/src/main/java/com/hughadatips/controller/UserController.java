package com.hughadatips.controller;

import com.hughadatips.dto.UserDTO;
import com.hughadatips.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/all")
    public List<UserDTO> getAll() {
        return userService.findAll();
    }
}