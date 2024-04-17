package br.com.pixelzone.pixelzone.dtos.anuncio;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "cria_anuncio_response"
)
public class CriaAnuncioResponse extends ResponseObject {

    @Schema(
        name = "id",
        description = "ID DO ANUNCIO QUE FOI CRIADO"
    )
    @JsonProperty("id") BigInteger id;

    public CriaAnuncioResponse(BigInteger id, String success){

        super.setSuccess(success);
        this.id = id;

    }

}
