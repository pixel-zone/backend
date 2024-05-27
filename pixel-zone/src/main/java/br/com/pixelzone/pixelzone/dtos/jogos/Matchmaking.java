package br.com.pixelzone.pixelzone.dtos.jogos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Matchmaking {

    private List<Jogo> jogos;

    public void remove(int id){

        jogos.remove(id);

    }

    public void addJogo(Jogo jogo){

        jogos.add(jogo);

    }
    
}
