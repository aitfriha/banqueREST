package com.example.demo.web.request;

import static com.example.demo.exceptions.ValidationConstants.AMOUNT_MUST_BE_MORE_THAN_ZERO;

import java.util.Date;

import com.example.demo.enums.OperationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
@Data
public class AccountOperationAddRequest {
	private Date operationDate;
	@Positive(message = AMOUNT_MUST_BE_MORE_THAN_ZERO)
	private double amount;
	private String description;
	private OperationType type;
	@NotNull
	private String banqueAccountId;
}
