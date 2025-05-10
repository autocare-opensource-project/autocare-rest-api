package com.frg.autocare.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frg.autocare.dto.ClientDTO;
import com.frg.autocare.entities.Car;
import com.frg.autocare.entities.Client;
import com.frg.autocare.repository.ClientRepository;
import com.frg.autocare.services.exceptions.CarServiceException;
import com.frg.autocare.services.interfaces.IBusinessService;
import com.frg.autocare.services.interfaces.IClientService;
import com.frg.autocare.technical.ModelObject;
import com.frg.autocare.technical.ModelObjectBuilder;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClientService implements IClientService {

  private final ClientRepository repository;
  private final IBusinessService helperService;
  private final ObjectMapper mapper = new ObjectMapper();

  @Autowired
  public ClientService(ClientRepository repository, IBusinessService helperService) {
    this.repository = repository;
    this.helperService = helperService;
  }

  @Override
  public String getAll() {

    List<Client> clients = repository.findAll();
    List<ModelObject> clientList = new ArrayList<>();

    for (Client client : clients) {

      ModelObjectBuilder clientBuilder =
          ModelObjectBuilder.createBuilder()
              .addAttribute("id", client.getId())
              .addAttribute("name", client.getName());

      Map<String, Object> allCarsByClientId = helperService.getAllCarsByClientId(client.getId());

      List<Car> cars = (List<Car>) allCarsByClientId.get("entities");

      ModelObject[] carsBuilder =
          cars.stream()
              .map(
                  car ->
                      ModelObjectBuilder.createBuilder()
                          .addAttribute("id", car.getId())
                          .addAttribute("model", car.getModel())
                          .addAttribute("make", car.getMake())
                          .build())
              .toArray(ModelObject[]::new);

      clientBuilder.addArrayAttribute("cars", carsBuilder).build();

      ModelObject finalBuilder =
          ModelObjectBuilder.createBuilder().addAttribute("client", clientBuilder.build()).build();

      clientList.add(finalBuilder);
    }

    try {
      return this.mapper.writeValueAsString(clientList);
    } catch (Exception e) {
      throw new RuntimeException("Error converting to JSON", e);
    }
  }

  @Override
  public Map<String, Object> findById(Long id) {

    log.info("Searching for client...");

    Optional<Client> optionalClient = repository.findById(id);

    if (!optionalClient.isPresent()) {
      throw new EntityNotFoundException("Client not found!");
    }

    Client client = optionalClient.get();
    Map<String, Object> serviceResponse = new HashMap<>();

    serviceResponse.put("entity", client);

    return serviceResponse;
  }

  @Override
  public Map<String, Object> createClient(ClientDTO dto) throws CarServiceException {

    String name = dto.getName();
    Client newClient = new Client();
    newClient.setName(name);
    Client saved = repository.save(newClient);

    Map<String, Object> serviceResponse = new HashMap<>();

    ModelObject body =
        ModelObjectBuilder.createBuilder().addAttribute("name", saved.getName()).build();

    try {
      var valueAsString = this.mapper.writeValueAsString(body);
      serviceResponse.put("id", saved.getId().toString());
      serviceResponse.put("body", valueAsString);
      return serviceResponse;
    } catch (Exception e) {
      throw new CarServiceException("Error converting to JSON", e);
    }
  }
}
