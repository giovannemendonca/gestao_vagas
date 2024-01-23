package br.com.giovannemendonca.gestao_vagas.modules.company.controller;

import br.com.giovannemendonca.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.giovannemendonca.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {

  CreateCompanyUseCase createCompanyUseCase;

  CompanyController(CreateCompanyUseCase createCompanyUseCase) {
    this.createCompanyUseCase = createCompanyUseCase;
  }

  @PostMapping("/")
  public ResponseEntity<Object> create(@Validated @RequestBody CompanyEntity companyEntity) {

    try {
      var result = createCompanyUseCase.execute(companyEntity);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }

  }
}
