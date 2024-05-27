package br.com.pixelzone.pixelzone.dtos.anuncio;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "verifica_anuncio_request",
    description = "REQUEST QUE CONTEM OS DADOS NECESSARIOS PARA A VERIFICACAO DE UMA REQUEST"
)
public record AprovaAnuncioRequest(

    @Schema(
        name = "id_anuncio",
        nullable = false,
        description = "ID DO A ANUNCIO"
    )
    @JsonProperty("id_anuncio") Long idAnuncio,

    @Schema(
        name = "aprovado",
        nullable = false,
        description = "VARIAVEL QUE DEFINE SE O ANUNCIO FOI APROVADO OU NÃO"
    )
    @JsonProperty("aprovado") Boolean aprovado

) {

    public ResponseEntity<ResponseObject> validate(){

        if(idAnuncio == null){

            return ResponseObject.error(
                HttpStatus.BAD_REQUEST, 
                "A variavel 'id_anuncio' não pode ser null"
            );

        }

        if(aprovado == null){

            return ResponseObject.error(
                HttpStatus.BAD_REQUEST, 
                "A variave 'aprovado' não pode ser null"
            );

        }

        return null;

    }

}
