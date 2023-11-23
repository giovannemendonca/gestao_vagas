package br.com.giovannemendonca.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.giovannemendonca.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.giovannemendonca.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.giovannemendonca.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.giovannemendonca.gestao_vagas.modules.job.dto.CreateJobDTO;
import br.com.giovannemendonca.gestao_vagas.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControlllerTest {

  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private CompanyRepository companyRepository;

  @Before
  public void setup() {
    mvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  @DisplayName("should be able to create a new job")
  public void should_be_able_to_create_a_new_job() throws Exception {

    var company = CompanyEntity.builder()
        .name("Giovanne Mendonça")
        .username("mendonca-solutions")
        .password("123456")
        .email("mendoncasolutions@dev.com")
        .description("Empresa de desenvolvimento de software")
        .build();

    // esse saveAndFlush é para salvar e já retornar o objeto salvo
    var companySaved = this.companyRepository.saveAndFlush(company);

    var createdJobDTO = CreateJobDTO.builder()
        .benefits("BENEFITS TESTS")
        .description("DESCRIPTION TESTS")
        .level("LEVEL TEST")
        .build();

    var result = mvc.perform(MockMvcRequestBuilders.post("/company/job/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtils.objectToJSON(createdJobDTO))
        .header("Authorization", TestUtils.generateToken(companySaved.getId(), "JAVAGAS_@123#")))
        .andExpect(MockMvcResultMatchers.status().isOk());

    System.out.println(result);

  }

  @Test
  public void slould_not_be_able_to_create_a_new_job_if_company_not_found() throws Exception{

    var createdJobDTO = CreateJobDTO.builder()
        .benefits("BENEFITS TESTS")
        .description("DESCRIPTION TESTS")
        .level("LEVEL TEST")
        .build();

         mvc.perform(MockMvcRequestBuilders.post("/company/job/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtils.objectToJSON(createdJobDTO))
        .header("Authorization", TestUtils.generateToken(UUID.randomUUID(), "JAVAGAS_@123#")))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());


  }

}