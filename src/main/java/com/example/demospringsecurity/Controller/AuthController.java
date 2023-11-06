package com.example.demospringsecurity.Controller;

import com.example.demospringsecurity.DTO.AuthRequest;
import com.example.demospringsecurity.DTO.JwtResponse;
import com.example.demospringsecurity.DTO.RefreshTokenRequest;
import com.example.demospringsecurity.Entity.RefreshToken;
import com.example.demospringsecurity.Entity.User;
import com.example.demospringsecurity.Service.JwtService;
import com.example.demospringsecurity.Service.RefreshTokenService;
import com.example.demospringsecurity.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public JwtResponse authenticateAndGetToken(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if(authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getUsername());
            return JwtResponse.builder()
                    .accessToken(jwtService.generateToken(request.getUsername()))
                    .token(refreshToken.getToken())
                    .build();
        } else {
            throw new UsernameNotFoundException("invalid user request");
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user.getName());
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequest.getToken())
                            .build();
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database"
                ));
    }

    @PostMapping("/signUp")
    public String addNewUser(@RequestBody User user) {
        return userService.addNewUser(user);
    }


}
