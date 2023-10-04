package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.BanqueAccount;
import com.example.demo.entities.Customer;
import com.example.demo.enums.AccountStatus;

import java.util.List;


public interface BanqueAccountRepository extends JpaRepository<BanqueAccount, String>{
	List<BanqueAccount> findByCustomer(Customer customer);
	void deleteAllByCustomer(Customer customer);
	List<BanqueAccount> findByIdContaining(String idKeyword);
	List<BanqueAccount> findByCustomerAndIdContains(Customer customer,String idKeyword);
	
}
