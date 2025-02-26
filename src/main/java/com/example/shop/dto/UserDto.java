package com.example.shop.dto;

import com.example.shop.enums.GenderEnum;
import com.example.shop.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;

    private String email;

    private String name;

    private GenderEnum gender;

    private Date birthday;

    private RoleEnum role;



}
