package com.example.demo.services;

import java.util.List;

import com.example.demo.dtos.BanqueAccountDto;
import com.example.demo.entities.BanqueAccount;
import com.example.demo.exceptions.BanqueAccountNotFoundException;
import com.example.demo.web.request.BankAccountUpdateRequest;


public interface BanqueAccountService {
	//BanqueAccount saveCurrentBanqueAccount(double balance,Long customerId,double overDraft);
	//BanqueAccount saveSavingBanqueAccount(double balance,Long customerId,double interestRate);
	BanqueAccount getBanqueAccount(String accountId);
	List<BanqueAccountDto> getAllBanqueAccount();
	void deleteBanqueAccount(String id);
	List<BanqueAccountDto> getAllBankAccountsByCustomer(Long id);
	void saveBanqueAccount(BanqueAccountDto banqueAccountDto );
	List<BanqueAccountDto> searchAccountsByKeyword(Long customerId,String keyword);
	void updateBankAccount(BankAccountUpdateRequest bankAccountUpdateRequest);
}
