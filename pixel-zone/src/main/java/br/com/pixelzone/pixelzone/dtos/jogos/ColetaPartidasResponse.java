package br.com.pixelzone.pixelzone.dtos.jogos;

import java.util.List;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Schema(
    name = "coleta_partidas_response"
)
public class ColetaPartidasResponse extends ResponseObject {

    private List<Jogo> jogos;

    public ColetaPartidasResponse(List<Jogo> jogos, String success){

        this.jogos = jogos;
        super.setSuccess("Jogos encontrados");

    }

    public static final ColetaPartidasResponse success(List<Jogo> jogos, String success){

        return new ColetaPartidasResponse(jogos, success);

    }
    
}
