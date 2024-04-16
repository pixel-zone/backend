package br.com.pixelzone.pixelzone.dtos.jogos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(
    name = "jogo"
)
@Setter
@Getter
@ToString
public abstract class Jogo {

    private int tipo;
   
}
