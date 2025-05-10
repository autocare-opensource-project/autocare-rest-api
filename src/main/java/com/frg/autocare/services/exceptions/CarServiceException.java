package com.frg.autocare.services.exceptions;

public class CarServiceException extends Exception {

  public CarServiceException(String message, Exception ex) {
    super(message, ex);
  }
}
