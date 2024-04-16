package br.com.pixelzone.pixelzone.dtos.games;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "altera_jogo_request",
    description = "REQUEST QUE CONTEM TODOS OS ITENS NECESSARIOS PARA A ALTERAÇÃO DE UM JOGO"
)
public record AlteraJogoRequest(

    @Schema(
        name = "id",
        nullable = false,
        description = "ID DO JOGO QUE SERA ALTERADO"
    )
    @JsonProperty("id") Long id,

    @Schema(
        name = "nome",
        nullable = false,
        description = "NOVO NOME DO ANUNCIO"
    )
    @JsonProperty("nome") String nome

) {

    public ResponseEntity<ResponseObject> validate(){

        if(id == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Id não foi adicionado a request").build()
            );

        }

        if(nome == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Nome não foi adicionado a request").build()
            );

        }

        return null;

    }

}
