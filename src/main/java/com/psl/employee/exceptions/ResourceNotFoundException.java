package com.psl.employee.exceptions;

public class ResourceNotFoundException extends Exception {
  public ResourceNotFoundException() {}

  public ResourceNotFoundException(String msg) {
    super(msg);
  }
}
