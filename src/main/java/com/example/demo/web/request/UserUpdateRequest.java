package com.example.demo.web.request;
import java.util.List;
import lombok.Data;

@Data
public class UserUpdateRequest {
    private Long id;
    private String username;
    private String password;
    private List<String> roles; 
}
