package com.example.demo.services;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dtos.CustomerDto;
import com.example.demo.entities.Customer;


public interface CustomerService {
	CustomerDto saveCustomer(CustomerDto customerDto);
	CustomerDto getCustomer(Long id);
	Customer getCustomerEntity(Long id);

	void deleteCustomer(Long id);
	CustomerDto updateCustomer(CustomerDto customerDto);
	List<CustomerDto>searchCustomersByKeyword(String keyword,int page,int size);
	List<CustomerDto> listCustomers(int page, int size);
	List<CustomerDto> totalSearchCustomersByKeyword(String keyword);
	Customer getCustomerEntityNull(Long id);
}
