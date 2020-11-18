package br.com.diegoalexandro.desafioagrojava.fazenda.api;

import br.com.diegoalexandro.desafioagrojava.fazenda.dominio.Fazenda;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
class NovaFazendaRequest {

    private final String nome;
    private final String cnpj;

    Fazenda toModel() {
        return new Fazenda(nome, cnpj);
    }


}
