package com.example.bookstore.service;

import com.example.bookstore.dto.user.UserRegistrationRequestDto;
import com.example.bookstore.dto.user.UserRegistrationResponseDto;
import com.example.bookstore.exceptions.RegistrationException;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;

public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException;

    ShoppingCart getShoppingCartForCurrentUser();

    User getCurrentUser();
}
