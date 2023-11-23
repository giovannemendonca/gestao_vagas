package br.com.giovannemendonca.gestao_vagas.modules.cadidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.giovannemendonca.gestao_vagas.exceptions.JobNotFoundException;
import br.com.giovannemendonca.gestao_vagas.exceptions.UserNotFoundException;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.entities.ApplyJobEntity;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.repositories.ApplyJobRepository;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.repositories.CandidateRepository;
import br.com.giovannemendonca.gestao_vagas.modules.job.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {
  
  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private ApplyJobRepository applyJobRepository;
  
  @Autowired 
  private JobRepository jobRepository;
  
  public ApplyJobEntity execute(UUID idCandidate, UUID idJob){

    this.candidateRepository.findById(idCandidate)
    .orElseThrow(() -> {
      throw new UserNotFoundException();
    });
  this.jobRepository.findById(idJob)
    .orElseThrow(() -> {
      throw new JobNotFoundException();
    });

    var applyJob = ApplyJobEntity.builder()
    .candidateId(idCandidate)
    .jobId(idJob).build();

    applyJob = applyJobRepository.save(applyJob);
    return applyJob;
  }
}
