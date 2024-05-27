package br.com.pixelzone.pixelzone.dtos.conta;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Schema(
    name = "leaderboards"
)
public class LeaderboardsDto extends ResponseObject {

    @JsonProperty("players") private List<UsuarioDto> players;

    public LeaderboardsDto(List<UsuarioDto> players){

        this.players = players;
        super.setSuccess("Playes rankeados com sucesso");

    }

    public static final ResponseEntity<ResponseObject> success(List<UsuarioDto> players){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new LeaderboardsDto(players));

    }

}
