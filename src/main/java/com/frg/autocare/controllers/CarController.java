/**
 * AutoCare REST API - Car controller class.
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

import com.frg.autocare.controllers.interfaces.ICarController;
import com.frg.autocare.dto.CarDTO;
import com.frg.autocare.services.exceptions.CarServiceException;
import com.frg.autocare.services.interfaces.ICarService;
import java.net.URI;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController implements ICarController {

  private final ICarService carService;

  @Override
  public ResponseEntity<String> getComplexCars() {
    String jsonString = null;
    try {
      jsonString = carService.getAll();
      return ResponseEntity.ok(jsonString);
    } catch (CarServiceException e) {
      log.error("Error: {}", e.getMessage());
      return ResponseEntity.internalServerError().build();
    }
  }

  @Override
  public ResponseEntity<String> createCar(CarDTO dto) {

    Map<String, Object> serviceResponse = null;
    try {

      serviceResponse = carService.create(dto);
      var id = (String) serviceResponse.get("id");
      var body = (String) serviceResponse.get("body");

      URI location =
          ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

      return ResponseEntity.created(location).body(body);
    } catch (CarServiceException e) {
      log.error("Error: {}", e.getMessage());
      return ResponseEntity.internalServerError().build();
    }
  }
}
