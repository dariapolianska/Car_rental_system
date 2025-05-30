package com.university.car_rental.service;


import com.university.car_rental.domain.UserDTO;
import com.university.car_rental.domain.auth.LoginDTO;
import com.university.car_rental.domain.auth.RegisterDTO;

public interface AuthService {
    UserDTO loginUser(LoginDTO request);

    UserDTO registerUser(RegisterDTO request);
    String generateToken(UserDTO user);
}
