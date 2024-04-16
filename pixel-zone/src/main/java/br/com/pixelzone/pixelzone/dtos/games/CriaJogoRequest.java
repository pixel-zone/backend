package br.com.pixelzone.pixelzone.dtos.games;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "cria_jogo_request",
    description = "REQUEST QUE CONTEM TODOS OS DADOS NECESSARIOS PARA A CRIAÇÃO DE UM NOVO JOGO NO BANCO DE DADOS"
)
public record CriaJogoRequest(

    @Schema(
        name = "nome",
        nullable = false,
        description = "NOME DO JOGO QUE SERA CRIADO"
    )
    @JsonProperty("nome") String nome

) {

    public ResponseEntity<ResponseObject> validate(){

        if(nome == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Nome não foi adicionado a request").build()
            );

        }

        return null;

    }

}
