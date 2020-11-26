package br.com.diegoalexandro.desafioagrojava.fazenda.exceptions;

import com.totvs.tjf.api.context.stereotype.ApiError;
import com.totvs.tjf.api.context.stereotype.ApiErrorParameter;

@ApiError
public class FazendaNaoEncontrada extends RuntimeException {

    @ApiErrorParameter
    private final String id;

    public FazendaNaoEncontrada(String id) {
        this.id = id;
    }
}
