package com.frg.autocare.controllers.interfaces;

import com.frg.autocare.dto.CarDTO;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ICarController {

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> getComplexCars();

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> createCar(@RequestBody @Valid CarDTO dto);
}
