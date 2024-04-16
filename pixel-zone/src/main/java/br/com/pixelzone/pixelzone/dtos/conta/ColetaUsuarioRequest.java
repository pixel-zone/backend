package br.com.pixelzone.pixelzone.dtos.conta;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "coleta_usuario_request",
    description = "REQUEST QUE CONTEM TODOS OS DADOS NECESSARIOS PARA A COLETA DE UM USUARIO"
)
public record ColetaUsuarioRequest(
    
    @Schema(
        name = "id",
        nullable = true,
        description = "PODE SER NULL CASO OUTRO IDENTIFICADOR SEJA UTILIZADO"
    )
    @JsonProperty("id") Long id,

    @Schema(
        name = "username",
        nullable = true,
        description = "PODE SER NULL CASO OUTRO IDENTIFICADOR SEJA UTILIZADO"
    )
    @JsonProperty("username") String username,

    @Schema(
        name = "email",
        nullable = true,
        description = "PODE SER NULL CASO OUTRO IDENTIFICADOR SEJA UTILIZADO"
    )
    @JsonProperty("email") String email

) {

    public ResponseEntity<ResponseObject> validate(){

        if(id == null && username == null && email == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Nenhum identificador do usuario foi adicionado a request").build()
            );

        }

        return null;

    }

}
