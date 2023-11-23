package br.com.giovannemendonca.gestao_vagas.exceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(){
    super("User Not Found");
  }
}
