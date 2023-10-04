package com.example.demo.services;

import static com.example.demo.exceptions.ExceptionType.ENTITY_NOT_FOUND;
import static com.example.demo.exceptions.MainException.throwException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtos.BanqueAccountDto;
import com.example.demo.entities.BanqueAccount;
import com.example.demo.entities.CurrentAccount;
import com.example.demo.entities.Customer;
import com.example.demo.entities.SavingAccount;
import com.example.demo.enums.AccountStatus;
import com.example.demo.repositories.BanqueAccountRepository;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.web.api.CustomerRestController;
import com.example.demo.web.request.BankAccountUpdateRequest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.demo.exceptions.BanqueAccountNotFoundException;
import com.example.demo.exceptions.EntityType;
import com.example.demo.exceptions.ExceptionType;
import com.example.demo.mappers.BanqueAccountMapper;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BanqueAccountServiceImpl implements BanqueAccountService {
	private BanqueAccountRepository accountRepository;
	private CustomerRepository customerRepository;
	private CustomerService customerService;
	private final BanqueAccountMapper banqueAccountMapper;
	/*
	 * @Override public BanqueAccount saveSavingBanqueAccount(double balance, Long
	 * customerId, double interestRate) { Customer customer =
	 * customerRepository.findById(customerId).orElse(null); BanqueAccount
	 * savingAccount = new SavingAccount();
	 * savingAccount.setId(UUID.randomUUID().toString());
	 * savingAccount.setBalance(balance);
	 * savingAccount.setStatus(AccountStatus.CREATED);
	 * savingAccount.setCustomer(customer); savingAccount.setCreatedAt(new Date());
	 * ((SavingAccount)savingAccount).setInterestRate(interestRate); savingAccount =
	 * accountRepository.save(savingAccount); return savingAccount; }
	 */
	/*
	 * @Override public BanqueAccount saveCurrentBanqueAccount(double balance, Long
	 * customerId, double overDraft) { Customer customer =
	 * customerRepository.findById(customerId).orElse(null); BanqueAccount
	 * currentAccount = new CurrentAccount();
	 * currentAccount.setId(UUID.randomUUID().toString());
	 * currentAccount.setBalance(balance);
	 * currentAccount.setStatus(AccountStatus.CREATED);
	 * currentAccount.setCustomer(customer); currentAccount.setCreatedAt(new
	 * Date()); ((CurrentAccount)currentAccount).setOverDraft(overDraft);
	 * currentAccount = accountRepository.save(currentAccount); return
	 * currentAccount;
	 * 
	 * }
	 */

	@Override
	public BanqueAccount getBanqueAccount(String accountId) {
		Optional<BanqueAccount> banqueAccount = accountRepository.findById(accountId);
		if (!banqueAccount.isPresent()) {
			throw exception(ENTITY_NOT_FOUND);
		}
		return banqueAccount.get();
	}

	@Override
	public List<BanqueAccountDto> getAllBanqueAccount() {
		List<BanqueAccountDto> banqueAccountDtos = new ArrayList<BanqueAccountDto>();
		List<BanqueAccount> banqueAccounts = accountRepository.findAll();
		banqueAccounts.forEach(banqueAccount -> {
			banqueAccountDtos.add(banqueAccountMapper.entityToDto(banqueAccount));
		});
		return banqueAccountDtos;
	}

	@Override
	public List<BanqueAccountDto> getAllBankAccountsByCustomer(Long id) {
		List<BanqueAccountDto> banqueAccountDtos = new ArrayList<BanqueAccountDto>();
		String a = null;
		Customer customer = customerService.getCustomerEntity(id);
		List<BanqueAccount> banqueAccounts = accountRepository.findByCustomer(customer);
		banqueAccounts.forEach(banqueAccount -> {
			BanqueAccountDto banqueAccountDto = new BanqueAccountDto();
			if (banqueAccount instanceof CurrentAccount) {
				banqueAccountDto = banqueAccountMapper.entityToDto(banqueAccount);
				banqueAccountDto.setType("CA");
				banqueAccountDtos.add(banqueAccountDto);
			} else {
				banqueAccountDto = banqueAccountMapper.entityToDto(banqueAccount);
				banqueAccountDto.setType("SA");
				banqueAccountDtos.add(banqueAccountDto);
			}
		});
		return banqueAccountDtos;
	}

	@Override
	public void deleteBanqueAccount(String id) {
		BanqueAccount banqueAccount = getBanqueAccount(id);
		accountRepository.delete(banqueAccount);
	}

	private RuntimeException exception(ExceptionType exceptionType, String... args) {
		return throwException(EntityType.BanqueAccount, exceptionType, args);
	}

	@Override
	public void saveBanqueAccount(BanqueAccountDto banqueAccountDto) {
		if (banqueAccountDto.getType().equals("currentAccount")) {
			CurrentAccount banqueAccount = banqueAccountMapper.dtoToCurrentAccount(banqueAccountDto);
			Customer customer = customerService.getCustomerEntity(banqueAccount.getCustomer().getId());
			banqueAccount.setCustomer(customer);
			banqueAccount.setId(UUID.randomUUID().toString());
			accountRepository.save(banqueAccount);

		} else {
			SavingAccount banqueAccount = banqueAccountMapper.dtoToSavingAccount(banqueAccountDto);
			Customer customer = customerService.getCustomerEntity(banqueAccount.getCustomer().getId());
			banqueAccount.setCustomer(customer);
			banqueAccount.setId(UUID.randomUUID().toString());
			accountRepository.save((BanqueAccount) banqueAccount);

		}

	}

	@Override
	public List<BanqueAccountDto> searchAccountsByKeyword(Long customerId,String keyword) {
		List<BanqueAccountDto> banqueAccountDtos = new ArrayList<BanqueAccountDto>();
		Customer customer = customerService.getCustomerEntity(customerId);
		List<BanqueAccount> banqueAccounts = accountRepository.findByCustomerAndIdContains(customer,keyword);
		banqueAccounts.forEach(banqueAccount -> {
			BanqueAccountDto banqueAccountDto = new BanqueAccountDto();
			if (banqueAccount instanceof CurrentAccount) {
				banqueAccountDto = banqueAccountMapper.entityToDto(banqueAccount);
				banqueAccountDto.setType("CA");
				banqueAccountDtos.add(banqueAccountDto);
			} else {
				banqueAccountDto = banqueAccountMapper.entityToDto(banqueAccount);
				banqueAccountDto.setType("SA");
				banqueAccountDtos.add(banqueAccountDto);
			}
		});
		/*banqueAccounts.forEach(banqueAccount -> {
			banqueAccountDtos.add(banqueAccountMapper.entityToDto(banqueAccount));
			
		});*/
		return banqueAccountDtos;
	}

	@Override
	public void updateBankAccount(BankAccountUpdateRequest bankAccountUpdateRequest) {
		BanqueAccount account= getBanqueAccount(bankAccountUpdateRequest.getId());
		account.setStatus(bankAccountUpdateRequest.getStatus());
		accountRepository.save(account);
	}

}
