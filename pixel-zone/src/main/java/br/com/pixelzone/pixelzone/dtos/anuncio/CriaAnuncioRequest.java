package br.com.pixelzone.pixelzone.dtos.anuncio;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "cria_anuncio_request",
    description = "REQUEST QUE CONTÉM TODOS OS DADOS NECESSARIOS PARA A CRIAÇÃO DE UM ANUNCIO"
)
public record CriaAnuncioRequest(

    @Schema(
        name = "id_usuario",
        nullable = false,
        description = "ID DO USUARIO QUE DESEJA CRIAR O ANUNCIO"
    )
    @JsonProperty("id_usuario") Long idUsuario,

    @Schema(
        name = "anuncio",
        nullable = false,
        description = "IMAGEM DO ANUNCIO"
    )
    @JsonProperty("anuncio") String anuncio

) {

    public ResponseEntity<ResponseObject> validate(){

        if(idUsuario == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Id do usuario não foi adicionado a request").build()
            );

        }

        if(anuncio == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Anuncio não foi adicionado a request").build()
            );

        }

        return null;

    }

}
