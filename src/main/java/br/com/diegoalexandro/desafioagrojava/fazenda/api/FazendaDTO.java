package br.com.diegoalexandro.desafioagrojava.fazenda.api;

import br.com.diegoalexandro.desafioagrojava.fazenda.dominio.Fazenda;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
class FazendaDTO {

    private UUID id;
    private String nome;
    private String cnpj;
    private String cidade;
    private String uf;
    private String logradouro;


    static FazendaDTO fromModel(Fazenda fazenda) {
        return new FazendaDTO(fazenda.getId(), fazenda.getNome(),
                fazenda.getCnpj(), fazenda.getCidade(),
                fazenda.getUf(), fazenda.getLogradouro());
    }
}
