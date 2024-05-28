package br.com.pixelzone.pixelzone.dtos.games;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "joga_request"
)
public record JogaRequest(

    @Schema(
        nullable = true,
        description = "Apenas precisa ser utilizado para o tipo de jogo 'jackpot'"
    )
    @JsonProperty("id_partida") Long idPartida,

    @Schema(
        nullable = true,
        description = "Apenas precisa ser utilizado para os tipos de jogo 'robots' e 'flip_coin'"
    )
    @JsonProperty("id_jogador") Long idJogador,

    @Schema(
        nullable = true,
        description = "Apenas precisa ser utilizado para os tipos de jogo 'robots' e 'flip_coin'"
    )
    @JsonProperty("jogada") Long jogada

) {

    public ResponseEntity<ResponseObject> validate(){

        if(idPartida == null && idJogador == null && jogada == null){

            return ResponseObject.error(HttpStatus.BAD_REQUEST, "Nenhum item foi adicionado ao corpo da request");

        }

        return null;

    }

}
