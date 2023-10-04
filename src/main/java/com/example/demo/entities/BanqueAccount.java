package com.example.demo.entities;

import java.util.Date;
import java.util.List;


import com.example.demo.enums.AccountStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE",length = 4)
@Data @NoArgsConstructor @AllArgsConstructor
public class BanqueAccount {
 @Id  
 private String id;
 private double balance;
 private Date createdAt;
 @Enumerated(EnumType.STRING)
 private AccountStatus status;
 @ManyToOne
 private Customer customer;
 //when deleting BanqueAccount all related operation will be deleted "orphanRemoval"
 @OneToMany(mappedBy = "banqueAccount",fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.REMOVE)
 private List<AccountOperation> accountOperation;
}
