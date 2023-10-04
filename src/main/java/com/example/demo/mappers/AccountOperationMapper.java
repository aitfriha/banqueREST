package com.example.demo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.dtos.AccountOperationDto;
import com.example.demo.entities.AccountOperation;
import com.example.demo.web.request.AccountOperationAddRequest;
import com.example.demo.web.request.TransferOperationAddRequest;


@Mapper(componentModel = "spring")
public interface AccountOperationMapper {
    @Mapping(source = "banqueAccountId", target = "banqueAccountDto.id" )
	@Mapping(target="id", ignore=true)
	AccountOperationDto addRequetToDto(AccountOperationAddRequest accountOperationAddRequest);
    @Mapping(target = "banqueAccount", ignore = true)
	AccountOperation dtoToEntity(AccountOperationDto accountOperation);
    @Mapping(source = "banqueAccount", target = "banqueAccountDto")
    @Mapping(source = "banqueAccount.customer", target = "banqueAccountDto.customerDto")
	AccountOperationDto EntityToDto(AccountOperation accountOperation);
    
    @Mapping(source = "sourceBankAccount", target = "banqueAccountId" )
    AccountOperationAddRequest TransferToDtoSource(TransferOperationAddRequest transferOperationAddRequest);
    
    @Mapping(source = "recipientBankAccount", target = "banqueAccountId" )
    AccountOperationAddRequest TransferToDtoRecipient(TransferOperationAddRequest transferOperationAddRequest);
}
