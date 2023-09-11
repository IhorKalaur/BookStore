package com.example.bookstore.dto.user;

import com.example.bookstore.model.Role;
import java.util.Set;
import lombok.Data;

@Data
public class CreateUserRequestDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String shippingAddress;
    private Set<Role> roles;
}
