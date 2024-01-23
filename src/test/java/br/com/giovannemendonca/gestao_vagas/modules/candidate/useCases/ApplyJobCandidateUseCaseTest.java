package br.com.giovannemendonca.gestao_vagas.modules.candidate.useCases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.giovannemendonca.gestao_vagas.exceptions.JobNotFoundException;
import br.com.giovannemendonca.gestao_vagas.exceptions.UserNotFoundException;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.entities.ApplyJobEntity;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.entities.CandidateEntity;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.repositories.ApplyJobRepository;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.repositories.CandidateRepository;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.useCases.ApplyJobCandidateUseCase;
import br.com.giovannemendonca.gestao_vagas.modules.job.entities.JobEntity;
import br.com.giovannemendonca.gestao_vagas.modules.job.repositories.JobRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ApplyJobCandidateUseCaseTest {

  @InjectMocks
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private JobRepository jobRepository;

  @Mock
  private ApplyJobRepository applyJobRepository;

  @Test
  @DisplayName("Should not be able to apply job with candidate not found")
   void shoul_not_be_able_to_apply_job_with_candidate_not_found() {
    try {
      applyJobCandidateUseCase.execute(null, null);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(UserNotFoundException.class);
    }
  }

  @Test
  @DisplayName("Should not be able to apply job with job not found")
   void should_not_be_able_to_apply_job_with_job_not_found() {

    var idCandidate = java.util.UUID.randomUUID();
    var candidate = new CandidateEntity();
    candidate.setId(idCandidate);

    when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));
    try {
      applyJobCandidateUseCase.execute(idCandidate, null);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(JobNotFoundException.class);
    }
  }

  @Test
  @DisplayName("Should be able to create a new apply job")
   void should_be_able_to_create_a_new_apply_job() {
    var idCandidate = UUID.randomUUID();
    var idJob = UUID.randomUUID();
    var idApplyJob = UUID.randomUUID();

    var applyJob = ApplyJobEntity.builder().candidateId(idCandidate)
        .jobId(idJob).build();

    var applyJobCreated = ApplyJobEntity.builder().id(idApplyJob).build();

    when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
    when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));
    when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

    var result = applyJobCandidateUseCase.execute(idCandidate, idJob);

    assertThat(result).hasFieldOrProperty("id");
    assertNotNull(result.getId());
    assertThat(result.getId()).isEqualByComparingTo(idApplyJob);
  }

}
