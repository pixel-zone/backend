package br.com.pixelzone.pixelzone.dtos.jogos;

import java.util.ArrayList;
import java.util.List;

import br.com.pixelzone.pixelzone.dtos.conta.UsuarioDto;
import br.com.pixelzone.pixelzone.enums.GameTypeValues;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Robots extends Jogo {

    private List<UsuarioDto> usuariosDtos;

    public Robots(UsuarioDto usuarioDto){

        super.setTipo(GameTypeValues.ROBOT.key);
        this.usuariosDtos = new ArrayList<>();
        usuarioDto.setCreator(true);
        usuariosDtos.add(usuarioDto);

    }
    
}
