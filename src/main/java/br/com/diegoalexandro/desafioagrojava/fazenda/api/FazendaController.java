package br.com.diegoalexandro.desafioagrojava.fazenda.api;

import br.com.diegoalexandro.desafioagrojava.fazenda.dominio.Fazenda;
import br.com.diegoalexandro.desafioagrojava.fazenda.dominio.FazendaRepository;
import com.totvs.tjf.api.context.stereotype.ApiGuideline;
import com.totvs.tjf.api.context.v2.request.ApiPageRequest;
import com.totvs.tjf.api.context.v2.request.ApiSortRequest;
import com.totvs.tjf.api.context.v2.response.ApiCollectionResponse;
import com.totvs.tjf.core.api.jpa.repository.ApiJpaCollectionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/fazendas")
@ApiGuideline(ApiGuideline.ApiGuidelineVersion.V2)
@RequiredArgsConstructor
class FazendaController {

    private final FazendaRepository fazendaRepository;

    @GetMapping
    public ApiCollectionResponse<FazendaDTO> recuperar(ApiPageRequest pageRequest, ApiSortRequest sortRequest) {
        ApiJpaCollectionResult<Fazenda> fazendas = fazendaRepository.findAll(pageRequest, sortRequest);
        List<FazendaDTO> fazendasDTO = fazendas
                .getItems()
                .stream()
                .map(FazendaDTO::fromModel)
                .collect(Collectors.toList());

        return ApiCollectionResponse.of(fazendasDTO, fazendas.hasNext());
    }

    @PostMapping
    public ResponseEntity<FazendaDTO> salvar(@RequestBody NovaFazendaRequest novaFazenda) {
        Fazenda fazenda = fazendaRepository.save(novaFazenda.toModel());
        return ResponseEntity.ok(FazendaDTO.fromModel(fazenda));
    }

}
