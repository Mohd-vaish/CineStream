package com.cinestream.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "app_users")
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true) private String email;
    @Column(nullable = false) private String name;
    @Column(nullable = false) private String password;
    private LocalDateTime createdAt = LocalDateTime.now();

    protected AppUser() {}
    public AppUser(String name, String email, String password) {
        this.name = name; this.email = email; this.password = password;
    }
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getPassword() { return password; }
}
