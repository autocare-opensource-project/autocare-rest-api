/**
 * AutoCare REST API - Car service component.
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
package com.frg.autocare.services;

import com.frg.autocare.dto.CarDTO;
import com.frg.autocare.dto.ToolDTO;
import com.frg.autocare.entities.Car;
import com.frg.autocare.entities.Tool;
import com.frg.autocare.exception.ResourceNotFoundException;
import com.frg.autocare.repository.CarRepository;
import com.frg.autocare.repository.ToolRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarService {

  private final CarRepository carRepository;
  private final ToolRepository toolRepository;

  @Transactional(readOnly = true)
  public List<CarDTO> getAllCars() {
    List<Car> cars = carRepository.findAll();

    return cars.stream().map(this::mapToCarDTO).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public CarDTO getCarById(Long id) {
    Car car =
        carRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Car", "id", id));

    return mapToCarDTO(car);
  }

  private CarDTO mapToCarDTO(Car car) {
    // Safely handle potential null references
    String clientName = car.getCustomer() != null ? car.getCustomer().getName() : null;
    String maintainerName = car.getMaintainer() != null ? car.getMaintainer().getName() : null;
    Long maintainerId = car.getMaintainer() != null ? car.getMaintainer().getId() : null;

    List<ToolDTO> tools =
        maintainerId != null
            ? toolRepository.findToolsByMaintainerId(maintainerId).stream()
                .map(this::mapToToolDTO)
                .collect(Collectors.toList())
            : List.of();

    return new CarDTO(
        car.getId(), car.getModel(), car.getMake(), clientName, maintainerName, tools);
  }

  private ToolDTO mapToToolDTO(Tool tool) {
    return new ToolDTO(tool.getId(), tool.getName());
  }
}
