package com.example.demo.services;

import static com.example.demo.exceptions.ExceptionType.ENTITY_NOT_FOUND;
import static com.example.demo.exceptions.MainException.throwException;
import static com.example.demo.exceptions.ExceptionType.BALANCE_NOT_SUFFICIENT;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtos.AccountOperationDto;
import com.example.demo.dtos.BanqueAccountDto;
import com.example.demo.dtos.CustomerDto;
import com.example.demo.entities.AccountOperation;
import com.example.demo.entities.BanqueAccount;
import com.example.demo.entities.Customer;
import com.example.demo.enums.OperationType;
import com.example.demo.exceptions.BalanceNotSufficientException;
import com.example.demo.exceptions.BanqueAccountNotFoundException;
import com.example.demo.exceptions.EntityType;
import com.example.demo.exceptions.ExceptionType;
import com.example.demo.mappers.AccountOperationMapper;
import com.example.demo.repositories.AccountOperationRepository;
import com.example.demo.repositories.BanqueAccountRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AccountOperationServiceImpl implements AccountOperationService{
    private AccountOperationRepository accountOperationRepository;
    private BanqueAccountRepository banqueAccountRepository; 
    private BanqueAccountService banqueAccountService;
	private AccountOperationMapper accountOperationMapper;


	@Override
	public AccountOperationDto debit(AccountOperationDto accountOperationDto){
		  BanqueAccount banqueAccount = banqueAccountService.getBanqueAccount(accountOperationDto.getBanqueAccountDto().getId());
		  AccountOperation accountOperationSaved;
		  if(accountOperationDto.getAmount()<banqueAccount.getBalance()) {
			  banqueAccount.setBalance(banqueAccount.getBalance()-accountOperationDto.getAmount());
			  banqueAccountRepository.save(banqueAccount); 
			  AccountOperation accountOperation = accountOperationMapper.dtoToEntity(accountOperationDto);
			  accountOperation.setBanqueAccount(banqueAccount);
			   accountOperationSaved = accountOperationRepository.save(accountOperation);
		  }
		  else {
			  throw exception(BALANCE_NOT_SUFFICIENT);
		  }
		  
		  return accountOperationMapper.EntityToDto(accountOperationSaved);
	}

	@Override
	public AccountOperationDto credit(AccountOperationDto accountOperationDto){
		  BanqueAccount banqueAccount = banqueAccountService.getBanqueAccount(accountOperationDto.getBanqueAccountDto().getId());
		  banqueAccount.setBalance(banqueAccount.getBalance()+accountOperationDto.getAmount());
		  banqueAccountRepository.save(banqueAccount); 
		  AccountOperation accountOperation = accountOperationMapper.dtoToEntity(accountOperationDto);
		  accountOperation.setBanqueAccount(banqueAccount);
		  accountOperationRepository.save(accountOperation);
		  return accountOperationMapper.EntityToDto(accountOperationRepository.save(accountOperation));
	}


	 private RuntimeException exception(ExceptionType exceptionType, String... args) {
	        return throwException(EntityType.Operation, exceptionType, args);
	    }

	@Override
	public void transfer(AccountOperationDto accountOperationDtoSource,AccountOperationDto accountOperationDtoRecipient) {
		debit(accountOperationDtoSource);
		credit(accountOperationDtoRecipient);
	}

	@Override
	public List<AccountOperationDto> getAllOperationsByBankAccount(String id) {
		List<AccountOperation> accountOperations=  accountOperationRepository.findByBanqueAccount(banqueAccountService.getBanqueAccount(id));
		List<AccountOperationDto> accountOperationsDtos= new ArrayList<AccountOperationDto>();
		for (AccountOperation accountOperation : accountOperations) {
			accountOperationsDtos.add(accountOperationMapper.EntityToDto(accountOperationRepository.save(accountOperation)));
		}
		return accountOperationsDtos;
	}

	@Override
	public List<AccountOperationDto> getAllOperationsByBankAccountWithPagination(String id, int page, int size) {
		int start = (page-1) * size;
		List<AccountOperationDto> accountOperationDtos = new ArrayList<AccountOperationDto>();
		List<AccountOperation> accountOperations = accountOperationRepository.searchOperationWithPagination(id,start,size);
		accountOperations.forEach(accountOperation -> {
			accountOperationDtos.add(accountOperationMapper.EntityToDto(accountOperation));
		});
		return accountOperationDtos;
	}

	@Override
	public List<AccountOperationDto> getAllOperationsByBankAccountWithPaginationSearch(String id, String keyword,
			int page, int size) {
		int start = (page-1) * size;
		List<AccountOperationDto> accountOperationDtos = new ArrayList<AccountOperationDto>();
		List<AccountOperation> accountOperations = accountOperationRepository.searchOperationWithPaginationSerach(id,keyword,start,size);
		accountOperations.forEach(accountOperation -> {
			accountOperationDtos.add(accountOperationMapper.EntityToDto(accountOperation));
		});
		return accountOperationDtos;
	}

	@Override
	public List<AccountOperationDto> getAllOperationsByBankAccountWithPaginationSearchSize(String banqueAccountId, String keyword) {
		List<AccountOperationDto> accountOperationDtos = new ArrayList<AccountOperationDto>();
		List<AccountOperation> accountOperations = accountOperationRepository.searchOperationWithPaginationSerachSize(banqueAccountId,keyword);
		accountOperations.forEach(accountOperation -> {
			accountOperationDtos.add(accountOperationMapper.EntityToDto(accountOperation));
		});
		return accountOperationDtos;
	}
	
	

}
