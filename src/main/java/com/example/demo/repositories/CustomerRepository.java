package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Customer;
import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, Long>{
Customer findByEmail(String email);
List<Customer> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String nameKeyword, String emailKeyword);
@Query(value = "SELECT * FROM customer ORDER BY id LIMIT :start, :size", nativeQuery = true)
List<Customer> findCustomersWithPagination(
    int start,
    int size
);
@Query(value = "SELECT * FROM customer WHERE LOWER(name) LIKE %:keyword% OR LOWER(email) LIKE %:keyword% OR id = :id ORDER BY id LIMIT :start, :size", nativeQuery = true)
List<Customer> searchCustomersWithPagination(
		int start,int size,String keyword,Long id
);
}
