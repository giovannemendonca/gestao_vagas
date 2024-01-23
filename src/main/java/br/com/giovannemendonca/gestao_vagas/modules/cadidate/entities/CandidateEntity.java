package br.com.giovannemendonca.gestao_vagas.modules.cadidate.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "candidate")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Schema(description = "Nome do candidato", example = "Giovanne Mendonça")
  private String name;

  @Schema(description = "Username do candidato", example = "giovanne.mendonca")
  @NotBlank
  @Pattern(regexp = "^[a-z0-9_-]{3,15}$", message = "O username deve conter apenas letras minusculas, numeros, _ e -")
  private String username;

  @Schema(description = "Email do candidato", example = "giovanne.dev@gmail.com")
  @Email(message = "O email deve ser válido")
  private String email;

  @Schema(description = "Senha do candidato", example = "123456", minLength = 6, requiredMode = Schema.RequiredMode.REQUIRED )
  @Length(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
  private String password;

  @Schema(description = "Descrição do candidato", example = "Desenvolvedor Fullstack")
  private String description;

  private String curriculum;

  @CreationTimestamp
  private LocalDateTime createdAt;

}
