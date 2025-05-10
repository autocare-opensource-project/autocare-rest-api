package com.frg.autocare.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {

  @NotNull(message = "model cannot be null") private String model;

  @NotNull(message = "make cannot be null") private String make;

  @NotNull(message = "client id is required") private Long clientId;

  @NotNull(message = "maintainer id is required") private Long maintainerId;
}
