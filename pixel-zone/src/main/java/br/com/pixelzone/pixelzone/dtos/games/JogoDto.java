package br.com.pixelzone.pixelzone.dtos.games;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "jogo"
)
public record JogoDto(

    @Schema(
        name = "id"
    )
    @JsonProperty("id") long id,

    @Schema(
        name = "name"
    )
    @JsonProperty("name") String name

) {}
