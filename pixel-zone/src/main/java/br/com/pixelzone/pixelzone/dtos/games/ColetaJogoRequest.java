package br.com.pixelzone.pixelzone.dtos.games;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "coleta_jogo_request"
)
public record ColetaJogoRequest(

    @Schema(
        name = "id",
        nullable = true,
        description = "ID DO JOGO QUE SERA COLETADO, CASO SEJA NULL TODOS OS JOGOS SERÃO COLETADOS"
    )
    @JsonProperty("id") Long id

) {}
