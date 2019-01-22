package com.example.authentication.demo.controller;

import com.example.authentication.demo.domain.entity.User;
import com.example.authentication.demo.domain.repository.UserRepository;
import com.example.authentication.demo.dto.AuthenticationResponseDto;
import com.example.authentication.demo.dto.SignUpDto;
import com.example.authentication.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("signUp")
    public
    @ResponseBody
    AuthenticationResponseDto signUp(
            @RequestBody SignUpDto signUpDto) {
        return userService.signUp(signUpDto);
    }

    @PostMapping("signIn")
    public
    @ResponseBody AuthenticationResponseDto signIn(
            @RequestBody SignUpDto signUpDto) {
        return userService.signIn(signUpDto);
    }

    @PostMapping("accessTokenFromRefresh")
    public
    @ResponseBody String getAccessTokenFromRefreshToken(
            @RequestBody String refreshToken) {
        return userService.getAccessTokenFromRefreshToken(refreshToken);
    }


    @GetMapping("list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
