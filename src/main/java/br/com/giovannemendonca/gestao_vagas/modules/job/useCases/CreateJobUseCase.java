package br.com.giovannemendonca.gestao_vagas.modules.job.useCases;

import br.com.giovannemendonca.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.giovannemendonca.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.giovannemendonca.gestao_vagas.modules.job.entities.JobEntity;
import br.com.giovannemendonca.gestao_vagas.modules.job.repositories.JobRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

  private JobRepository jobRepository;

  private CompanyRepository companyRepository;

  CreateJobUseCase(JobRepository jobRepository, CompanyRepository companyRepository) {
    this.jobRepository = jobRepository;
    this.companyRepository = companyRepository;
  }

  public JobEntity execute(JobEntity jobEntity) {

     this.companyRepository.findById(jobEntity.getCompanyId())
      .orElseThrow(CompanyNotFoundException::new);
    return jobRepository.save(jobEntity);

  }

}
