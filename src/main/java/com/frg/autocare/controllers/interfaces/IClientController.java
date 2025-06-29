package com.frg.autocare.controllers.interfaces;

import com.frg.autocare.dto.ClientDTO;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IClientController {

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> createClient(@RequestBody @Valid ClientDTO dto);
}
