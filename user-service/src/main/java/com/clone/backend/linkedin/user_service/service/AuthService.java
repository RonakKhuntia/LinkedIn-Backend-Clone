package com.clone.backend.linkedin.user_service.service;

import com.clone.backend.linkedin.user_service.entity.User;
import com.clone.backend.linkedin.user_service.exception.BadRequestException;
import com.clone.backend.linkedin.user_service.exception.ResourceNotFoundException;
import com.clone.backend.linkedin.user_service.model.LoginDto;
import com.clone.backend.linkedin.user_service.model.SignupDto;
import com.clone.backend.linkedin.user_service.model.UserDto;
import com.clone.backend.linkedin.user_service.repository.UserRepository;
import com.clone.backend.linkedin.user_service.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UserDto signup(SignupDto signupDto) {
        boolean exists = userRepository.existsByEmail(signupDto.getEmail());
        if(exists) {
            throw new BadRequestException("User already exists, cannot signup again.");
        }
        User user = modelMapper.map(signupDto, User.class);
        user.setPassword(PasswordUtil.hashPassword(signupDto.getPassword()));
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    public String login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() ->  new ResourceNotFoundException("User not found with email : " + loginDto.getEmail()));
        boolean isPasswordMatch = PasswordUtil.checkPassword(loginDto.getPassword(), user.getPassword());
        if(!isPasswordMatch) {
            throw new BadRequestException("Invalid password");
        }
        return jwtService.generateAccessToken(user);
    }
}
