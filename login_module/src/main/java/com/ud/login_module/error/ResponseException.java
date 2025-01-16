package com.ud.login_module.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseException extends RuntimeException {
  int statusCode;
  public ResponseException(String message, int statusCode){
    super(message);
    this.statusCode=statusCode;
  }

}
