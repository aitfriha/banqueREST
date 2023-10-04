package com.example.demo.entities;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
private Long id;
private String name;
private String email;   
//orphanRemoval = true, cascade = CascadeType.REMOVE
@OnDelete(action = OnDeleteAction.CASCADE)
@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
//@JsonProperty(access = Access.WRITE_ONLY) 
private List<BanqueAccount> banqueAccount;

}
