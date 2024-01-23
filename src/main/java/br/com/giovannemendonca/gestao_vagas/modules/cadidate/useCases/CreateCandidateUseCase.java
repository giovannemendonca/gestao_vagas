package br.com.giovannemendonca.gestao_vagas.modules.cadidate.useCases;

import br.com.giovannemendonca.gestao_vagas.exceptions.UserFoundException;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.entities.CandidateEntity;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.repositories.CandidateRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {

  private CandidateRepository candidateRepository;

  private PasswordEncoder passwordEncoder;

  CreateCandidateUseCase (CandidateRepository candidateRepository, PasswordEncoder passwordEncoder){
    this.candidateRepository = candidateRepository;
    this.passwordEncoder = passwordEncoder;
  }

 public CandidateEntity execute(CandidateEntity candidateEntity){

   this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
           .ifPresent(candidate -> {
             throw new UserFoundException();
           });

   var password = passwordEncoder.encode(candidateEntity.getPassword());
   candidateEntity.setPassword(password);


   return this.candidateRepository.save(candidateEntity);

  }
}
