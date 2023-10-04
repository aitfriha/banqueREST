package com.example.demo.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import static com.example.demo.exceptions.ValidationConstants.SOURCE_BANK_ACCOUNT;

import java.util.Date;

import com.example.demo.enums.OperationType;

import static com.example.demo.exceptions.ValidationConstants.RECIPIENT_BANK_ACCOUNT;
import static com.example.demo.exceptions.ValidationConstants.AMOUNT_MUST_BE_MORE_THAN_ZERO;

@Data
public class TransferOperationAddRequest {
	@NotBlank(message = SOURCE_BANK_ACCOUNT)
	private String sourceBankAccount;
	@NotBlank(message =  RECIPIENT_BANK_ACCOUNT)
	private String recipientBankAccount;
	@Positive(message = AMOUNT_MUST_BE_MORE_THAN_ZERO)
	private double amount;
	private Date operationDate;
	private OperationType type;
	private String description;
}
