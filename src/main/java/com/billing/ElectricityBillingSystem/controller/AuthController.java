package com.billing.ElectricityBillingSystem.controller;

import com.billing.ElectricityBillingSystem.config.JwtUtils;
import com.billing.ElectricityBillingSystem.dao.UserDAO;
import com.billing.ElectricityBillingSystem.dto.AuthenticationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Tag(name = "Auth API")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDAO userDAO;
    private final JwtUtils jwtUtils;

    @PostMapping("/authenticate")
    @Operation(summary = "Generate an authentication token", description  = "Return an authentication token (user or admin)")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationDTO request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );

        final UserDetails userDetails = userDAO.findUserByEmail(request.getEmail());
        if(userDetails != null){
            return ResponseEntity.ok(jwtUtils.generateToken(userDetails));
        }

        return ResponseEntity.badRequest().body("Some error has occured");
    }

}