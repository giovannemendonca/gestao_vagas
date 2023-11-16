package br.com.giovannemendonca.gestao_vagas.modules.company.useCases;

import br.com.giovannemendonca.gestao_vagas.exceptions.UserFoundException;
import br.com.giovannemendonca.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.giovannemendonca.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

  @Autowired
  CompanyRepository companyRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  public CompanyEntity execute(CompanyEntity companyEntity) {
    this.companyRepository.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
        .ifPresent(company -> {
          throw new UserFoundException();
        });
    companyRepository.save(companyEntity);

    var password = passwordEncoder.encode(companyEntity.getPassword());
    companyEntity.setPassword(password);

    return this.companyRepository.save(companyEntity);
  }
}
