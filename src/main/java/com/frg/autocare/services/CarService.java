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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frg.autocare.dto.CarDTO;
import com.frg.autocare.entities.Car;
import com.frg.autocare.entities.Client;
import com.frg.autocare.entities.Maintainer;
import com.frg.autocare.entities.Tool;
import com.frg.autocare.repository.CarRepository;
import com.frg.autocare.repository.ToolRepository;
import com.frg.autocare.services.exceptions.CarServiceException;
import com.frg.autocare.services.interfaces.IBusinessService;
import com.frg.autocare.services.interfaces.ICarService;
import com.frg.autocare.services.interfaces.IMaintainerService;
import com.frg.autocare.technical.ModelObject;
import com.frg.autocare.technical.ModelObjectBuilder;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CarService implements ICarService {

  private final CarRepository carRepository;

  private final ToolRepository toolRepository;

  private final IBusinessService businessService;

  private final IMaintainerService maintainerService;

  private final ObjectMapper mapper = new ObjectMapper();

  @Autowired
  public CarService(
      CarRepository carRepository,
      ToolRepository toolRepository,
      IBusinessService businessService,
      IMaintainerService maintainerService) {
    this.carRepository = carRepository;
    this.toolRepository = toolRepository;
    this.businessService = businessService;
    this.maintainerService = maintainerService;
  }

  @Override
  public String getAll() throws CarServiceException {
    List<Car> cars = carRepository.findAll();
    List<ModelObject> carList = new ArrayList<>();

    for (Car car : cars) {
      ModelObjectBuilder carBuilder =
          ModelObjectBuilder.createBuilder()
              .addAttribute("id", car.getId())
              .addAttribute("model", car.getModel())
              .addAttribute("make", car.getMake())
              .addAttribute("clientName", car.getClient().getName())
              .addAttribute("maintainerName", car.getMaintainer().getName());

      List<Tool> tools = toolRepository.findToolsByMaintainerId(car.getMaintainer().getId());
      ModelObject[] toolBuilders =
          tools.stream()
              .map(
                  tool ->
                      ModelObjectBuilder.createBuilder()
                          .addAttribute("id", tool.getId())
                          .addAttribute("name", tool.getName())
                          .build())
              .toArray(ModelObject[]::new);

      carBuilder.addArrayAttribute("tools", toolBuilders);

      ModelObject finalBuilder =
          ModelObjectBuilder.createBuilder().addAttribute("car", carBuilder.build()).build();

      carList.add(finalBuilder);
    }

    try {
      return this.mapper.writeValueAsString(carList);
    } catch (JsonProcessingException e) {
      String exceptionMessage = "Error converting to JSON ";
      log.error(exceptionMessage + e.getMessage());
      throw new CarServiceException(exceptionMessage, e);
    }
  }

  @Override
  @Transactional
  public Map<String, Object> create(CarDTO dto) throws CarServiceException {

    Long clientId = dto.getClientId();
    Long maintainerId = dto.getMaintainerId();
    Map<String, Object> serviceResponse = new HashMap<>();
    try {
      var clientServiceResponse = businessService.findClientById(clientId);
      Client foundClient = (Client) clientServiceResponse.get("entity");

      var maintainerServiceResponse = maintainerService.findById(maintainerId);
      Maintainer foundMaintainer = (Maintainer) maintainerServiceResponse.get("entity");

      Car newCar = new Car();
      newCar.setModel(dto.getModel());
      newCar.setMake(dto.getMake());
      newCar.setClient(foundClient);
      newCar.setMaintainer(foundMaintainer);

      var saved = carRepository.save(newCar);
      var id = saved.getId().toString();

      ModelObjectBuilder newCarSaved =
          ModelObjectBuilder.createBuilder()
              .addAttribute("model", saved.getModel())
              .addAttribute("make", saved.getMake());

      ModelObject body = newCarSaved.build();

      var valueAsString = this.mapper.writeValueAsString(body);
      serviceResponse.put("id", id);
      serviceResponse.put("body", valueAsString);
      return serviceResponse;
    } catch (JsonProcessingException e) {
      String exceptionMessage = "Error converting to JSON";
      log.error(exceptionMessage + e.getMessage());
      throw new CarServiceException(exceptionMessage, e);
    } catch (EntityNotFoundException e) {
      String exceptionMessage = "Entity not found: " + e.getMessage();
      log.error(exceptionMessage);
      throw new CarServiceException(exceptionMessage, e);
    }
  }
}
