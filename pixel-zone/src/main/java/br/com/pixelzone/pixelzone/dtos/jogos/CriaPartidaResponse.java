package br.com.pixelzone.pixelzone.dtos.jogos;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Schema(
    name = "cria_partida_response"
)
public class CriaPartidaResponse extends ResponseObject{

    private long id;

    public CriaPartidaResponse(long id, String success){

        this.id = id;
        super.setSuccess(success);

    }

    public static final CriaPartidaResponse success(long id, String success){

        return new CriaPartidaResponse(id, success);

    }
    
}
