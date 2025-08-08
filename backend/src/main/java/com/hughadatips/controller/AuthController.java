package com.hughadatips.controller;

import com.hughadatips.dto.UserDTO;
import com.hughadatips.entity.User;
import com.hughadatips.service.LoginHistoryService;
import com.hughadatips.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    private LoginHistoryService loginHistoryService;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        User user = userService.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Utilisateur non trouvé"));

        loginHistoryService.recordLogin(user);   //  << history saved

        return ResponseEntity.ok(userService.toDTO(user));
    }


    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterRequest request) {
        User user = User.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .password(request.getPassword())
                .telephone(request.getTelephone())
                .role(User.ROLE.CLIENT)
                .build();
        UserDTO dto = userService.register(user);
        return ResponseEntity.ok(dto);
    }



    @Data
    public static class RegisterRequest {
        private String nom;
        private String prenom;
        private String email;
        private String password;
        private String telephone;
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }
}
