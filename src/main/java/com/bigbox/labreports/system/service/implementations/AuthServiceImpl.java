package com.bigbox.labreports.system.service.implementations;
import com.bigbox.labreports.system.core.entity.User;
import com.bigbox.labreports.system.core.results.*;
import com.bigbox.labreports.system.entity.dtos.user.UserForLoginRequest;
import com.bigbox.labreports.system.entity.dtos.user.UserForRegisterRequest;
import com.bigbox.labreports.system.repository.UserRepository;
import com.bigbox.labreports.system.core.security.JwtUtil;
import com.bigbox.labreports.system.service.contracts.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           JwtUtil jwtUtil,
                           UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Result register(UserForRegisterRequest userForRegister) {
        if (userRepository.findByUsername(userForRegister.getUsername()) != null) {
            return new ErrorResult("Username is already taken");
        }

        User user = new User();
        user.setUsername(userForRegister.getUsername());
        user.setEmail(userForRegister.getEmail());
        user.setPassword(passwordEncoder.encode(userForRegister.getPassword()));

        userRepository.save(user);
        return new SuccessResult("User registered successfully");
    }

    @Override
    public DataResult<String> login(UserForLoginRequest userForLogin) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userForLogin.getUsername(), userForLogin.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findByUsername(userForLogin.getUsername());
            List<String> roles = userRepository.findRolesByUserId(user.getUserId());

            String token = jwtUtil.generateToken(user.getUsername(), roles);
            return new SuccessDataResult<>(token, "Login successful");
        } catch (Exception e) {
            return new ErrorDataResult<>("Invalid username or password");
        }
    }
}
