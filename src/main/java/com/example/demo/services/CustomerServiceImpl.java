package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dtos.CustomerDto;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.CustomerNotFountException;
import com.example.demo.exceptions.EntityType;
import com.example.demo.exceptions.ExceptionType;
import com.example.demo.mappers.CustomerMapper;
import com.example.demo.repositories.BanqueAccountRepository;
import com.example.demo.repositories.CustomerRepository;
import static com.example.demo.exceptions.MainException.throwException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.example.demo.exceptions.ExceptionType.DUPLICATE_ENTITY;
import static com.example.demo.exceptions.ExceptionType.ENTITY_NOT_FOUND;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
	private CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;
	private BanqueAccountRepository accountRepository;

	@Override
	public CustomerDto saveCustomer(CustomerDto customerDto) {
		Customer customer = customerRepository.findByEmail(customerDto.getEmail());
		if (customer != null) {
			throw exception(DUPLICATE_ENTITY);
		}
		Customer savedCustomer = customerRepository.save(customerMapper.dtoToEntity(customerDto));
		return customerMapper.entityToDto(savedCustomer);
	}

	@Override
	public CustomerDto updateCustomer(CustomerDto customerDto) {
		Customer customer = customerRepository.findByEmail(customerDto.getEmail());
		if (customer != null && !customer.getId().equals(customerDto.getId())) {
			throw exception(DUPLICATE_ENTITY);
		}
		Customer savedCustomer = customerRepository.save(customerMapper.dtoToEntity(customerDto));
		return customerMapper.entityToDto(savedCustomer);
	}

	// TODO REGLE DE GESTION CUSTMER A DES BANKACCOUNT
	@Override
	public void deleteCustomer(Long id) {
		CustomerDto customerDto = getCustomer(id);
		Customer customer = customerMapper.dtoToEntity(customerDto);
		// DELETE JUSTE ACCOUT OF DELETED USER
		accountRepository.deleteAllByCustomer(customer);
		// customer.setBanqueAccount(null);
		customerRepository.delete(customer);
	}

	@Override
	public List<CustomerDto> listCustomers(int page, int size) {
		List<CustomerDto> customersDtos = new ArrayList<CustomerDto>();
		if (size == 0) {
			List<Customer> customers = customerRepository.findAll();
			customers.forEach(customer -> {
				customersDtos.add(customerMapper.entityToDto(customer));
			});
			return customersDtos;
		}
		int start = (page-1) * size;
		List<Customer> customers = customerRepository.findCustomersWithPagination(start,size);
		customers.forEach(customer -> {
			customersDtos.add(customerMapper.entityToDto(customer));
		});
		return customersDtos;

	}

	@Override
	public CustomerDto getCustomer(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (!customer.isPresent()) {
			throw exception(ENTITY_NOT_FOUND);
		}
		return customerMapper.entityToDto(customer.get());
	}


	@Override
	public Customer getCustomerEntity(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (!customer.isPresent()) {
			throw exception(ENTITY_NOT_FOUND);
		}
		return customer.get();
	}

	@Override
	public List<CustomerDto> searchCustomersByKeyword(String keyword,int page,int size) {
		int start = (page-1) * size;
		Long id = (long) 0;
		if (canParseLong(keyword)) {
			id = Long.parseLong(keyword);
		}
		List<CustomerDto> customersDtos = new ArrayList<CustomerDto>();
		List<Customer> customers = customerRepository.searchCustomersWithPagination(start,size,keyword,id);
		//List<Customer> customers = customerRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword,keyword);
		customers.forEach(customer -> {
			customersDtos.add(customerMapper.entityToDto(customer));
		});
		return customersDtos;
	}

	@Override
	public List<CustomerDto> totalSearchCustomersByKeyword(String keyword) {
		List<CustomerDto> customersDtos = new ArrayList<CustomerDto>();
		List<Customer> customers = customerRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword,keyword);
		customers.forEach(customer -> {
			customersDtos.add(customerMapper.entityToDto(customer));
		});
		return customersDtos;
	}
	
	//this methode will be used in UserService i need to return null not an exception 
	@Override
	public Customer getCustomerEntityNull(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (!customer.isPresent()) {
			return null;
		}
		return customer.get();
	}
	
	public static boolean canParseLong(String str) {
		try {
			Long.parseLong(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	private RuntimeException exception(ExceptionType exceptionType, String... args) {
		return throwException(EntityType.Customer, exceptionType, args);
	}


}
