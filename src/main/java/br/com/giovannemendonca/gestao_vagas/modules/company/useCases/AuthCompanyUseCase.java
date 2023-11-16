package br.com.giovannemendonca.gestao_vagas.modules.company.useCases;

import br.com.giovannemendonca.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.giovannemendonca.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.giovannemendonca.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCompanyUseCase {

  @Value("${security.token.secret}")
  private String secretKey;

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {

    var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername())
            .orElseThrow(() -> new AuthenticationException("username/password invalid"));

    var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

    if (!passwordMatches) {
      throw new AuthenticationException("username/password invalid");
    }

    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    var expiresAt = Instant.now().plus(Duration.ofHours(2));

    var token = JWT.create()
            .withIssuer("javagas")
            .withSubject(company.getId().toString())
            .withClaim("roles", Arrays.asList("COMPANY"))
            .withExpiresAt(expiresAt)
            .sign(algorithm);

    var authCompanyResponseDTO = AuthCompanyResponseDTO.builder()
            .access_token(token)
            .expires_in(expiresAt.getEpochSecond())
            .build();

    return authCompanyResponseDTO;

  }

}
