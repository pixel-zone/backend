package br.com.pixelzone.pixelzone.dtos.conta;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "deleta_conta_request",
    description = "REQUEST QUE CONTEM TODOS OS DADOS PARA DELETAR UMA CONTA"
)
public record RemoveUsuarioRequest(

    @Schema(
        name = "id",
        nullable = false,
        description = "ID DO USUARIO QUE SERA DELETADO"
    )
    @JsonProperty("id") Long id

) {

    public ResponseEntity<ResponseObject> validate(){

        if(id == null || id < 1){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Id não pode ser null ou menor do que um").build()
            );

        }

        return null;

    }

}
