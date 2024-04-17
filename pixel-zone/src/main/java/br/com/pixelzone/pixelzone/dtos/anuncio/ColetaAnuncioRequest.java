package br.com.pixelzone.pixelzone.dtos.anuncio;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "coleta_anuncio_request"
)
public record ColetaAnuncioRequest(

    @Schema(
        name = "id_usuario",
        nullable = true,
        description = "PODE SER NULL CASO OUTRO IDENTIFICADOR SEJA UTILIZADO"
    )
    @JsonProperty("id_usuario") Long idUsuario,

    @Schema(
        name = "id_anuncio",
        nullable = true,
        description = "PODE SER NULL CASO OUTRO IDENTIFICADOR SEJA UTILIZADO"
    )
    @JsonProperty("id_anuncio") Long idAnuncio

) {

    public ResponseEntity<ResponseObject> validate(){

        if(idUsuario == null && idAnuncio == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Nenhum identificador foi adicionado a request").build()
            );

        }

        return null;

    }


}
