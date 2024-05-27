package br.com.pixelzone.pixelzone.dtos.conta;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(
    name = "usuario",
    description = "OBJETO COM OS DADOS DO USUARIO"
)
@Getter
@Setter
@AllArgsConstructor
public class UsuarioDto{

    @Schema(
        name = "id",
        description = "ID DO USUARIO"
    )
    @JsonProperty("id") private long id;

    @Schema(
        name = "username",
        description = "USERNAME DO USUARIO"
    )
    @JsonProperty("username") private String username;

    @Schema(
        name = "email",
        description = "EMAIL DO USUARIO"
    )
    @JsonProperty("email") private String email;

    @Schema(
        name = "points",
        description = "PONTOS DO USUARIO"
    )
    @JsonProperty("points") private long points;

    @Schema(
        name = "user_type_id",
        description = "TIPO DE USUARIO, 1: JOGADOR, 2: MODERADOR"
    )
    @JsonProperty("user_type_id") private int userTypeId;

    @Schema(
        name = "items",
        description = "ITEMS QUE O USUARIO POSSUI"
    )
    @JsonProperty("items") private List<Long> items;

    @JsonProperty("creator") boolean creator = false;

    public long points(){
        return points;
    }

    public long id(){
        return id;
    }

}
