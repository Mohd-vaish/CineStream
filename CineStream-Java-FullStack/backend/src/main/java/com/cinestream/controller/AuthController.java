package com.cinestream.controller;

import com.cinestream.dto.*;
import com.cinestream.model.AppUser;
import com.cinestream.repository.UserRepository;
import com.cinestream.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository users; private final PasswordEncoder encoder; private final JwtService jwt;
    public AuthController(UserRepository users, PasswordEncoder encoder, JwtService jwt) {
        this.users=users; this.encoder=encoder; this.jwt=jwt;
    }
    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest req) {
        if(users.existsByEmail(req.email().toLowerCase()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered");
        AppUser user=users.save(new AppUser(req.name().trim(), req.email().toLowerCase(), encoder.encode(req.password())));
        return new AuthResponse(jwt.generate(user.getEmail()), user.getName(), user.getEmail());
    }
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest req) {
        AppUser user=users.findByEmail(req.email().toLowerCase())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));
        if(!encoder.matches(req.password(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        return new AuthResponse(jwt.generate(user.getEmail()), user.getName(), user.getEmail());
    }
}
