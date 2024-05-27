package br.com.pixelzone.pixelzone.dtos.games;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "id"
)
public record JogaRequest(

    @JsonProperty("id") Integer id

) {

    public ResponseEntity<ResponseObject> validate(){

        if(id == null){

            return ResponseObject.error(HttpStatus.BAD_REQUEST, "A variavel 'id' não foi adicionada ao corpo da request");

        }

        return null;

    }

}
