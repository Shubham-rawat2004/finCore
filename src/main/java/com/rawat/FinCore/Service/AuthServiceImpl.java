package com.rawat.FinCore.Service;

import com.rawat.FinCore.DTO.AuthResponse;
import com.rawat.FinCore.DTO.LoginRequest;
import com.rawat.FinCore.DTO.RegisterRequest;
import com.rawat.FinCore.Entities.Customer;
import com.rawat.FinCore.Entities.User;
import com.rawat.FinCore.Enum.Role;
import com.rawat.FinCore.Repository.CustomerRepository;
import com.rawat.FinCore.Repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService; // will be created next step

    public AuthServiceImpl(UserRepository userRepository,
                           CustomerRepository customerRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole()));

        if (request.getCustomerId() != null) {
            Customer customer = customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
            user.setCustomer(customer);
        }

        User saved = userRepository.save(user);
        String token = jwtService.generateToken(saved);

        AuthResponse response = new AuthResponse();
        response.setUsername(saved.getUsername());
        response.setRole(saved.getRole().name());
        response.setAccessToken(token);
        return response;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));

        User user = (User) auth.getPrincipal();
        String token = jwtService.generateToken(user);

        AuthResponse response = new AuthResponse();
        response.setUsername(user.getUsername());
        response.setRole(user.getRole().name());
        response.setAccessToken(token);
        return response;
    }
}
