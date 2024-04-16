package br.com.pixelzone.pixelzone.dtos.games;

import java.util.List;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ColetaJogosResponse extends ResponseObject {

    private List<JogoDto> jogos;

    public ColetaJogosResponse(List<JogoDto> jogos, String success){

        super.setSuccess(success);
        this.jogos = jogos;

    }

    public final static ColetaJogosResponse success(List<JogoDto> jogos, String success){

        return new ColetaJogosResponse(jogos, success);

    }
}
