package com.example.demo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.dtos.CustomerDto;
import com.example.demo.entities.Customer;
import com.example.demo.web.request.CustomerAddRequest;
import com.example.demo.web.request.CustomerUpdateRequest;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
	/**
	 * Map Customer to CustomerDto
	 * @param Customer
	 * @return CustomerDto
	*/
	//@Mapping
	CustomerDto entityToDto(Customer customer);
	/**
	 * Map CustomerDto to Customer
	 * @param CustomerDto
	 * @return Customer
	*/
	@Mapping(target = "banqueAccount",ignore = true)
	Customer dtoToEntity(CustomerDto customerDto);
	/**
	 * Map CustomerAddRequest to CustomerDto
	 * @param CustomerAddRequest
	 * @return CustomerDto
	*/
	@Mapping(target = "id",ignore = true)
	CustomerDto addRequestToDto(CustomerAddRequest customerAddRequest);
	/**
	 * Map CustomerUpdateRequest to CustomerDto
	 * @param CustomerUpdateRequest
	 * @return CustomerDto
	*/
	CustomerDto updateRequestToDto(CustomerUpdateRequest customerAddRequest);
}
