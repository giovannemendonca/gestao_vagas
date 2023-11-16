package br.com.giovannemendonca.gestao_vagas.modules.cadidate.useCases;

import br.com.giovannemendonca.gestao_vagas.modules.cadidate.dto.ProfileCandidateResponseDTO;
import br.com.giovannemendonca.gestao_vagas.modules.cadidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  public ProfileCandidateResponseDTO execute(UUID idCandidate) {
    var candidate = this.candidateRepository.findById(idCandidate)
            .orElseThrow(() -> new UsernameNotFoundException("Candidato não encontrado"));

    var candidateDTO = ProfileCandidateResponseDTO.builder()
            .email(candidate.getEmail())
            .description(candidate.getDescription())
            .id(candidate.getId())
            .name(candidate.getName())
            .username(candidate.getUsername())
            .build();

    return candidateDTO;
  }
}
