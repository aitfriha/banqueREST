package com.example.demo.services;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dtos.CustomerDto;
import com.example.demo.dtos.UserDto;
import com.example.demo.entities.Customer;
import com.example.demo.entities.User;


public interface UserService {
	UserDto saveUser(UserDto userDto);
	UserDto getUser(Long id);
	User getUserEntity(Long id);

	void deleteUser(Long id);
	UserDto updateUser(UserDto userDto);
	List<UserDto>searchUsersByKeyword(String keyword,int page,int size);
	List<UserDto> listUsers(int page, int size);
	List<UserDto> totalSearchUsersByKeyword(String keyword);
	UserDto getUserByUsername(String username);
}
