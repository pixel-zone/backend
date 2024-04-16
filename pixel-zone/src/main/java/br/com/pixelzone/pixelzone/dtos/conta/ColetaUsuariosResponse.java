package br.com.pixelzone.pixelzone.dtos.conta;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Schema(
    name = "coleta_usuarios_response",
    description = "RESPONSE QUE CONTEM TODOS OS USUARIOS CONTRADOS"
)
public class ColetaUsuariosResponse extends ResponseObject{

    @Schema(
        name = "usuarios"
    )
    @JsonProperty("usuarios") private List<UsuarioDto> usuarioDtos;

    public ColetaUsuariosResponse(List<UsuarioDto> usuarioDtos, String success){

        this.usuarioDtos = usuarioDtos;
        super.setSuccess(success);

    }

    public static final ColetaUsuariosResponse success(List<UsuarioDto> usuarioDtos, String success){

        return new ColetaUsuariosResponse(usuarioDtos, success);

    }

}
