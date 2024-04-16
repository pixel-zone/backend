package br.com.pixelzone.pixelzone.dtos.conta;

import java.math.BigInteger;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Schema(
    name = "cria_usuario_response"
)
public class CriaUsuarioResponse extends ResponseObject{

    private BigInteger id;

    public CriaUsuarioResponse(BigInteger id, String success){

        this.id = id;
        super.setSuccess(success);

    }

    public static final CriaUsuarioResponse success(BigInteger id, String success){

        return new CriaUsuarioResponse(id, success);

    }
    
}
