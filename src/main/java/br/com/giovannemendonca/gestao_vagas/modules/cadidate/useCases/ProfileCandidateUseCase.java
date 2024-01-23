package br.com.giovannemendonca.gestao_vagas.modules.cadidate.useCases;

import br.com.giovannemendonca.gestao_vagas.exceptions.UserNotFoundException;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.dto.ProfileCandidateResponseDTO;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

  private CandidateRepository candidateRepository;

  ProfileCandidateUseCase(CandidateRepository candidateRepository) {
    this.candidateRepository = candidateRepository;
  }

  public ProfileCandidateResponseDTO execute(UUID idCandidate) {
    var candidate = this.candidateRepository.findById(idCandidate)
            .orElseThrow(UserNotFoundException::new);

    return ProfileCandidateResponseDTO.builder()
            .email(candidate.getEmail())
            .description(candidate.getDescription())
            .id(candidate.getId())
            .name(candidate.getName())
            .username(candidate.getUsername())
            .build();
  }
}
