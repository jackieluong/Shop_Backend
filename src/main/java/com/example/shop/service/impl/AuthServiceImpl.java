package com.example.shop.service.impl;

import com.example.shop.constant.RoleEnum;
import com.example.shop.dto.LoginDto;
import com.example.shop.dto.RegisterDto;
import com.example.shop.dto.UserDto;
import com.example.shop.entity.User;
import com.example.shop.repository.UserRepository;
import com.example.shop.security.JwtTokenProvider;
import com.example.shop.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto, User user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword());
        Authentication authentication= authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtTokenProvider.createAccessToken(user);


        return jwtToken;


    }

    @Override
    public UserDto register(RegisterDto registerDto) {
        // add check for email exists in database
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new RuntimeException("Email is already exist");
        }

        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setName(registerDto.getName());
        user.setBirthday(registerDto.getBirthday());
        user.setGender(registerDto.getGender());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(registerDto.getRole());


//        Role userRole = roleRepository.findByName("USER").get();
//        user.getRoles().add(userRole);

        User addedUser =  userRepository.save(user);
        return modelMapper.map(addedUser, UserDto.class);
    }
}
