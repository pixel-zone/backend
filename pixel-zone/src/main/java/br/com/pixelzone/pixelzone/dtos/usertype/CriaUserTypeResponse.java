package br.com.pixelzone.pixelzone.dtos.usertype;

import java.math.BigInteger;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CriaUserTypeResponse extends ResponseObject {

    private BigInteger key;

    private CriaUserTypeResponse(BigInteger key, String success){

        this.key = key;
        super.setSuccess(success);  

    }

    public static final CriaUserTypeResponse success(BigInteger key, String success){
        return new CriaUserTypeResponse(key, success);
    }
    
}
