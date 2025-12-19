package com.rawat.FinCore.Service;

import com.rawat.FinCore.DTO.AuthResponse;
import com.rawat.FinCore.DTO.LoginRequest;
import com.rawat.FinCore.DTO.RegisterRequest;
import lombok.Data;


public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
