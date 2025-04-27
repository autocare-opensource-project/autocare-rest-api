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

import com.frg.autocare.dto.CarDTO;
import com.frg.autocare.services.CarService;
import java.net.URI;
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

  private final CarService carService;

  @Override
  public ResponseEntity<String> getComplexCars() {
    String jsonString = carService.getAll();
    return ResponseEntity.ok(jsonString);
  }

  @Override
  public ResponseEntity<String> createCar(CarDTO dto) {

    var id = carService.create(dto);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

    return ResponseEntity.created(location).build();
  }
}
