package com.example.demo.dtos;


import java.util.List;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private List<String> roles; 
    private CustomerDto customerDto;
}
