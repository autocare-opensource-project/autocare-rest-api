package com.frg.autocare.services.interfaces;

import com.frg.autocare.dto.ClientDTO;
import com.frg.autocare.services.exceptions.ClientServiceException;
import java.util.Map;

public interface IClientService {

  String getAll();

  Map<String, Object> createClient(ClientDTO dto) throws ClientServiceException;
}
