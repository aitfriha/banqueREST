package com.example.demo.web.api;

import static com.example.demo.exceptions.EntityType.BanqueAccount;
import static com.example.demo.exceptions.ExceptionType.ADDED;
import static com.example.demo.exceptions.ExceptionType.UPDATED;
import static com.example.demo.exceptions.ExceptionType.DELETED;
import static com.example.demo.exceptions.MainException.getMessageTemplate;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dtos.BanqueAccountDto;
import com.example.demo.exceptions.ExceptionType;
import com.example.demo.mappers.BanqueAccountMapper;
import com.example.demo.response.Response;
import com.example.demo.services.BanqueAccountService;
import com.example.demo.web.request.BankAccountAddRequest;
import com.example.demo.web.request.BankAccountUpdateRequest;


import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("banqueAccounts")
@CrossOrigin("*")
public class BanqueAccountRestController {
	private BanqueAccountMapper banqueAccountMapper;
	private BanqueAccountService banqueAccountService;
	@Operation(summary = "Get all bank accounts", description = "Returns a list of all bank accounts")
	@GetMapping("/all")
	public List<BanqueAccountDto> getAllBanqueAccount(){
		return banqueAccountService.getAllBanqueAccount();
	}
	@Operation(summary = "Delete bank account by ID", description = "Deletes a bank account by ID")
	@DeleteMapping("/{id}/delete")
	public ResponseEntity deleteBanqueAccount(@PathVariable String id) {
		banqueAccountService.deleteBanqueAccount(id);
		return new ResponseEntity<Response>(Response.ok().setPayload(getMessageTemplate(BanqueAccount,DELETED)),HttpStatus.OK);
	}
	@Operation(summary = "Get all bank accounts by customer ID", description = "Returns a list of bank accounts for a customer")
	@GetMapping("/{idCustomer}/AllBankAccountsByCustomer")
	public List<BanqueAccountDto> getAllBankAccountsByCustomer(@PathVariable(name = "idCustomer") Long id){
		return banqueAccountService.getAllBankAccountsByCustomer(id);
	}
	@Operation(summary = "Update Bank Account", description = "Returns a message")
	@PutMapping("/updateBankAccount")
	public ResponseEntity updateBankAccount(@RequestBody BankAccountUpdateRequest bankAccountUpdateRequest){
		banqueAccountService.updateBankAccount(bankAccountUpdateRequest);
		return new ResponseEntity<Response>(Response.ok().setPayload(getMessageTemplate(BanqueAccount,UPDATED)),HttpStatus.OK);
	}
	@Operation(summary = "Add new bank account", description = "Returns message")
	@PostMapping("/add")
	public ResponseEntity save(@RequestBody BankAccountAddRequest bankAccountAddRequest ){
			banqueAccountService.saveBanqueAccount(banqueAccountMapper.banqueAccountAddRequestToDto(bankAccountAddRequest));
		return new ResponseEntity<Response>(Response.ok().setPayload(getMessageTemplate(BanqueAccount,ADDED)),HttpStatus.OK);
	}
	
	@GetMapping("/{customerId}/searchByKeyword")
	@Operation(summary = "Search accounts by keyword", description = "Search accounts by keyword")
	public List<BanqueAccountDto> searchAccountsByKeyword(@PathVariable(name = "customerId") Long customerId,@RequestParam(name ="keyword" ) String keyword){
		return banqueAccountService.searchAccountsByKeyword(customerId,keyword);
	}
}
