package br.com.pixelzone.pixelzone.dtos;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    public static final ResponseEntity<ResponseObject> success(HttpStatus httpStatus, String success){

        return formataResponse(
            httpStatus, 
            new ResponseObject(null, success)
        );

    }

    public static final ResponseEntity<ResponseObject> error(HttpStatus httpStatus, String error){

        return formataResponse(
            httpStatus, 
            new ResponseObject(error, null)
        );

    }

    public static final ResponseObject error(String error){

        return new ResponseObject(error, null);

    }

    public static final ResponseObject success(String success){

        return new ResponseObject(null, success);

    }

}
