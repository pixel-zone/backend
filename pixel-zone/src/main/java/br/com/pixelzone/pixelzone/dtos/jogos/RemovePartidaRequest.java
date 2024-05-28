package br.com.pixelzone.pixelzone.dtos.jogos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "remove_partida_request"
)
public record RemovePartidaRequest(

    @Schema(
        nullable = false
    )
    @JsonProperty("id_jogador") Long idJogador,

    @Schema(
        nullable = false
    )
    @JsonProperty("id_partida") Long idPartida

) {

    public ResponseEntity<ResponseObject> validate(){

        String message = null;

        if(idJogador == null){

            message = "A variavel 'id_jogador' não pode ser null";

        } else if(idPartida == null){

            message = "A variavel 'id_partida' não pode ser null";

        }

        return message == null ? null : ResponseObject.error(HttpStatus.BAD_REQUEST, message);

    }

}
