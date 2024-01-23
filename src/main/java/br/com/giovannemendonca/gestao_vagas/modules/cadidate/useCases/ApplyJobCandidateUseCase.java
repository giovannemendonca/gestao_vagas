package br.com.giovannemendonca.gestao_vagas.modules.cadidate.useCases;

import br.com.giovannemendonca.gestao_vagas.exceptions.JobNotFoundException;
import br.com.giovannemendonca.gestao_vagas.exceptions.UserNotFoundException;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.entities.ApplyJobEntity;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.repositories.ApplyJobRepository;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.repositories.CandidateRepository;
import br.com.giovannemendonca.gestao_vagas.modules.job.repositories.JobRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {
  
  private CandidateRepository candidateRepository;

  private ApplyJobRepository applyJobRepository;
  
  private JobRepository jobRepository;


  ApplyJobCandidateUseCase(CandidateRepository candidateRepository, ApplyJobRepository applyJobRepository, JobRepository jobRepository){
    this.candidateRepository = candidateRepository;
    this.applyJobRepository = applyJobRepository;
    this.jobRepository = jobRepository;
  }
  
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
