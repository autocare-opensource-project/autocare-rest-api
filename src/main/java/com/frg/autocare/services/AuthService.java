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

import com.frg.autocare.dto.auth.AuthRequest;
import com.frg.autocare.dto.auth.AuthResponse;
import com.frg.autocare.exception.ResourceNotFoundException;
import com.frg.autocare.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserAccountRepository repository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Value("${app.jwt.expiration}")
  private long jwtExpiration;

  public AuthResponse authenticate(AuthRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.email(), request.password()));

    var user =
        repository
            .findByEmail(request.email())
            .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.email()));

    var jwtToken = jwtService.generateToken(user);

    return new AuthResponse(jwtToken, "Bearer", jwtExpiration / 1000);
  }
}
