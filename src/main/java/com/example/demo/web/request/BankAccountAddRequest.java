package com.example.demo.web.request;

import java.util.Date;
import com.example.demo.enums.AccountStatus;

import lombok.Data;
@Data
public class BankAccountAddRequest {
	 private double balance;
	 private Date createdAt;
	 private AccountStatus status;
	 private Long customerId;
	 private double overDraft;
	 private double interestRate;
	 private String type;

}
