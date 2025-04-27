package com.frg.autocare.services;

import com.frg.autocare.dto.CarDTO;

public interface ICarService {

  String getAll();

  String create(CarDTO dto);
}
