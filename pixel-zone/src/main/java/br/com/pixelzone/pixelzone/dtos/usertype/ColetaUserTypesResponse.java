package br.com.pixelzone.pixelzone.dtos.usertype;

import java.util.List;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Schema(
    name = "coleta_user_type_response"
)
@Getter
@ToString
public class ColetaUserTypesResponse extends ResponseObject {

    private List<UserTypeDto> userTypes;

    private ColetaUserTypesResponse(List<UserTypeDto> userTypes, String success){
        this.userTypes = userTypes;
        super.setSuccess(success);
    }

    public static ColetaUserTypesResponse success(List<UserTypeDto> userTypeDtos, String success){
        return new ColetaUserTypesResponse(userTypeDtos, success);
    }

}
