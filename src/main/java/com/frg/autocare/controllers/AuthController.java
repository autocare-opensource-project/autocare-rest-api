/**
 * AutoCare REST API - Authentication controller.
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
package com.frg.autocare.controllers;

import com.frg.autocare.dto.auth.AuthenticationRequest;
import com.frg.autocare.dto.auth.AuthenticationResponse;
import com.frg.autocare.dto.auth.RegisterRequest;
import com.frg.autocare.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

  private final AuthenticationService service;

  @PostMapping("/register")
  @Operation(
      summary = "Register a new user",
      description = "Register a new user account with email and password")
  @ApiResponse(responseCode = "200", description = "User registered successfully")
  @ApiResponse(responseCode = "400", description = "Invalid input data")
  @ApiResponse(responseCode = "409", description = "Email already exists")
  public ResponseEntity<AuthenticationResponse> register(
      @Valid @RequestBody RegisterRequest request) {
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/authenticate")
  @Operation(
      summary = "Authenticate user",
      description = "Authenticate user with email and password")
  @ApiResponse(responseCode = "200", description = "Authentication successful")
  @ApiResponse(responseCode = "401", description = "Invalid credentials")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @Valid @RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(service.authenticate(request));
  }
}
