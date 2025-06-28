/**
 * AutoCare REST API - Authentication request DTO.
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
package com.frg.autocare.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Authentication request")
public class AuthenticationRequest {

  @Email(message = "Email should be valid")
  @NotBlank(message = "Email is required")
  @Schema(description = "User's email address", example = "john.doe@example.com")
  private String email;

  @NotBlank(message = "Password is required")
  @Schema(description = "User's password", example = "password123")
  private String password;
}
