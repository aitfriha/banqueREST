package com.example.demo.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.AccountOperationDto;
import com.example.demo.dtos.CustomerDto;
import com.example.demo.enums.OperationType;
import com.example.demo.exceptions.EntityType;
import com.example.demo.exceptions.ExceptionType;
import com.example.demo.exceptions.validation.MapValidationErrorService;
import com.example.demo.mappers.AccountOperationMapper;
import com.example.demo.response.Response;
import com.example.demo.services.AccountOperationService;
import com.example.demo.web.request.AccountOperationAddRequest;
import com.example.demo.web.request.TransferOperationAddRequest;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import static com.example.demo.exceptions.ExceptionType.ADDED;
import static com.example.demo.exceptions.MainException.getMessageTemplate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("accountOperations")
@CrossOrigin("*")
public class AccountOperationRestController {
	private AccountOperationService accountOperationService;
	private AccountOperationMapper accountOperationMapper;
	private final MapValidationErrorService mapValidationErrorService;

	// ACTION CAN BE DEBIT OR CREDIT
	@PostMapping("/transfert")
	@Operation(summary = "Perform fund transfer", description = "Transfers funds from source to recipient account")
	public ResponseEntity transfert(@Valid @RequestBody TransferOperationAddRequest transferOperationAddRequest,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return mapValidationErrorService.mapValidationService(bindingResult);
		accountOperationService.transfer(accountOperationMapper.addRequetToDto(accountOperationMapper.TransferToDtoSource(transferOperationAddRequest)), accountOperationMapper.addRequetToDto(accountOperationMapper.TransferToDtoRecipient(transferOperationAddRequest)));
		return new ResponseEntity<Response>(Response.ok().setPayload(getMessageTemplate(EntityType.Operation, ADDED)),
				HttpStatus.OK);
	}

	@PostMapping("/debit")
	@Operation(summary = "Debit account", description = "Debits the account by the specified amount && banqueAccountId")
	public ResponseEntity debit(@Valid @RequestBody AccountOperationAddRequest accountOperationAddRequest,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return mapValidationErrorService.mapValidationService(bindingResult);
		accountOperationService.debit(accountOperationMapper.addRequetToDto(accountOperationAddRequest));
		return new ResponseEntity<Response>(
				Response.ok().setPayload(getMessageTemplate(EntityType.Operation, ExceptionType.DEBIT)), HttpStatus.OK);
	}

	@Operation(summary = "Credit account", description = "Credits the account by the specified amount && banqueAccountId")
	@PostMapping("/credit")
	public ResponseEntity credit(@Valid @RequestBody AccountOperationAddRequest accountOperationAddRequest,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return mapValidationErrorService.mapValidationService(bindingResult);
		accountOperationService.credit(accountOperationMapper.addRequetToDto(accountOperationAddRequest));
		return new ResponseEntity<Response>(
				Response.ok().setPayload(getMessageTemplate(EntityType.Operation, ExceptionType.CREDIT)),
				HttpStatus.OK);
	}
	@Operation(summary = "Get history for a bank account", description = "Returns a list of operations for an account")
	@GetMapping("/{idBankAccount}/AllOperationsByBankAccount")
	public List<AccountOperationDto> getAllOperationsByBankAccount(@PathVariable(name = "idBankAccount") String id){
		return accountOperationService.getAllOperationsByBankAccount(id);
	}
	
	@Operation(summary = "Get history for a bank account", description = "Returns a list of operations for an account")
	@GetMapping("/{idBankAccount}/AllOperationsByBankAccountSize")
	public Integer getAllOperationsByBankAccountSize(@PathVariable(name = "idBankAccount") String id){
		return accountOperationService.getAllOperationsByBankAccount(id).size();
	}
	
	@GetMapping("/{idBankAccount}/operationsByBankAccountWithPagination")
	public List<AccountOperationDto> getAllOperationsByBankAccountWithPagination(@PathVariable(name = "idBankAccount") String id,
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "size", required = false, defaultValue = "0") int size) {
		return accountOperationService.getAllOperationsByBankAccountWithPagination(id,page, size);
	}
	
	@GetMapping("/{idBankAccount}/operationsByBankAccountWithPaginationSearch")
	public List<AccountOperationDto> operationsByBankAccountWithPaginationSearch(@PathVariable(name = "idBankAccount") String id,
			@RequestParam(name = "keyword", defaultValue = "", required = false) String keyword,
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "size", required = false, defaultValue = "0") int size) {
		return accountOperationService.getAllOperationsByBankAccountWithPaginationSearch(id,keyword,page, size);
	}
	@GetMapping("/{idBankAccount}/operationsByBankAccountWithPaginationSearchSize")
	public Integer operationsByBankAccountWithPaginationSearchSize(@PathVariable(name = "idBankAccount") String id,
			@RequestParam(name = "keyword", defaultValue = "", required = false) String keyword) {
		return accountOperationService.getAllOperationsByBankAccountWithPaginationSearchSize(id,keyword).size();
	}
	
	
}
