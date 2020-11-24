package br.com.diegoalexandro.desafioagrojava.fazenda.api;

import br.com.diegoalexandro.desafioagrojava.fazenda.dominio.Fazenda;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integrado")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class FazendaIT {

    public static final String API_FAZENDAS = "/api/fazendas/";
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("Deve recuperar as fazendas")
    void teste1() throws Exception {
        entityManager.persist(Fazenda.builder().nome("Fazenda 1").cnpj("73701266000135").cidade("Assis").uf("SP").logradouro("Rod 1").build());
        entityManager.persist(Fazenda.builder().nome("Fazenda 2").cnpj("92757509000100").cidade("Assis").uf("SP").logradouro("Rod 1").build());
        entityManager.persist(Fazenda.builder().nome("Fazenda 3").cnpj("37115196000170").cidade("Assis").uf("SP").logradouro("Rod 1").build());
        entityManager.persist(Fazenda.builder().nome("Fazenda 4").cnpj("29709767000176").cidade("Assis").uf("SP").logradouro("Rod 1").build());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(API_FAZENDAS))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Map resultado = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Map.class);

        List<Map<String, String>> items = (List<Map<String, String>>) resultado.get("items");

        assertThat(items).hasSize(4);

        items.forEach(item -> {
            assertThat(item).containsKey("nome");
            assertThat(item).containsKey("cnpj");
            assertThat(item).containsKey("cidade");
            assertThat(item).containsKey("uf");
            assertThat(item).containsKey("logradouro");
        });
    }

    @Test
    @DisplayName("Deve recuperar uma fazenda pelo ID")
    void teste2() throws Exception {
        Fazenda fazenda = Fazenda.builder().nome("Fazenda 1").cnpj("73701266000135").cidade("Assis").uf("SP").logradouro("Rod 1").build();
        entityManager.persist(fazenda);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(API_FAZENDAS + fazenda.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Map<String, String> resultado = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Map.class);

        assertThat(resultado)
                .containsEntry("id", fazenda.getId().toString())
                .containsEntry("nome", "Fazenda 1")
                .containsEntry("cnpj", "73701266000135")
                .containsEntry("cidade", "Assis")
                .containsEntry("uf", "SP")
                .containsEntry("logradouro", "Rod 1");
    }

    @Test
    @DisplayName("Deve retornar 404 se não encontrar uma fazenda")
    void teste3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_FAZENDAS + UUID.randomUUID().toString()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Deve salvar uma fazenda")
    void teste4() throws Exception {

        Map<String, String> fazenda = Map.of("nome", "Fazenda do teste",
                "cnpj", "53429817000146",
                "cidade", "Assis",
                "uf", "SP",
                "logradouro", "Rua 1");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(fazenda);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(API_FAZENDAS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Map resposta = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Map.class);

        String id = (String) resposta.get("id");

        TypedQuery<Fazenda> query = entityManager.createQuery("select a from Fazenda a where a.id = ?1", Fazenda.class);
        query.setParameter(1, UUID.fromString(id));

        Fazenda fazendaCadastrada = query.getSingleResult();

        assertThat(fazendaCadastrada.getNome()).isEqualTo("Fazenda do teste");
        assertThat(fazendaCadastrada.getCnpj()).isEqualTo("53429817000146");
        assertThat(fazendaCadastrada.getCidade()).isEqualTo("Assis");
        assertThat(fazendaCadastrada.getUf()).isEqualTo("SP");
        assertThat(fazendaCadastrada.getLogradouro()).isEqualTo("Rua 1");
    }

    @Test
    @DisplayName("Não deve salvar uma Fazenda com mesmo nome")
    void teste5() throws Exception {

        entityManager.persist(Fazenda.builder().nome("Fazenda 1").cnpj("73701266000135").cidade("Assis").uf("SP").logradouro("Rod 1").build());

        Map<String, String> fazenda = Map.of("nome", "Fazenda 1",
                "cnpj", "53429817000146",
                "cidade", "Assis",
                "uf", "SP",
                "logradouro", "Rua 1");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(fazenda);
        mockMvc.perform(MockMvcRequestBuilders.post(API_FAZENDAS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros", Matchers.contains("Campo nome duplicado.")));
    }

    @Test
    @DisplayName("Não deve salvar uma Fazenda com mesmo cnpj")
    void teste6() throws Exception {

        entityManager.persist(Fazenda.builder().nome("Fazenda Teste").cnpj("53429817000146").cidade("Assis").uf("SP").logradouro("Rod 1").build());

        Map<String, String> fazenda = Map.of("nome", "Fazenda 1",
                "cnpj", "53429817000146",
                "cidade", "Assis",
                "uf", "SP",
                "logradouro", "Rua 1");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(fazenda);
        mockMvc.perform(MockMvcRequestBuilders.post(API_FAZENDAS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros", Matchers.contains("Campo cnpj duplicado.")));
    }
}
