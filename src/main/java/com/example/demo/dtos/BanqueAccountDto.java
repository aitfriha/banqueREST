package com.example.demo.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class BanqueAccountDto {
	 private String id;
	 private double balance;
	 private Date createdAt;
	 private String status;
	 private String type;
	 private double overDraft;
	 private double interestRate;
	 private CustomerDto customerDto;
}
