package com.frg.autocare.services;

import com.frg.autocare.entities.Maintainer;
import com.frg.autocare.repository.MaintainerRepository;
import com.frg.autocare.services.interfaces.IMaintainerService;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MaintainerService implements IMaintainerService {

  private final MaintainerRepository repository;

  @Autowired
  public MaintainerService(MaintainerRepository repository) {
    this.repository = repository;
  }

  @Override
  public Map<String, Object> findById(Long id) {
    log.info("Searching for maintainer...");

    Optional<Maintainer> optionalMaintainer = repository.findById(id);

    if (!optionalMaintainer.isPresent()) {
      throw new EntityNotFoundException("Error searching for maintainer data!");
    }

    Maintainer foundMaintainer = optionalMaintainer.get();
    Map<String, Object> serviceResponse = new HashMap<>();

    serviceResponse.put("entity", foundMaintainer);

    return serviceResponse;
  }
}
