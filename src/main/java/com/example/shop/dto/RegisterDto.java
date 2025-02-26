package com.example.shop.dto;

import com.example.shop.enums.GenderEnum;
import com.example.shop.enums.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RegisterDto {

    @NotBlank(message = "Name must not be blank")
    private String name;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email must not be blank")
    private String email;

    @NotBlank(message = "Password must not be blank")
    private String password;

    private Date birthday;
    private GenderEnum gender;
    private RoleEnum role;

}
