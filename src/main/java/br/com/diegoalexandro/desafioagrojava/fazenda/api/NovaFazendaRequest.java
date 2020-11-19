package br.com.diegoalexandro.desafioagrojava.fazenda.api;

import br.com.diegoalexandro.desafioagrojava.fazenda.dominio.Fazenda;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
class NovaFazendaRequest {

    @NotEmpty
    private final String nome;

    @CNPJ
    @NotEmpty
    private final String cnpj;

    @NotEmpty
    private final String cidade;

    @NotEmpty
    @Size(max = 2)
    private final String uf;

    @NotEmpty
    private final String logradouro;

    Fazenda toModel() {
        return Fazenda.builder()
                .nome(nome)
                .cnpj(cnpj)
                .cidade(cidade)
                .logradouro(logradouro)
                .uf(uf)
                .build();
    }


}
