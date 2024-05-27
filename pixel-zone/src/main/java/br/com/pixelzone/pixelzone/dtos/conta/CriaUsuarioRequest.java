package br.com.pixelzone.pixelzone.dtos.conta;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "cria_conta_request",
    description = "REQUEST QUE CONTÉM OS DADOS PARA A CRIAÇÃO DE UMA CONTA"
)
public record CriaUsuarioRequest(

    @Schema(
        name = "username",
        nullable = false,
        description = "NOME QUE O USUARIO INSERIU"
    )
    @JsonProperty("username") String username,

    @Schema(
        name = "email",
        nullable = false,
        description = "EMAIL QUE O USUARIO INSERIU"
    )
    @JsonProperty("email") String email,

    @Schema(
        name = "senha",
        nullable = false,
        description = "SENHA QUE O USUARIO INSERIU"
    )
    @JsonProperty("senha") String senha,

    @Schema(
        name = "type",
        nullable = false,
        description = "TIPO DE USUARIO"
    )
    @JsonProperty("type") Integer type

) {

    public ResponseEntity<ResponseObject> validate(){

        if(username == null || username.length() < 3){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("O username não pode ser null ou ter menos de 3 characteres").build()
            );
        }

        if(email == null || email.length() < 3){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("O email não pode ser null ou ter menos de 3 characteres").build()
            );

        }

        if(senha == null || senha.length() < 3){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("A senha não pode ser null ou ter menos de 3 characteres").build()
            );
    
        }

        if(type == null || type < 1){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Tipo de usuario que esta sendo criado").build()
            );

        } 

        return null;

    }

}
