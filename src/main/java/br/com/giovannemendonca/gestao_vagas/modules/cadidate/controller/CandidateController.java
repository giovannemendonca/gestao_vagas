package br.com.giovannemendonca.gestao_vagas.modules.cadidate.controller;

import br.com.giovannemendonca.gestao_vagas.modules.cadidate.dto.ProfileCandidateResponseDTO;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.entities.CandidateEntity;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.useCases.CreateCandidateUseCase;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.useCases.ListAllJobsByFilterUseCase;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.useCases.ProfileCandidateUseCase;
import br.com.giovannemendonca.gestao_vagas.modules.job.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidato")
public class CandidateController {

  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Autowired
  private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;


  @PostMapping("/")
  @Operation(summary = "Cadastrar candidato", description = "essa função cadastra um novo candidato")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Candidato cadastrado com sucesso", content = {
                  @Content(schema = @Schema(implementation = CandidateEntity.class))
          }),
          @ApiResponse(responseCode = "400", description = "Erro ao cadastrar candidato")
  })
  public ResponseEntity<Object> create(@Validated @RequestBody CandidateEntity candidateEntity) {


    try {
      var result = this.createCandidateUseCase.execute(candidateEntity);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(summary = "Perfil do candidato", description = "essa função retorna todas as informações do candidato")
  @SecurityRequirement(name = "jwt_auth")
  @ApiResponses({
          @ApiResponse(responseCode = "200", content = {
                  @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
          }),
          @ApiResponse(responseCode = "400", description = "User not found")
  })
  public ResponseEntity<Object> get(HttpServletRequest request) {

    var idCandidate = request.getAttribute("candidate_id");

    try {
      var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
      return ResponseEntity.ok(profile);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/job")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(summary = "Listar vagas disponíveis para candidato", description = "essa função retorna todas as vagas disponíveis por filtro")
  @SecurityRequirement(name = "jwt_auth")
  @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))})
  public List<JobEntity> findJobByFilter(@RequestParam String filter) {
    return this.listAllJobsByFilterUseCase.execute(filter);
  }
}
