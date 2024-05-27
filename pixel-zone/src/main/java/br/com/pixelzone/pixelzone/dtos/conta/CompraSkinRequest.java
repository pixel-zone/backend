package br.com.pixelzone.pixelzone.dtos.conta;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "compra_skin_request"
)
public record CompraSkinRequest(

    @Schema(
        nullable = false
    )
    @JsonProperty("id_usuario") Long idUsuario,

    @Schema(
        nullable = false
    )
    @JsonProperty("id_skin") Long idSkin
    
) {

    public ResponseEntity<ResponseObject> validate(){

        String message = null;

        if(idUsuario == null){

            message = "A variavel 'id_usuario não pode ser null'";

        } else if(idSkin == null){

            message = "A variavel 'id_skin' não pode ser null";

        }

        return message == null ? null : ResponseObject.error(HttpStatus.BAD_REQUEST, message);

    }

}
