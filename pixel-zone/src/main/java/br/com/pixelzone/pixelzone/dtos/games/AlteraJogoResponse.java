package br.com.pixelzone.pixelzone.dtos.games;

import java.math.BigInteger;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class AlteraJogoResponse extends ResponseObject {

    private BigInteger idJogo;

    public AlteraJogoResponse(BigInteger idJogo, String success){

        this.idJogo = idJogo;
        super.setSuccess(success);

    }

    public final static AlteraJogoResponse success(BigInteger idJogo, String success){

        return new AlteraJogoResponse(idJogo, success);

    }
    
}
