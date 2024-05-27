package br.com.pixelzone.pixelzone.dtos.conta;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "altera_conta_request",
    description = "REQUEST QUE CONTÉM TODOS OS DADOS NECESSARIOS PARA A ALTERAÇÃO DE UM USUARIO"
)
public record AlteraUsuarioRequest(

    @Schema(
        name = "id",
        nullable = false,
        description = "ID DO USUARIO QUE SERA ALTERADO"
    )
    @JsonProperty("id") Long id,

    @Schema(
        name = "username",
        nullable = true,
        description = "SÓ PRECISA SER INSERIDO CASO PRECISE SER MODIFICADO, CASO SEJA NULL SERA IGNORADO"
    )
    @JsonProperty("username") String username,

    @Schema(
        name = "email",
        nullable = true,
        description = "SÓ PRECISA SER INSERIDO CASO PRECISE SER MODIFICADO, CASO SEJA NULL SERA IGNORADO"
    )
    @JsonProperty("email") String email,

    @Schema(
        name = "senha",
        nullable = true,
        description = "SÓ PRECISA SER INSERIDO CASO PRECISE SER MODIFICADO, CASO SEJA NULL SERA IGNORADO"
    )
    @JsonProperty("senha") String senha,

    @Schema(
        name = "type",
        nullable = true,
        description = "SÓ PRECISA SER INSERIDO CASO PRECISE SER MODIFICADO, CASO SEJA NULL SERA IGNORADO"
    )
    @JsonProperty("type") Integer type,

    @Schema(
        nullable = true
    )
    @JsonProperty("chosen_skin") Long chosenSkin


) {

    public ResponseEntity<ResponseObject> validate(){

        if(id == null || id < 1){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Id não pode ser null nem menor do que zero").build()
            );
    
        }

        if(username == null && email == null && senha == null && type == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Request não possui nenhum item no body").build()
            );

        }

        if(type != null){

            if(type < 1){

                return formataResponse(
                    HttpStatus.BAD_REQUEST, 
                    ResponseObject.builder().error("Todos os tipos de usuario são acima de zero").build()
                );

            }

        }

        if(username != null){

            if(username.length() < 3){

                return formataResponse(
                    HttpStatus.BAD_REQUEST, 
                    ResponseObject.builder().error("Username não pode ter menos de 3 characteres").build()
                );

            }

        }

        if(email != null){

            if(email.length() < 3){

                return formataResponse(
                    HttpStatus.BAD_REQUEST, 
                    ResponseObject.builder().error("Email não pode ter menos de 3 characteres").build()
                );

            }

        }

        if(senha != null){

            if(senha.length() < 3){

                return formataResponse(
                    HttpStatus.BAD_REQUEST, 
                    ResponseObject.builder().error("Senha não pode ter menos de 3 characteres").build()
                );

            }

        }

        return null;

    }

}
