package com.frg.autocare.services;

import com.frg.autocare.entities.Car;
import com.frg.autocare.entities.Client;
import com.frg.autocare.repository.CarRepository;
import com.frg.autocare.repository.ClientRepository;
import com.frg.autocare.services.interfaces.IBusinessService;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BusinessService implements IBusinessService {

  private final CarRepository carRepository;
  private final ClientRepository clientRepository;

  @Autowired
  public BusinessService(CarRepository carRepository, ClientRepository clientRepository) {
    this.carRepository = carRepository;
    this.clientRepository = clientRepository;
  }

  @Override
  public Map<String, Object> getAllCarsByClientId(Long clientId) {
    log.info("Searching for cars...");
    List<Car> cars = carRepository.findCarsByClientId(clientId);

    Map<String, Object> serviceResponse = new HashMap<>();
    serviceResponse.put("entities", cars);

    return serviceResponse;
  }

  @Override
  public Map<String, Object> findClientById(Long id) throws EntityNotFoundException {
    log.info("Searching for client...");

    Optional<Client> optionalClient = clientRepository.findById(id);

    if (!optionalClient.isPresent()) {
      throw new EntityNotFoundException("Error searching for client data");
    }

    Client client = optionalClient.get();
    Map<String, Object> serviceResponse = new HashMap<>();

    serviceResponse.put("entity", client);

    return serviceResponse;
  }
}
