package br.com.pixelzone.pixelzone.dtos.conta;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import br.com.pixelzone.pixelzone.dtos.ResponseObject;

@Schema(
    name = "compra_skin_response"
)
public class CompraSkinResponse extends ResponseObject {

    @JsonProperty("usuario") 
    private UsuarioDto usuarioDto;

    public CompraSkinResponse(UsuarioDto usuarioDto){

        this.usuarioDto = usuarioDto;
        super.setSuccess("Compra realizada com sucesso");

    }

    public static ResponseEntity<ResponseObject> answer(UsuarioDto usuarioDto){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new CompraSkinResponse(usuarioDto))
        ;

    }

}
