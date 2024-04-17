package br.com.pixelzone.pixelzone.dtos.anuncio;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "remove_anuncio_request"
)
public record RemoveAnuncioRequest(

    @Schema(
        name = "id",
        nullable = false,
        description = "ID DO ANUNCIO QUE SERA REMOVIDO"
    )
    @JsonProperty("id") Long id

) {

    public ResponseEntity<ResponseObject> validate(){

        if(id == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Id do anuncio não foi adicionado a request").build()
            );

        }

        return null;

    }

}
