package ru.netology.cloudstorage.service;

import ru.netology.cloudstorage.jwt.JwtTokenUtils;
import ru.netology.cloudstorage.model.AuthRequest;
import ru.netology.cloudstorage.model.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Logger logger = LogManager.getLogger(AuthService.class);
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;

    public ResponseEntity<?> login(AuthRequest authRequest) {
        logger.debug("Login attempt");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            logger.error("Bad credentials");
            return ResponseEntity.badRequest().header("Description", "Bad credentials").build();
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getLogin());
        String token = jwtTokenUtils.generateToken(userDetails);
        logger.info("Login success");
        return ResponseEntity.ok()
                .header("Description", "Success authorization")
                .body(new AuthResponse(token));
    }
}