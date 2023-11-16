package br.com.giovannemendonca.gestao_vagas.exceptions;

public class UserFoundException extends RuntimeException {

  public UserFoundException() {
    super("O usuário já existe");
  }

}
