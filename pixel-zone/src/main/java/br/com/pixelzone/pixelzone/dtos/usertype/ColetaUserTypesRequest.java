package br.com.pixelzone.pixelzone.dtos.usertype;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "coleta_user_types_request",
    description = "CONTEM TODOS ITENS NECESSARIOS PARA COLETA DE TIPOS DE USUARIOS"
)
public record ColetaUserTypesRequest(

    @Schema(
        name = "id",
        nullable = true,
        description = "ID DO TIPO DE USUARIO QUE SERA COLETADO, CASO SEJA NULL TODOS SERÃO COLETADOS"
    )
    @JsonProperty("id") Long id

) {

}
