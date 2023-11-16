package br.com.giovannemendonca.gestao_vagas.modules.cadidate.controller;

import br.com.giovannemendonca.gestao_vagas.modules.cadidate.dto.AuthCandidateRequestDTO;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.useCases.AuthCandidateUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public class AuthCandidateController {

  @Autowired
  private AuthCandidateUseCase authCandidateUseCase;

  @PostMapping("/auth")
  public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDTO authCandidateDTO) {
    try {
      var token = this.authCandidateUseCase.execute(authCandidateDTO);
      return ResponseEntity.ok(token);
    }catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}
