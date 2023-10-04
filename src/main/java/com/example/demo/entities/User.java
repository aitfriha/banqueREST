package com.example.demo.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Column(name = "role")
    private List<String> roles; // Une liste de rôles pour l'utilisateur
    @OneToOne(optional = true) // La relation est optionnelle pour les utilisateurs avec le rôle "admin"
    private Customer customer; // Un client est associé à l'utilisateur (si l'utilisateur est un client)
}
