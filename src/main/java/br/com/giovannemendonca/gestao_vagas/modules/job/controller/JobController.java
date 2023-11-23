package br.com.giovannemendonca.gestao_vagas.modules.job.controller;

import br.com.giovannemendonca.gestao_vagas.modules.job.dto.CreateJobDTO;
import br.com.giovannemendonca.gestao_vagas.modules.job.entities.JobEntity;
import br.com.giovannemendonca.gestao_vagas.modules.job.useCases.CreateJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company/job")
public class JobController {

  @Autowired
  private CreateJobUseCase createJobUseCase;

  @PostMapping("/")
  @PreAuthorize("hasRole('ROLE_COMPANY')")
  @Tag(name = "Vagas", description = "Informações das vagas")
  @Operation(summary = "Cadastro de vagas", description = "essa função é responsável por cadastrar uma nova vaga na plataforma")
  @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = JobEntity.class))})
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> create(@Validated @RequestBody CreateJobDTO jobDTO, HttpServletRequest request) {

    var companyId = request.getAttribute("company_id");

    try {
      
    var jobEntity = JobEntity.builder()
            .benefits(jobDTO.getBenefits())
            .level(jobDTO.getLevel())
            .companyId(UUID.fromString(companyId.toString()))
            .description(jobDTO.getDescription())
            .build();
             jobEntity.setCompanyId(UUID.fromString(companyId.toString()));

    var result =  this.createJobUseCase.execute(jobEntity);
    return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }

   
  }
}
