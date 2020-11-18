package br.com.diegoalexandro.desafioagrojava.fazenda.api;

import br.com.diegoalexandro.desafioagrojava.fazenda.dominio.Fazenda;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

@Tag("integrado")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class FazendaIT {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("Deve recupearar as fazendas")
    void teste1() throws Exception {
        entityManager.persist(new Fazenda("Fazenda 1", "73701266000135"));
        entityManager.persist(new Fazenda("Fazenda 2", "92757509000100"));
        entityManager.persist(new Fazenda("Fazenda 3", "37115196000170"));
        entityManager.persist(new Fazenda("Fazenda 4", "29709767000176"));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/fazendas"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Map resultado = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Map.class);

        List<Map<String, String>> items = (List<Map<String, String>>) resultado.get("items");

        Assertions.assertThat(items).hasSize(4);

        items.forEach(item -> {
            Assertions.assertThat(item).containsKey("nome");
            Assertions.assertThat(item).containsKey("cnpj");
        });
    }
}
