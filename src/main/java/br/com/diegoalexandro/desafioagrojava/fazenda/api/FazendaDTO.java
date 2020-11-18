package br.com.diegoalexandro.desafioagrojava.fazenda.api;

import br.com.diegoalexandro.desafioagrojava.fazenda.dominio.Fazenda;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class FazendaDTO {

    private UUID id;
    private String nome;
    private String cnpj;


    static FazendaDTO fromModel(Fazenda fazenda) {
       return new FazendaDTO(fazenda.getId(), fazenda.getNome(), fazenda.getCnpj());
    }
}
