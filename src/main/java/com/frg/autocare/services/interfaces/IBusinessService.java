package com.frg.autocare.services.interfaces;

import jakarta.persistence.EntityNotFoundException;
import java.util.Map;

public interface IBusinessService {

  Map<String, Object> getAllCarsByClientId(Long clientId);

  Map<String, Object> findClientById(Long id) throws EntityNotFoundException;
}
