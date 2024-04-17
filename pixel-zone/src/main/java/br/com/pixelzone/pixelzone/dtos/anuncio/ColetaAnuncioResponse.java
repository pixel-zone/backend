package br.com.pixelzone.pixelzone.dtos.anuncio;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "coleta_anuncio_response"
)
public class ColetaAnuncioResponse extends ResponseObject {

    @Schema(
        name = "anuncios",
        description = "ANUNCIOS COLETADOS"
    )
    @JsonProperty("anuncios") List<AnuncioDto> anuncios;

    public ColetaAnuncioResponse(List<AnuncioDto> anuncioDtos, String success){

        this.anuncios = anuncioDtos;
        super.setSuccess(success);

    } 

}
