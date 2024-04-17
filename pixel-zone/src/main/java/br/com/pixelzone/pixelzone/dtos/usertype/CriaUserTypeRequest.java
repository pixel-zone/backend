package br.com.pixelzone.pixelzone.dtos.usertype;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "cria_user_type_request",
    description = "REQUEST QUE CONTEM TODOS OS DADOS NECESSARIOS PARA A CRIAÇÃO DE UM USER TYPE"
)
public record CriaUserTypeRequest(

    @Schema(
        name = "name",
        nullable = false,
        description = "NOME DO TIPO DE USUARIO QUE SERA CRIADO"
    )
    @JsonProperty("name") String name
    
) {

    public ResponseEntity<ResponseObject> validate(){

        if(name == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.error("A variavel name não pode ser null")
            );

        }

        return null;

    }

}
