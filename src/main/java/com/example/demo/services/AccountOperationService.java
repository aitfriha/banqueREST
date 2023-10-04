package com.example.demo.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.demo.dtos.AccountOperationDto;
import com.example.demo.entities.AccountOperation;
import com.example.demo.enums.OperationType;
import com.example.demo.exceptions.BalanceNotSufficientException;
import com.example.demo.exceptions.BanqueAccountNotFoundException;

public interface AccountOperationService {
	//DEBIT AND CREDIT ACTION IN ONE METHODE
	AccountOperationDto debit(AccountOperationDto accountOperationDto);
	AccountOperationDto credit(AccountOperationDto accountOperationDto);
	void transfer(AccountOperationDto accountOperationDtoSource,AccountOperationDto accountOperationDtoRecipient);
	List<AccountOperationDto> getAllOperationsByBankAccount(String id);
	List<AccountOperationDto> getAllOperationsByBankAccountWithPagination(String id, int page, int size);
	List<AccountOperationDto> getAllOperationsByBankAccountWithPaginationSearch(String id, String keyword, int page,
			int size);
	List<AccountOperationDto> getAllOperationsByBankAccountWithPaginationSearchSize(String id, String keyword);
}
