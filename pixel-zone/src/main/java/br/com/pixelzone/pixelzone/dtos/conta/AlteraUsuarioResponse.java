package br.com.pixelzone.pixelzone.dtos.conta;

import java.math.BigInteger;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Schema(
    name = "altera_usuario_response"
)
public class AlteraUsuarioResponse extends ResponseObject{

    private BigInteger id;

    public AlteraUsuarioResponse(BigInteger id, String success){

        this.id = id;
        super.setSuccess(success);

    }

    public static final AlteraUsuarioResponse success(BigInteger id, String success){

        return new AlteraUsuarioResponse(id, success);

    }
    
}
