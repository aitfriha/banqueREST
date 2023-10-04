package com.example.demo.web.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import static com.example.demo.exceptions.ValidationConstants.*;;
@Data
public class CustomerAddRequest {
	@NotBlank(message = USER_NAME_NOT_BLANK)
	private String name;
	@NotBlank(message = USER_EMAIL_NOT_BLANK)
	private String email;
}
