package com.pomolex.enduromotorcycleguide.domain.exception;

public class UserAlreadyAuthException extends RuntimeException{
  public UserAlreadyAuthException(String message) {
    super(message);
  }

}
