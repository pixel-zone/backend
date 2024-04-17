package br.com.pixelzone.pixelzone.dtos.anuncio;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "altera_anuncio_request"
)
public record AlteraAnuncioRequest(

    @Schema(
        name = "id",
        nullable = false,
        description = "ID DO ANUNCIO QUE SERA ALTERADO"
    )
    @JsonProperty("id") Long id,

    @Schema(
        name = "anuncio",
        nullable = false,
        description = "NOVO ANUNCIO QUE IRA SUBSTITUIR O ANTIGO"
    )
    @JsonProperty("anuncio") String anuncio

) {

    public ResponseEntity<ResponseObject> validate(){

        if(id == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Id do anuncio não foi adicionado a request").build()
            );

        }

        if(anuncio == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Anuncio não foi adicionado a request").build()
            );

        }

        return null;

    }

}
