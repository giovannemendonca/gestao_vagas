package br.com.giovannemendonca.gestao_vagas.modules.cadidate.useCases;

import br.com.giovannemendonca.gestao_vagas.modules.job.entities.JobEntity;
import br.com.giovannemendonca.gestao_vagas.modules.job.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllJobsByFilterUseCase {

  @Autowired
  JobRepository jobRepository;

  public List<JobEntity> execute(String filter) {
    return this.jobRepository.findAllByDescriptionContainingIgnoreCase(filter);

  }
}
