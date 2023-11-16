package br.com.giovannemendonca.gestao_vagas.modules.job.controller;

import br.com.giovannemendonca.gestao_vagas.modules.job.dto.CreateJobDTO;
import br.com.giovannemendonca.gestao_vagas.modules.job.entities.JobEntity;
import br.com.giovannemendonca.gestao_vagas.modules.job.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
  public JobEntity create(@Validated @RequestBody CreateJobDTO jobDTO, HttpServletRequest request) {

    var companyId = request.getAttribute("company_id");

    var jobEntity = JobEntity.builder()
            .benefits(jobDTO.getBenefits())
            .level(jobDTO.getLevel())
            .companyId(UUID.fromString(companyId.toString()))
            .description(jobDTO.getDescription())
            .build();

    jobEntity.setCompanyId(UUID.fromString(companyId.toString()));

    return this.createJobUseCase.execute(jobEntity);
  }
}
