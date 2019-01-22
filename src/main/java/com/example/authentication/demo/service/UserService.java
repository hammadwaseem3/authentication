package com.example.authentication.demo.service;

import com.example.authentication.demo.domain.entity.Role;
import com.example.authentication.demo.domain.entity.User;
import com.example.authentication.demo.domain.repository.UserRepository;
import com.example.authentication.demo.dto.AuthenticationResponseDto;
import com.example.authentication.demo.dto.SignUpDto;
import com.example.authentication.demo.exception.CustomException;
import com.example.authentication.demo.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponseDto signIn(SignUpDto signUpDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signUpDto.getEmail(), signUpDto.getPassword()));
            return AuthenticationResponseDto.builder()
                    .accessToken(jwtTokenProvider.createToken(signUpDto.getEmail(), userRepository.findByEmail(signUpDto.getEmail()).getRoles()))
                    .refreshToken(jwtTokenProvider.createRefreshToken(signUpDto.getEmail(), userRepository.findByEmail(signUpDto.getEmail()).getRoles()))
                        .build();
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public AuthenticationResponseDto signUp(SignUpDto signUpDto) {
        if (!userRepository.existsByEmail(signUpDto.getEmail())) {
            signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
            User user = new User(signUpDto.getEmail(), signUpDto.getPassword());
            user.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN))); // currently this is hardcoded but I will updated
            userRepository.save(user);
            return AuthenticationResponseDto.builder()
                    .accessToken(jwtTokenProvider.createToken(user.getEmail(), user.getRoles()))
                    .refreshToken(jwtTokenProvider.createRefreshToken(user.getEmail(), user.getRoles()))
                        .build();
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String getAccessTokenFromRefreshToken(String refreshToken) {
        if (refreshToken != null && jwtTokenProvider.validateRefreshToken(refreshToken)) {
             String userName = jwtTokenProvider.getUsernameFromRefreshToken(refreshToken);
             User user = userRepository.findByEmail(userName);
             return jwtTokenProvider.createToken(user.getEmail(), user.getRoles());
        } else {
            throw new CustomException("Invalid Refresh Token", HttpStatus.FORBIDDEN);
        }
    }
}
