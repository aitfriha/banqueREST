package com.example.demo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.dtos.BanqueAccountDto;
import com.example.demo.entities.BanqueAccount;
import com.example.demo.entities.CurrentAccount;
import com.example.demo.entities.SavingAccount;
import com.example.demo.web.request.BankAccountAddRequest;


@Mapper(componentModel = "spring")
public interface BanqueAccountMapper {
	/**
	 * Map Entity to BanqueAccountDto
	 * 
	 * @param banqueAccount
	 * @return BanqueAccountDto
	 */
	@Mapping(source = "customer", target = "customerDto")
	BanqueAccountDto entityToDto(BanqueAccount banqueAccount);

	/**
	 * Map BanqueAccountDto to Entity
	 * 
	 * @param banqueAccountDto
	 * @return BanqueAccount
	 */
	@Mapping(source = "customerDto", target = "customer")
	@Mapping(ignore = true, target = "accountOperation")
	@Mapping(target = "customer.banqueAccount", ignore = true)
	BanqueAccount dtoToEntity(BanqueAccountDto banqueAccountDto);



	/**
	 * Map bankAccountAddRequest to BanqueAccountDto
	 * 
	 * @param bankAccountAddRequest
	 * @return BanqueAccountDto
	 */
	@Mapping(source = "customerId", target = "customerDto.id")
	@Mapping(target = "id", ignore = true)
	BanqueAccountDto banqueAccountAddRequestToDto(BankAccountAddRequest bankAccountAddRequest);


	@Mapping(target = "customer.id", source = "dto.customerDto.id")
	CurrentAccount dtoToCurrentAccount(BanqueAccountDto dto);
	

	@Mapping(target = "customer.id", source = "dto.customerDto.id")
	SavingAccount dtoToSavingAccount(BanqueAccountDto dto);

}
