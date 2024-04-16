package br.com.pixelzone.pixelzone.dtos.games;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "remove_jogo_request",
    description = "REQUEST QUE CONTÉM TODOS OS DADOS PARA A REMOÇÃO DE UM JOGO"
)
public record RemoveJogoRequest(

    @Schema(
        name = "id",
        nullable = false,
        description = "ID DO JOGO QUE SERA REMOVIDO"
    )
    @JsonProperty("id") Long id

) {

    public ResponseEntity<ResponseObject> validate(){

        if(id == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Id não foi adicionado a request").build()
            );

        }

        return null;

    }

}
