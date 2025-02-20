package com.example.shop.service;

import com.example.shop.dto.LoginDto;
import com.example.shop.dto.RegisterDto;
import com.example.shop.dto.UserDto;
import com.example.shop.entity.User;

public interface AuthService {
    String login(LoginDto loginDto, User user);

    UserDto register(RegisterDto registerDto);

}
