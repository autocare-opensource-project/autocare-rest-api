package com.frg.autocare.controllers;

import com.frg.autocare.controllers.interfaces.IClientController;
import com.frg.autocare.dto.ClientDTO;
import com.frg.autocare.services.exceptions.ClientServiceException;
import com.frg.autocare.services.interfaces.IClientService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController implements IClientController {

  private final IClientService clientService;

  @Override
  public ResponseEntity<String> createClient(ClientDTO dto) {

    try {
      var serviceResponse = clientService.createClient(dto);

      var id = (String) serviceResponse.get("id");
      var body = (String) serviceResponse.get("body");

      URI location =
          ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

      return ResponseEntity.created(location).body(body);

    } catch (ClientServiceException e) {
      return ResponseEntity.internalServerError().build();
    }
  }
}
