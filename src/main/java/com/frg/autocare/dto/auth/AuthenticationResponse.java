/**
 * AutoCare REST API - Authentication response DTO.
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

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication response")
public record AuthenticationResponse(
    @JsonProperty("access_token") @Schema(description = "JWT access token") String accessToken,
    @JsonProperty("token_type") @Schema(description = "Token type", example = "Bearer")
        String tokenType,
    @JsonProperty("expires_in") @Schema(description = "Token expiration time in seconds")
        Long expiresIn) {}
