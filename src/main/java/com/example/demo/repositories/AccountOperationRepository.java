package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.AccountOperation;
import com.example.demo.entities.BanqueAccount;
import com.example.demo.enums.OperationType;

import java.util.List;


public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long>{
List<AccountOperation> findByBanqueAccount(BanqueAccount banqueAccount);
@Query(value = "SELECT * FROM account_operation ao WHERE ao.banque_account_id like :idBankAccount ORDER BY id LIMIT :start, :size", nativeQuery = true)
List<AccountOperation> searchOperationWithPagination(String idBankAccount, int start, int size);
@Query(value = "SELECT * FROM account_operation ao WHERE ao.banque_account_id like :idBankAccount AND (LOWER(ao.type) LIKE %:keyword% OR LOWER(ao.id) LIKE %:keyword%)  ORDER BY id LIMIT :start, :size", nativeQuery = true)
List<AccountOperation> searchOperationWithPaginationSerach(String idBankAccount, String keyword, int start, int size);

@Query(value = "SELECT * FROM account_operation ao WHERE ao.banque_account_id like :idBankAccount AND (LOWER(ao.type) LIKE %:keyword% OR LOWER(ao.id) LIKE %:keyword%)  ORDER BY id", nativeQuery = true)
List<AccountOperation> searchOperationWithPaginationSerachSize(String idBankAccount, String keyword);
}
