package com.example.demo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.dtos.CustomerDto;
import com.example.demo.dtos.UserDto;
import com.example.demo.entities.Customer;
import com.example.demo.entities.User;
import com.example.demo.web.request.CustomerAddRequest;
import com.example.demo.web.request.CustomerUpdateRequest;
import com.example.demo.web.request.UserAddRequest;
import com.example.demo.web.request.UserUpdateRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {
	/**
	 * Map User to UserDto
	 * @param User
	 * @return UserDto
	*/
	//@Mapping
	@Mapping(source = "customer",target ="customerDto" )
	@Mapping(target = "password",ignore = true) // dont map the password it will be null
	UserDto entityToDto(User user);
	/**
	 * Map userDto to Customer
	 * @param userDto
	 * @return User
	*/
	@Mapping(target = "customer",ignore = true)
	User dtoToEntity(UserDto userDto);
	/**
	 * Map UserAddRequest to UserDto
	 * @param UserAddRequest
	 * @return UserDto
	*/
	//@Mapping
	@Mapping(target = "id",ignore = true)
	@Mapping(source="customerId",target = "customerDto.id")
	UserDto addRequestToDto(UserAddRequest userAddRequest);
	/**
	 * Map UserUpdateRequest to UserDto
	 * @param UserUpdateRequest
	 * @return UserDto
	*/
	//@Mapping
	@Mapping(target = "customerDto",ignore = true)
	UserDto updateRequestToDto(UserUpdateRequest userUpdateRequest);
}
