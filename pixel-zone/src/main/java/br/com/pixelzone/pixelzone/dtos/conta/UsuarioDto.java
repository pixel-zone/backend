package br.com.pixelzone.pixelzone.dtos.conta;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "usuario",
    description = "OBJETO COM OS DADOS DO USUARIO"
)
public record UsuarioDto(

    @Schema(
        name = "id",
        description = "ID DO USUARIO"
    )
    @JsonProperty("id") long id,

    @Schema(
        name = "username",
        description = "USERNAME DO USUARIO"
    )
    @JsonProperty("username") String username,

    @Schema(
        name = "email",
        description = "EMAIL DO USUARIO"
    )
    @JsonProperty("email") String email,

    @Schema(
        name = "points",
        description = "PONTOS DO USUARIO"
    )
    @JsonProperty("points") long points,

    @Schema(
        name = "user_type_id",
        description = "TIPO DE USUARIO, 1: JOGADOR, 2: MODERADOR"
    )
    @JsonProperty("user_type_id") int userTypeId,

    @Schema(
        name = "items",
        description = "ITEMS QUE O USUARIO POSSUI"
    )
    @JsonProperty("items") long items

) {

}
