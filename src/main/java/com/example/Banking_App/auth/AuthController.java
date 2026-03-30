package com.example.Banking_App.auth;


import com.example.Banking_App.dto.LoginRequestDto;
import com.example.Banking_App.dto.LoginResponseDto;
import com.example.Banking_App.entity.User;
import com.example.Banking_App.repository.UserRepository;
import com.example.Banking_App.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {

        User user = authService.authenticate(
                request.getUsername(),
                request.getPassword()
        );

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole()
        );

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}
