package com.rawat.FinCore.Service;

import com.rawat.FinCore.Entities.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(User user);
    String extractUsername(String token);
    boolean isTokenValid(String token, UserDetails userDetails);

    // *** ADD THIS ONE LINE ***
    Long extractCustomerId(String token);
}
