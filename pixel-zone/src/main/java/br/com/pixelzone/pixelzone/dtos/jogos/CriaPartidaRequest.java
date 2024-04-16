package br.com.pixelzone.pixelzone.dtos.jogos;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "cria_partida_request",
    description = "REQUEST QUE CONTEM TODOS OS DADOS NECESSARIOS PARA A CRIAÇÃO DE UMA PARTIDA"
)
public record CriaPartidaRequest(

    @Schema(
        name = "id",
        nullable = false,
        description = "ID DO USUARIO QUE ESTA CRIANDO A PARTIDA"
    )
    @JsonProperty("id") Long id,

    @Schema(
        name = "game_type_id",
        nullable = false,
        description = "ID DO TIPO DE JOGO QUE SERA JOGADO"
    )
    @JsonProperty("game_type_id") Integer gameTypeId
    
) {

    public ResponseEntity<ResponseObject> validate(){

        if(id == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Id do usuario não foi adicionado a request").build()
            );

        }

        if(gameTypeId == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Id do tipo de partida não foi adicionado").build()
            );

        }

        return null;

    }

}
