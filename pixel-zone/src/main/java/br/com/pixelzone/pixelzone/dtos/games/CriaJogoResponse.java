package br.com.pixelzone.pixelzone.dtos.games;

import java.math.BigInteger;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Schema(
    name = "cria_jogo_response"
)
public class CriaJogoResponse extends ResponseObject {

    private BigInteger id;

    public CriaJogoResponse(BigInteger id, String success){

        this.id = id;
        super.setSuccess(success);

    }

    public static final CriaJogoResponse success(BigInteger id, String success){

        return new CriaJogoResponse(id, success);

    }
    
}
