package br.com.giovannemendonca.gestao_vagas.modules.job.useCases;

import br.com.giovannemendonca.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.giovannemendonca.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.giovannemendonca.gestao_vagas.modules.job.entities.JobEntity;
import br.com.giovannemendonca.gestao_vagas.modules.job.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private CompanyRepository companyRepository;

  public JobEntity execute(JobEntity jobEntity) {

    this.companyRepository.findById(jobEntity.getCompanyId())
      .orElseThrow(() -> {
       throw new CompanyNotFoundException();
      });
    return jobRepository.save(jobEntity);

  }

}
