package br.com.giovannemendonca.gestao_vagas.modules.cadidate.useCases;

import br.com.giovannemendonca.gestao_vagas.modules.cadidate.dto.AuthCandidateRequestDTO;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.dto.AuthCandidateResponseDTO;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.repositories.CandidateRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCandidateUseCase {

  @Value("${security.token.secret.candidate}")
  private String secretKey;

  @Autowired
  CandidateRepository candidateRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {

    var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
            .orElseThrow(() -> new UsernameNotFoundException("Username/Password invalid"));

    var passwordMatches = this.passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());

    if (!passwordMatches) {
      throw new AuthenticationException("Username/Password invalid");
    }

    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    var expiresIn = Instant.now().plus(Duration.ofMinutes(30));

    var token = JWT.create()
            .withIssuer("javagas")
            .withExpiresAt(expiresIn)
            .withClaim("roles", Arrays.asList("CANDIDATE"))
            .withSubject(candidate.getId().toString())
            .sign(algorithm);

    var authCandidateResponse = AuthCandidateResponseDTO.builder()
            .access_token(token)
            .expires_in(expiresIn.toEpochMilli()) // esse toEpochMilli() é para retornar em milisegundos
            .build();

    return authCandidateResponse;

  }

}
