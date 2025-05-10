package com.frg.autocare.services.interfaces;

import com.frg.autocare.dto.CarDTO;
import com.frg.autocare.services.exceptions.CarServiceException;
import java.util.Map;

public interface ICarService {

  String getAll() throws CarServiceException;

  Map<String, Object> create(CarDTO dto) throws CarServiceException;
}
