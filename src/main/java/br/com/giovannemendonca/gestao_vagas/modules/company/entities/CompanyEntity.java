package br.com.giovannemendonca.gestao_vagas.modules.company.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "company")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank
@Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_-]*$", message = "O username deve conter apenas letras minusculas, numeros, _ e -")
  private String username;

  @Email(message = "O email deve ser válido")
  private String email;

  @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
  private String password;

  private String website;

  private String name;

  private String description;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
