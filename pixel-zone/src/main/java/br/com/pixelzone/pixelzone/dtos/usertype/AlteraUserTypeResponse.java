package br.com.pixelzone.pixelzone.dtos.usertype;

import java.math.BigInteger;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Schema(
    name = "altera_user_type_response"
)
@Getter
@ToString
public class AlteraUserTypeResponse extends ResponseObject{

    private BigInteger id;

    private AlteraUserTypeResponse(BigInteger id, String success){

        this.id = id;
        super.setSuccess(success);

    }

    public static final AlteraUserTypeResponse success(BigInteger id, String success){

        return new AlteraUserTypeResponse(id, success);

    }
    
}
