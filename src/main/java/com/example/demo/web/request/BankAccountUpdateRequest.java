package com.example.demo.web.request;

import java.util.Date;
import com.example.demo.enums.AccountStatus;

import lombok.Data;
@Data
public class BankAccountUpdateRequest {
	String id;
	 private AccountStatus status;


}
