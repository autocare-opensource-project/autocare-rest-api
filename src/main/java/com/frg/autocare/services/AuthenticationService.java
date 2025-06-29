/**
 * AutoCare REST API - Authentication service.
 * Copyright (C) 2024  AutoCare REST API original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this application.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.frg.autocare.services;

import com.frg.autocare.dto.auth.AuthenticationRequest;
import com.frg.autocare.dto.auth.AuthenticationResponse;
import com.frg.autocare.dto.auth.RegisterRequest;
import com.frg.autocare.entities.User;
import com.frg.autocare.exception.ResourceAlreadyExistsException;
import com.frg.autocare.exception.ResourceNotFoundException;
import com.frg.autocare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Value("${app.jwt.expiration}")
  private long jwtExpiration;

  public AuthenticationResponse register(RegisterRequest request) {
    // Check if user already exists
    if (repository.findByEmail(request.email()).isPresent()) {
      throw new ResourceAlreadyExistsException("User", "email", request.email());
    }

    var user =
        User.builder()
            .name(request.name())
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .role(request.role())
            .build();

    repository.save(user);

    var jwtToken = jwtService.generateToken(user);

    return new AuthenticationResponse(jwtToken, "Bearer", jwtExpiration / 1000);
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.email(), request.password()));

    var user =
        repository
            .findByEmail(request.email())
            .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.email()));

    var jwtToken = jwtService.generateToken(user);

    return new AuthenticationResponse(jwtToken, "Bearer", jwtExpiration / 1000);
  }
}
