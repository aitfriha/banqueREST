package com.example.demo.exceptions;

public enum ExceptionType {
   
    ENTITY_NOT_FOUND("not found"),
    DUPLICATE_ENTITY("duplicate"),
	ADDED("added"),
	UPDATED("updated"),
	DELETED("deleted"),
	BALANCE_NOT_SUFFICIENT("balance not sufficient"),
	DEBIT("completed successfully Bank account debited"),
	CREDIT("completed successfully Bank account credited"),
	FORBIDDEN("access to the requested resource is forbidden"),
	WRONG_CREDENTIALS("wrong credencials");
    String value;
    ExceptionType(String value) {
        this.value = value;
    }

    String getValue() {
        return this.value;
    }
}
