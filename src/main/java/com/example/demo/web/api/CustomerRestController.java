package com.example.demo.web.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
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

import com.example.demo.dtos.CustomerDto;

import static com.example.demo.exceptions.EntityType.Customer;
import static com.example.demo.exceptions.ExceptionType.ADDED;
import static com.example.demo.exceptions.ExceptionType.UPDATED;
import static com.example.demo.exceptions.ExceptionType.DELETED;
import com.example.demo.exceptions.validation.MapValidationErrorService;
import com.example.demo.mappers.CustomerMapper;
import com.example.demo.response.Response;
import com.example.demo.services.CustomerService;
import com.example.demo.web.request.CustomerAddRequest;
import com.example.demo.web.request.CustomerUpdateRequest;

import io.swagger.v3.oas.annotations.Operation;

import static com.example.demo.exceptions.MainException.getMessageTemplate;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("customers")
@CrossOrigin("*")
public class CustomerRestController {
	private CustomerService customerService;
	private final CustomerMapper customerMapper;
	private final MapValidationErrorService mapValidationErrorService;

	@Operation(summary = "Get all customers", description = "Returns a list of all customers")
	@GetMapping("/all")
	@PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER','SCOPE_ROLE_ADMIN')")
	public List<CustomerDto> listCustomes() {
		return customerService.listCustomers(0, 0);
	}

	@Operation(summary = "Get customer by ID", description = "Returns a customer by ID")
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
	public CustomerDto getCustomer(@PathVariable Long id) {
		return customerService.getCustomer(id);
	}
    //we can use one methode for add and for update if id not exist its a new customer if id existe its a an update
	@Operation(summary = "Add new customer", description = "Returns a message")
	@PostMapping("/add")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	public ResponseEntity saveCustomer(@Valid @RequestBody CustomerAddRequest customerAddRequest,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return mapValidationErrorService.mapValidationService(bindingResult);
		customerService.saveCustomer(customerMapper.addRequestToDto(customerAddRequest));
		return new ResponseEntity<Response>(Response.ok().setPayload(getMessageTemplate(Customer, ADDED)),
				HttpStatus.OK);
	}
	/*
	@Operation(summary = "Add new customer", description = "Returns a message")
	@PostMapping("/add")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	public ResponseEntity saveCustomer(@Valid @RequestBody CustomerAddRequest customerAddRequest,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return mapValidationErrorService.mapValidationService(bindingResult);
		customerService.saveCustomer(customerMapper.addRequestToDto(customerAddRequest));
		//ResponseEntity<Response> responseEntity= new ResponseEntity<Response>(Response.ok().setPayload(getMessageTemplate(Customer, ADDED)),
		//		HttpStatus.OK);
		
		//System.out.println(responseEntity);
		Response response = Response.ok().setPayload(getMessageTemplate(Customer, ADDED));
		System.out.println("################responseresponseresponse");
		System.out.println(response);
		return ResponseEntity.ok(response);
		//return responseEntity;
	}*/

	@Operation(summary = "Delete customer by ID", description = "Deletes a customer by ID")
	@DeleteMapping("/{id}/delete")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	public ResponseEntity delete(@PathVariable Long id) {
		customerService.deleteCustomer(id);
		return new ResponseEntity<Response>(Response.ok().setPayload(getMessageTemplate(Customer, DELETED)),
				HttpStatus.OK);
	}

	@Operation(summary = "Update customer", description = "Updates an existing customer")
	@PutMapping("/update")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	public ResponseEntity update(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return mapValidationErrorService.mapValidationService(bindingResult);
		customerService.updateCustomer(customerMapper.updateRequestToDto(customerUpdateRequest));
		return new ResponseEntity<Response>(Response.ok().setPayload(getMessageTemplate(Customer, UPDATED)),
				HttpStatus.OK);
	}

	@GetMapping("/search")
	@Operation(summary = "Search customers by keyword", description = "Search customers by keyword")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	public List<CustomerDto> searchCustomersByKeyword(@RequestParam(name = "keyword", defaultValue = "") String keyword,
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "size", required = false, defaultValue = "0") int size) {

		return customerService.searchCustomersByKeyword(keyword, page, size);
	}

	@GetMapping("/customerSizeSearch")
	@Operation(summary = "Retrieve the total number of customers based on a search query", description = "return number")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	public Integer totalSearchCustomersByKeyword(@RequestParam(name = "keyword", defaultValue = "") String keyword) {
		return customerService.totalSearchCustomersByKeyword(keyword).size();
	}

	@Operation(summary = "Return total number of customers", description = "Return a number")
	@GetMapping("/customerSize")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	public Integer totalCustomes() {
		return customerService.listCustomers(0, 0).size();
	}

	@GetMapping("/customersWithPagination")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	public List<CustomerDto> getCustomersWithPagination(
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "size", required = false, defaultValue = "0") int size) {
		return customerService.listCustomers(page, size);
	}
}
