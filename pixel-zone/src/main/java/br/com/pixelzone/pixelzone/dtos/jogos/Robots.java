package br.com.pixelzone.pixelzone.dtos.jogos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.conta.UsuarioDto;
import br.com.pixelzone.pixelzone.enums.GameTypeValues;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Robots extends Jogo {

    @JsonProperty("usuarios") private List<UsuarioDto> usuariosDtos;
    @JsonProperty("jogadas") private Map<Long, Long> jogadorEJogada;

    public Robots(UsuarioDto usuarioDto, long id){

        super.setTipo(GameTypeValues.ROBOT.key);
        super.setId(id);

        this.usuariosDtos = new ArrayList<>();
        this.jogadorEJogada = new HashMap<>();

        usuarioDto.setCreator(true);

        usuariosDtos.add(usuarioDto);

    }
    
}
