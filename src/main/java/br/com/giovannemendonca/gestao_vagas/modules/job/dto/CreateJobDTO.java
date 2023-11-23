package br.com.giovannemendonca.gestao_vagas.modules.job.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobDTO {

  @Schema(example = "Vaga para desenvolvedor júnior", requiredMode = Schema.RequiredMode.REQUIRED)
  private String description;

  @Schema(example = "Plano de saúde, vale transporte, vale refeição", requiredMode = Schema.RequiredMode.REQUIRED)
  private String benefits;

  @Schema(example = "Júnior", requiredMode = Schema.RequiredMode.REQUIRED)
  private String level;
}
