package br.com.pixelzone.pixelzone.dtos.anuncio;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "anuncio"
)
public record AnuncioDto(

    @Schema(
        name = "id",
        description = "ID DO ANUNCIO"
    )
    @JsonProperty("id") long id,

    @Schema(
        name = "id_usuario",
        description = "ID DO USUARIO QUE CRIOU O ANUNCIO"
    )
    @JsonProperty("id_usuario") long idUsuario,

    @Schema(
        name = "imagem",
        description = "IMAGEM DO ANUNCIO EM BASE 64"
    )
    @JsonProperty("ad") String ad

) {}
