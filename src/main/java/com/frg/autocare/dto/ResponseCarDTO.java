package com.frg.autocare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCarDTO {

  private String model;
  private String make;
  private String client;
  private String maintainer;
}
