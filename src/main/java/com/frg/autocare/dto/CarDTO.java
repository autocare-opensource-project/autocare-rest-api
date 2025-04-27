package com.frg.autocare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {

  private String model;
  private String make;
  private Long clientId;
  private Long maintainerId;
}
