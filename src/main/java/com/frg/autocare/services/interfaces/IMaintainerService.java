package com.frg.autocare.services.interfaces;

import java.util.Map;

public interface IMaintainerService {

  Map<String, Object> findById(Long id);
}
