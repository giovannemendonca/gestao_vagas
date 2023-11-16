package br.com.giovannemendonca.gestao_vagas.modules.cadidate.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "candidate")
@Data
public class CandidateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;


  private String name;

  @NotBlank
  @Pattern(regexp = "^[a-z0-9_-]{3,15}$", message = "O username deve conter apenas letras minusculas, numeros, _ e -")
  private String username;

  @Email(message = "O email deve ser válido")
  private String email;

  @Length(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
  private String password;

  private String description;

  private String curriculum;

  @CreationTimestamp
  private LocalDateTime createdAt;

}
