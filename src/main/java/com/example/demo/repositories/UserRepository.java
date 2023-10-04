package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.Customer;
import com.example.demo.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String userName);
	User findByCustomer(Customer customer);
	List<User> findByUsernameContainingIgnoreCase(String usernameKeyword);
	@Query(value = "SELECT * FROM user ORDER BY id LIMIT :start, :size", nativeQuery = true)
	List<User> findCustomersWithPagination(
	    int start,
	    int size
	);
	@Query(value = "SELECT * FROM user WHERE LOWER(username) LIKE %:keyword% OR id = :id ORDER BY id LIMIT :start, :size", nativeQuery = true)
	List<User> searchUsersWithPagination(
			int start,int size,String keyword,Long id
	);
}
