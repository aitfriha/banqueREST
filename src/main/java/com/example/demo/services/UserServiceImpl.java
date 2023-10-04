package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dtos.UserDto;
import com.example.demo.entities.Customer;
import com.example.demo.entities.User;
import com.example.demo.exceptions.EntityType;
import com.example.demo.exceptions.ExceptionType;
import com.example.demo.mappers.UserMapper;
import com.example.demo.repositories.UserRepository;

import static com.example.demo.exceptions.MainException.throwException;
import lombok.AllArgsConstructor;


import static com.example.demo.exceptions.ExceptionType.DUPLICATE_ENTITY;
import static com.example.demo.exceptions.ExceptionType.ENTITY_NOT_FOUND;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	private final UserMapper userMapper;
	private CustomerService customerService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto saveUser(UserDto userDto) {
		User user = userRepository.findByUsername(userDto.getUsername());
		if (user != null) {
			throw exception(DUPLICATE_ENTITY);
		}
		Customer customer = customerService.getCustomerEntityNull(userDto.getCustomerDto().getId());
		User userTest = userRepository.findByCustomer(customer);
		if (userTest != null) {
			throw exception(DUPLICATE_ENTITY);
		}
		User userCryp = userMapper.dtoToEntity(userDto);
		//Customer customer = customerService.getCustomerEntityNull(userDto.getCustomerDto().getId());
		if (customer != null) {
			userCryp.setCustomer(customer);
		}
		userCryp.setPassword(bCryptPasswordEncoder.encode(userCryp.getPassword()));
		User savedUser = userRepository.save(userCryp);
		return userMapper.entityToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto) {
		User user = userRepository.findByUsername(userDto.getUsername());
		if (user != null && user.getId() != userDto.getId()) {
			throw exception(DUPLICATE_ENTITY);
		}
		User savedUser = userRepository.save(userMapper.dtoToEntity(userDto));
		return userMapper.entityToDto(savedUser);
	}

	// TODO REGLE DE GESTION CUSTMER A DES BANKACCOUNT
	@Override
	public void deleteUser(Long id) {
		UserDto userDto = getUser(id);
		User user = userMapper.dtoToEntity(userDto);
		userRepository.delete(user);
	}

	@Override
	public List<UserDto> listUsers(int page, int size) {
		List<UserDto> usersDtos = new ArrayList<UserDto>();
		if (size == 0) {
			List<User> users = userRepository.findAll();
			users.forEach(user -> {
				usersDtos.add(userMapper.entityToDto(user));
			});
			return usersDtos;
		}
		int start = (page - 1) * size;
		List<User> users = userRepository.findCustomersWithPagination(start, size);
		users.forEach(user -> {
			usersDtos.add(userMapper.entityToDto(user));
		});
		return usersDtos;

	}

	@Override
	public UserDto getUser(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw exception(ENTITY_NOT_FOUND);
		}
		return userMapper.entityToDto(user.get());
	}

	@Override
	public User getUserEntity(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw exception(ENTITY_NOT_FOUND);
		}
		return user.get();
	}

	@Override
	public List<UserDto> searchUsersByKeyword(String keyword, int page, int size) {
		int start = (page - 1) * size;
		List<UserDto> usersDtos = new ArrayList<UserDto>();
		Long id = (long) 0;
		if (canParseLong(keyword)) {
			id = Long.parseLong(keyword);
		}
		List<User> users = userRepository.searchUsersWithPagination(start, size, keyword, id);
		users.forEach(user -> {
			usersDtos.add(userMapper.entityToDto(user));
		});
		return usersDtos;
	}

	@Override
	public List<UserDto> totalSearchUsersByKeyword(String keyword) {
		List<UserDto> usersDtos = new ArrayList<UserDto>();
		List<User> users = userRepository.findByUsernameContainingIgnoreCase(keyword);
		users.forEach(customer -> {
			usersDtos.add(userMapper.entityToDto(customer));
		});
		return usersDtos;
	}

	@Override
	public UserDto getUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user != null) {
			UserDto userDto = userMapper.entityToDto(user);
			return userDto;
		} else
			return null;

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
		return throwException(EntityType.User, exceptionType, args);
	}

}
