package br.com.giovannemendonca.gestao_vagas.modules.job.repositories;

import br.com.giovannemendonca.gestao_vagas.modules.job.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {

  // select * from job where description like '%filter%'
  List<JobEntity> findAllByDescriptionContainingIgnoreCase(String filter);

}
