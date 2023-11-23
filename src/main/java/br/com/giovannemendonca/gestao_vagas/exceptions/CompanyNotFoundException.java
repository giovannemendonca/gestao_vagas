package br.com.giovannemendonca.gestao_vagas.exceptions;

public class CompanyNotFoundException extends RuntimeException{
  
  public CompanyNotFoundException(){
    super("Company Not Found");
  }
}
