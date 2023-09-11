package com.example.bookstore.service;

import com.example.bookstore.dto.user.CreateUserRequestDto;
import com.example.bookstore.dto.user.UserDto;
import com.example.bookstore.dto.user.UserRegistrationRequestDto;
import com.example.bookstore.dto.user.UserRegistrationResponseDto;
import com.example.bookstore.exceptions.RegistrationException;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException;

    UserDto save(CreateUserRequestDto requestDto);

    List<UserDto> findAll(Pageable pageable);

    UserDto getById(Long id);
}
