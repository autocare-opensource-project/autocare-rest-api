/**
 * AutoCare REST API - Car DTO.
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
package com.frg.autocare.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Car entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Car information")
public class CarDTO {

  @Schema(description = "Car ID")
  private Long id;

  @Schema(description = "Car model", example = "Corolla")
  private String model;

  @Schema(description = "Car make", example = "Toyota")
  private String make;

  @Schema(description = "Client name", example = "John Doe")
  private String clientName;

  @Schema(description = "Maintainer name", example = "Jane Smith")
  private String maintainerName;

  @Schema(description = "Tools used for maintenance")
  private List<ToolDTO> tools;
}
