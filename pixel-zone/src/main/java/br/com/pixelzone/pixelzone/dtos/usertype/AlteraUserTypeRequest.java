package br.com.pixelzone.pixelzone.dtos.usertype;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "altera_user_type_request",
    description = "REQUEST QUE CONTÉM TODOS OS DADOS NECESSARIOS PARA A ALTERAÇÃO DE UM TIPO DE USUARIO"
)
public record AlteraUserTypeRequest(

    @Schema(
        name = "id",
        nullable = false,
        description = "ID DO TIPO DE USUARIO QUE SERA MODIFICADO"
    )
    @JsonProperty("id") Long id,

    @Schema(
        name = "name",
        nullable = false,
        description = "name"
    )
    @JsonProperty("name") String name

) {

    public ResponseEntity<ResponseObject> validate(){

        if(id == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.error("Id não pode ser null")
            );

        }

        if (name == null) {

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.error("Name não pode ser null")
            );

        }

        return null;

    }

}
