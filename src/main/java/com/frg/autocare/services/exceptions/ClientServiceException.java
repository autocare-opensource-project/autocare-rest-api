package com.frg.autocare.services.exceptions;

public class ClientServiceException extends Exception {

  public ClientServiceException(String message, Exception ex) {
    super(message, ex);
  }
}
