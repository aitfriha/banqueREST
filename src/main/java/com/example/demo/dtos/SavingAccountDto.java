package com.example.demo.dtos;

import java.util.Date;

import com.example.demo.enums.AccountStatus;

import lombok.Data;
@Data
public class SavingAccountDto {
	 private String id;
	 private double balance;
	 private Date createdAt;
	 private AccountStatus status;
	 private CustomerDto customerDto;
	 private double interestRate;

}
