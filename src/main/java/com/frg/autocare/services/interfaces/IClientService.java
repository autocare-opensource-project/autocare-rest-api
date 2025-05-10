package com.frg.autocare.services.interfaces;

import com.frg.autocare.dto.ClientDTO;
import com.frg.autocare.services.exceptions.CarServiceException;
import java.util.Map;

public interface IClientService {

  String getAll();

  Map<String, Object> findById(Long id);

  Map<String, Object> createClient(ClientDTO dto) throws CarServiceException;
}
