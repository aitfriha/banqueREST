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
import com.example.demo.dtos.UserDto;

import static com.example.demo.exceptions.EntityType.Customer;
import static com.example.demo.exceptions.EntityType.User;
import static com.example.demo.exceptions.ExceptionType.ADDED;
import static com.example.demo.exceptions.ExceptionType.DELETED;
import static com.example.demo.exceptions.ExceptionType.UPDATED;
import com.example.demo.exceptions.validation.MapValidationErrorService;
import com.example.demo.mappers.CustomerMapper;
import com.example.demo.mappers.UserMapper;
import com.example.demo.response.Response;
import com.example.demo.services.CustomerService;
import com.example.demo.services.UserService;
import com.example.demo.web.request.CustomerAddRequest;
import com.example.demo.web.request.CustomerUpdateRequest;
import com.example.demo.web.request.UserAddRequest;
import com.example.demo.web.request.UserUpdateRequest;

import io.swagger.v3.oas.annotations.Operation;

import static com.example.demo.exceptions.MainException.getMessageTemplate;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("users")
@CrossOrigin("*")
public class UserRestController {
	private UserService userService;
	private final UserMapper userMapper;
	private final MapValidationErrorService mapValidationErrorService;

	@Operation(summary = "Get all Users", description = "Returns a list of all Users")
	@GetMapping("/all")
	public List<UserDto> listUsers() {
		return userService.listUsers(0, 0);
	}

	@Operation(summary = "Get User by ID", description = "Returns a User by ID")
	@GetMapping("/{id}")
	public UserDto getUser(@PathVariable Long id) {
		return userService.getUser(id);
	}
    //we can use one methode for add and for update if id not exist its a new user if id existe its a an update
	@Operation(summary = "Add new User", description = "Returns a message")
	@PostMapping("/add")
	public ResponseEntity saveUser(@Valid @RequestBody UserAddRequest userAddRequest,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return mapValidationErrorService.mapValidationService(bindingResult);
		userService.saveUser(userMapper.addRequestToDto(userAddRequest));
		return new ResponseEntity<Response>(Response.ok().setPayload(getMessageTemplate(User, ADDED)),
				HttpStatus.OK);
	}

	@Operation(summary = "Update User", description = "Updates an existing User")
	@PutMapping("/update")
	public ResponseEntity update(@Valid @RequestBody UserUpdateRequest userUpdateRequest,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return mapValidationErrorService.mapValidationService(bindingResult);
		userService.updateUser(userMapper.updateRequestToDto(userUpdateRequest));
		return new ResponseEntity<Response>(Response.ok().setPayload(getMessageTemplate(User, UPDATED)),
				HttpStatus.OK);
	}
	
	@GetMapping("/search")
	@Operation(summary = "Search users by keyword", description = "Search users by keyword")
//	@PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
	public List<UserDto> searchCustomersByKeyword(@RequestParam(name = "keyword", defaultValue = "") String keyword,
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "size", required = false, defaultValue = "0") int size) {
		                   
		return userService.searchUsersByKeyword(keyword, page, size);
	}

	@GetMapping("/userSizeSearch")
	@Operation(summary = "Retrieve the total number of users based on a search query", description = "return number")
//	@PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
	public Integer totalSearchCustomersByKeyword(@RequestParam(name = "keyword", defaultValue = "") String keyword) {
		return userService.totalSearchUsersByKeyword(keyword).size();
	}

	@Operation(summary = "Return total number of users", description = "Return a number")
	@GetMapping("/userSize")
//	@PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
	public Integer totalUsers() {
		return userService.listUsers(0, 0).size();
	}

	@GetMapping("/usersWithPagination")
//	@PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
	public List<UserDto> getUsersWithPagination(
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "size", required = false, defaultValue = "0") int size) {
		return userService.listUsers(page, size);
	}
	
	@Operation(summary = "Get one user by username", description = "Returns a user by username")
	@GetMapping("/userByUsername")
	public UserDto getUserByUsername(@RequestParam(name = "username") String username) {
		return userService.getUserByUsername(username);
	}
	
	@Operation(summary = "Delete user by ID", description = "Deletes a user by ID")
	@DeleteMapping("/{id}/delete")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	public ResponseEntity delete(@PathVariable Long id) {
		userService.deleteUser(id);
		return new ResponseEntity<Response>(Response.ok().setPayload(getMessageTemplate(User, DELETED)),
				HttpStatus.OK);
	}

}
