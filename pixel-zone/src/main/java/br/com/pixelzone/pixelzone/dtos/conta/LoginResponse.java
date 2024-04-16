package br.com.pixelzone.pixelzone.dtos.conta;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Schema(
    name = "login_response"
)
public class LoginResponse extends ResponseObject {

    private UsuarioDto usuario;

    public LoginResponse(UsuarioDto usuarioDto, String success){

        this.usuario = usuarioDto;
        super.setSuccess(success);

    }

    public static final LoginResponse success(UsuarioDto usuarioDto, String success){

        return new LoginResponse(usuarioDto, success);

    }
    
}
