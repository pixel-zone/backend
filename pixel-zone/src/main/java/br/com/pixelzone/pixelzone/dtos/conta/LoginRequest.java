package br.com.pixelzone.pixelzone.dtos.conta;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "login_request",
    description = "REQUEST QUE CONTEM TODOS OS DADOS NECESSARIOS PARA O LOGIN"
)
public record LoginRequest(

    @Schema(
        name = "email",
        nullable = false,
        description = "EMAIL DO USUARIO QUE DESEJA REALIZAR O LOGIN"
    )
    @JsonProperty("email") String email,

    @Schema(
        name = "senha",
        nullable = false,
        description = "SENHA DO USUARIO QUE DESEJA REALIZAR O LOGIN"
    )
    @JsonProperty("senha") String senha
    
) {

    public ResponseEntity<ResponseObject> validate(){

        if(email == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Email do usuario não foi adicionado a request").build()
            );

        }

        if(senha == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Senha do usuario não foi adicionada a request").build()
            );

        }

        return null;

    }

}
