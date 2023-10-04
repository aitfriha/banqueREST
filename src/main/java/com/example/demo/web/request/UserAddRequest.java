package com.example.demo.web.request;
import java.util.List;
import lombok.Data;

@Data
public class UserAddRequest {
    private String username;
    private String password;
    private List<String> roles; 
    private String customerId;
}
