package br.com.giovannemendonca.gestao_vagas.modules.cadidate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthCandidateResponseDTO {

  private String access_token;

  private Long expires_in;

}
