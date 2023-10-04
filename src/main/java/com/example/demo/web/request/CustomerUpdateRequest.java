package com.example.demo.web.request;

import static com.example.demo.exceptions.ValidationConstants.USER_EMAIL_NOT_BLANK;
import static com.example.demo.exceptions.ValidationConstants.USER_NAME_NOT_BLANK;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomerUpdateRequest {
	@NotNull
	private Long id;
	@NotBlank(message = USER_NAME_NOT_BLANK)
	private String name;
	@NotBlank(message = USER_EMAIL_NOT_BLANK)
	private String email;
}
