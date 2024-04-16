package br.com.pixelzone.pixelzone.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
    name = "response_object"
)
public class ResponseObject{

    private String error;
    private String success;

    public static final ResponseObject error(String error){

        return new ResponseObject(error, null);

    }

    public static final ResponseObject success(String success){

        return new ResponseObject(null, success);

    }

}
