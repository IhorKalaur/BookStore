package com.example.bookstore.service.impl;

import com.example.bookstore.dto.user.CreateUserRequestDto;
import com.example.bookstore.dto.user.UserDto;
import com.example.bookstore.dto.user.UserRegistrationRequestDto;
import com.example.bookstore.dto.user.UserRegistrationResponseDto;
import com.example.bookstore.exceptions.EntityNotFoundException;
import com.example.bookstore.exceptions.RegistrationException;
import com.example.bookstore.mapper.UserMapper;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Can't register user with this email "
                    + requestDto.getEmail());
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setEmail(requestDto.getEmail());
        return userMapper.toResponseDto(userRepository.save(user));
    }

    @Override
    public UserDto save(CreateUserRequestDto requestDto) {
        return userMapper.toDto(
                userRepository.save(
                        userMapper.toModel(requestDto)));
    }

    @Override
    public List<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).stream()
                .map(user -> userMapper.toDto(user))
                .toList();
    }

    @Override
    public UserDto getById(Long id) {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cant get user by id: " + id)));
    }
}
