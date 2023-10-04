package com.example.demo.dtos;

import java.util.Date;

import com.example.demo.enums.OperationType;
import lombok.Data;

@Data
public class AccountOperationDto { 
	private Long id;
	private Date operationDate;
	private double amount;
	private String description;
	private OperationType type;
	private BanqueAccountDto banqueAccountDto;
}
