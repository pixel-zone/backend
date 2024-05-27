package br.com.pixelzone.pixelzone.dtos.anuncio;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "altera_anuncio_response"
)
public class AlteraAnuncioResponse extends ResponseObject {

    @Schema(
        name = "id",
        description = "ID DO ANUNCIO QUE FOI ALTERADO"
    )
    @JsonProperty("id") BigInteger id;

    public AlteraAnuncioResponse(BigInteger id, String success){

        this.id = id;
        super.setSuccess(success);

    }

    public static final AlteraAnuncioResponse success(BigInteger id, String success){

        return new AlteraAnuncioResponse(id, success);

    }
    
}
