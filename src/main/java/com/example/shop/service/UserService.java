package com.example.shop.service;


import com.example.shop.dto.UserDto;
import com.example.shop.entity.User;

public interface UserService {
        UserDto updateUser(UserDto userDto, long id);

        void deleteUser(long id);

        UserDto getUserById(long id);

        User getUserByEmail(String email);

        public void updateUserToken(String token, String email);
    }


