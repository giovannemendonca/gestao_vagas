package br.com.giovannemendonca.gestao_vagas.modules.cadidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {

  private UUID id;

  @Schema(example = "Giovanne Mendonça")
  private String name;

  @Schema(example ="giovanne.mendonca")
  private String username;

  @Schema(example = "giovanne.mendonca@dev.com" )
  private String email;

  @Schema(example = "Desenvolvedor Java")
  private String description;
}
