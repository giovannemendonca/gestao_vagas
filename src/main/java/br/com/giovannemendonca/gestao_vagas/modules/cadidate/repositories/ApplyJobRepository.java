package br.com.giovannemendonca.gestao_vagas.modules.cadidate.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.giovannemendonca.gestao_vagas.modules.cadidate.entities.ApplyJobEntity;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID>{
  
} 