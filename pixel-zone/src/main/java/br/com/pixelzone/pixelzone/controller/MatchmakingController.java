package br.com.pixelzone.pixelzone.controller;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;

import br.com.pixelzone.pixelzone.dtos.jogos.Jackpot;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import br.com.pixelzone.pixelzone.dtos.conta.UsuarioDto;
import br.com.pixelzone.pixelzone.dtos.games.JogaRequest;
import br.com.pixelzone.pixelzone.dtos.jogos.AdicionaOuRemoveUsuarioRequest;
import br.com.pixelzone.pixelzone.dtos.jogos.ColetaPartidasResponse;
import br.com.pixelzone.pixelzone.dtos.jogos.CriaPartidaRequest;
import br.com.pixelzone.pixelzone.dtos.jogos.CriaPartidaResponse;
import br.com.pixelzone.pixelzone.dtos.jogos.FlipCoin;
import br.com.pixelzone.pixelzone.dtos.jogos.Jogo;
import br.com.pixelzone.pixelzone.dtos.jogos.Matchmaking;
import br.com.pixelzone.pixelzone.dtos.jogos.RemovePartidaRequest;
import br.com.pixelzone.pixelzone.dtos.jogos.Robots;
import br.com.pixelzone.pixelzone.repositories.mysql.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/pixel_zone/matchmaking/v1/pixel_zone/matchmaking")
@Tag(
    name = "APIs-PIXEL-ZONE: MATCHMAKING",
    description = "CONTEM TODOS OS ENPOINTS RELACIONADOS AS PARTIDAS"
)
public class MatchmakingController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private Matchmaking matchmaking;

    @DeleteMapping("/remove")
    @Operation(
        summary = "API UTILIZADA PARA A REMOÇÃO DE UMA PARTIDA",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            ),
            @ApiResponse(
                responseCode = "401",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)   
                )
            ),
            @ApiResponse(
                responseCode = "404",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            )
        }
    )
    public ResponseEntity<ResponseObject> remove(@RequestBody RemovePartidaRequest request){

        ResponseEntity<ResponseObject> validate = request.validate();

        if(validate != null){

            return validate;

        }

        for(int i = 0 ; i < matchmaking.getJogos().size() ; i++){

            Jogo jogo = matchmaking.getJogos().get(i);

            if(jogo.getId() == request.idPartida()){

                matchmaking.remove(i);

                return ResponseObject.success(HttpStatus.OK, "Partida removida com sucesso");

            }

        }

        return ResponseObject.error(HttpStatus.NOT_FOUND, "Partida não encontrada");

    }

    @PutMapping("/joga")
    @Operation(
        summary = "API UTILIZADA PARA A REALIZAÇÃO DA JOGADA DO JACKPOT",
        responses = {
            @ApiResponse(
                responseCode = "201",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)   
                )
            ),
            @ApiResponse(
                responseCode = "404",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            )
        }
    )
    public ResponseEntity<ResponseObject> joga(@RequestBody JogaRequest request){

        ResponseEntity<ResponseObject> validate = request.validate();

        if(validate != null){

            return validate;

        }

        Jogo jogo = null;
        int id = -1;

        for(Jogo jogo1 : matchmaking.getJogos()){

            if(jogo1.getId() == request.idPartida()){

                jogo = jogo1;
                id++;

                break;

            }

        }

        if(jogo == null){

            formataResponse(
                HttpStatus.NOT_FOUND, 
                ResponseObject.error("Nenhuma partida encontrada com este id")
            );

        }

        if(jogo instanceof Jackpot jackpot){

            int valor = (jackpot.getUsuariosDtos().size() * 500) / 10;

            Collections.shuffle(jackpot.getUsuariosDtos());

            for(int i = 0 ; i < jackpot.getUsuariosDtos().size() ; i++){

                UsuarioDto usuarioDto = jackpot.getUsuariosDtos().get(i);

                if(i == 0){

                    usuarioRepository.alteraPontucaoDeUsuario(usuarioDto.getPoints() + (valor * 5), usuarioDto.id());

                } else if(i == 1){

                    usuarioRepository.alteraPontucaoDeUsuario(usuarioDto.getPoints() + (valor * 3), usuarioDto.id());

                } else if(i == 2){

                    usuarioRepository.alteraPontucaoDeUsuario(usuarioDto.getPoints() + (valor * 2), usuarioDto.id());

                } else {

                    usuarioRepository.alteraPontucaoDeUsuario(usuarioDto.getPoints() - 500, usuarioDto.id());

                }

            }   

            matchmaking.remove(id);

        } else if(jogo instanceof FlipCoin flipCoin){

            long idJogador = 0;

            for(Map.Entry<Long, Long> entry : flipCoin.getJogadorEJogada().entrySet()){

                if(entry.getValue() == request.jogada()){

                    idJogador = entry.getKey();
                    break;

                }

            }

            if(idJogador == 0){

                flipCoin.getJogadorEJogada().put(request.idJogador(), request.jogada());

                return formataResponse(
                    HttpStatus.CONFLICT, 
                    ResponseObject.builder().error("Jogada efeituada").build()
                );

            } else {

                return formataResponse(
                    HttpStatus.CONFLICT, 
                    ResponseObject.builder().error("Jogada ja realizada por outro jogador").build()
                );

            }

        }

        return formataResponse(
            HttpStatus.OK, 
            ResponseObject.success("Jackpot finalizado com sucesso")
        );

    }
        
    @PostMapping("/cria")
    @Operation(
        summary = "API UTILIZADA PARA A CRIAÇÃO DE PARTIDAS",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "A PARTIDA FOI CRIADO COM SUCESSO",
                content = @Content(
                    schema = @Schema(implementation = CriaPartidaResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "ALGUM DOS ITENS DA REQUEST É INVALIDO",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)   
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "USUARIO QUE SOLICITOU A CRIAÇÃO NÃO FOI ENCONTRADO",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "ALGUM PROBLEMA OCORREU NO SERVIDOR",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            )
        }
    )
    public ResponseEntity<ResponseObject> criaPartida(
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        @RequestBody CriaPartidaRequest request){

        ResponseEntity<ResponseObject> validate = request.validate();

        if(validate != null){

            return validate;

        }

        List<UsuarioDto> usuariosDto = usuarioRepository.coletaUsuarioComId(request.id());

        if(usuariosDto == null || usuariosDto.size() < 1){

            return formataResponse(
                HttpStatus.NOT_FOUND,
                ResponseObject.builder().error("O usuario que solicitou a criação da partida não existe").build()
            );

        }

        long id = matchmaking.getJogos().size(); 

        switch (request.gameTypeId()) {
            case 1 -> {
                matchmaking.addJogo(
                    new FlipCoin(usuariosDto.get(0), id, request.jogada())
                );
            }
            case 2 -> {
                matchmaking.addJogo(
                    new Jackpot(usuariosDto.get(0), id)
                );
            }
            case 3 -> {
                matchmaking.addJogo(
                    new Robots(usuariosDto.get(0), id, request.jogada())
                );
            }
            default -> {
                return formataResponse(
                    HttpStatus.BAD_REQUEST, 
                    ResponseObject.builder().error("O id do jogo desejado não existe").build()
                );
            }
        }

        return formataResponse(
            HttpStatus.CREATED, 
            new CriaPartidaResponse(
                matchmaking.getJogos().size() - 1, 
                "Partida criada com sucesso"
            )
        );

    }

    @GetMapping("/coleta")
    @Operation(
        summary = "API UTILIZADA PARA A CRIAÇÃO DE PARTIDAS",
        responses = {
            @ApiResponse(
                responseCode = "404",
                description = "NENHUMA PARTIDA FOI ENCONTRADA",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            ),
            @ApiResponse(
                responseCode = "200",
                description = "PARTIDAS ENCONTRADAS",
                content = @Content(
                    schema = @Schema(implementation = ColetaPartidasResponse.class)
                )
            )
        }
    )
    public ResponseEntity<ResponseObject> coletaPartidas(){

        if(matchmaking.getJogos().size() < 1){

            return formataResponse(
                HttpStatus.NOT_FOUND, 
                ResponseObject.builder().error("Nenhum jogo foi encontrado").build()
            );

        }

        return formataResponse(
            HttpStatus.OK, 
            new ColetaPartidasResponse(matchmaking.getJogos(), "Partidas encontradas")
        );

    }

    @PutMapping("/adiciona_ou_remove_usuario")
    @Operation(
        summary = "API UTILIZADA PARA A ADIÇÃO OU REMOÇÃO DE UM USUARIO DE UMA PARTIDA",
        responses = {
            @ApiResponse(
                responseCode = "400",
                description = "ALGUM ITEM NA REQUEST ESTA INVALIDO",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "USUARIO, JOGO OU PARTIDAS NÃO FORAM ENCONTRADAS",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            ),
            @ApiResponse(
                responseCode = "200",
                description = "USUARIO ADICIONADO COM SUCESSO",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            )
        }
    )
    public ResponseEntity<ResponseObject> adicionaOuRemoveUsuario(
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        @RequestBody AdicionaOuRemoveUsuarioRequest request){

        ResponseEntity<ResponseObject> validate = request.validate();

        if(validate != null){

            return validate;

        }

        List<UsuarioDto> usuariosDto = usuarioRepository.coletaUsuarioComId(request.idUsuario());

        if(usuariosDto == null || usuariosDto.size() < 1){

            return formataResponse(
                HttpStatus.NOT_FOUND, 
                ResponseObject.builder().error("Usuario não foi encontrado").build()
            );

        }

        UsuarioDto usuarioDto = usuariosDto.get(0);

        if(request.idPartida() > matchmaking.getJogos().size()){

            return formataResponse(
                HttpStatus.NOT_FOUND, 
                ResponseObject.builder().error("Partida não encontrada").build()
            );

        }

        Jogo jogo = null;

        int id = -1;

        for(Jogo jogo1 : matchmaking.getJogos()){

            if(jogo1.getId() == request.idPartida()){

                jogo = jogo1;

                id++;

                break;

            }

        }

        if(jogo == null){

            return formataResponse(
                HttpStatus.NOT_FOUND, 
                ResponseObject.builder().error("Partida não encontrada").build()
            );

        }

        if(jogo instanceof FlipCoin flipCoin){

            for(int i = 0 ; i < flipCoin.getUsuariosDtos().size() ; i++){

                if(flipCoin.getUsuariosDtos().get(i).id() == request.idUsuario()){

                    flipCoin.getUsuariosDtos().remove(i);

                    if(flipCoin.getUsuariosDtos().size() < 1){

                        matchmaking.remove(id);

                    } else {

                        flipCoin.getUsuariosDtos().getFirst().setCreator(true);

                    }

                    return formataResponse(
                        HttpStatus.OK, 
                        ResponseObject.builder().success("Usuario removido com sucesso").build()
                    );

                }

            }

            flipCoin.getUsuariosDtos().add(usuarioDto); 

            return formataResponse(
                HttpStatus.OK, 
                ResponseObject.builder().success("Usuario adicionado com sucesso").build()
            );

        } else if(jogo instanceof Jackpot jackpot){

            int i = 0;

            for(UsuarioDto usuarioDto2 : jackpot.getUsuariosDtos()){

                if(usuarioDto2.id() == request.idUsuario()){

                    jackpot.getUsuariosDtos().remove(i);

                    if(jackpot.getUsuariosDtos().size() < 1){

                        matchmaking.remove(id);

                    } else {

                        jackpot.getUsuariosDtos().getFirst().setCreator(true);

                    }

                    return formataResponse(
                        HttpStatus.OK, 
                        ResponseObject.builder().success("Usuario removido com sucesso").build()
                    );

                }

                i++;

            }

            jackpot.getUsuariosDtos().add(usuarioDto);

            return formataResponse(
                HttpStatus.OK, 
                ResponseObject.builder().success("Usuario adicionado com sucesso").build()
            );

        } else if(jogo instanceof Robots robots){

            int i = 0;

            for(UsuarioDto usuarioDto2 : robots.getUsuariosDtos()){

                if(usuarioDto2.id() == request.idUsuario()){

                    robots.getUsuariosDtos().remove(i);

                    if(robots.getUsuariosDtos().size() < 1){

                        matchmaking.remove(id);

                    } else {

                        robots.getUsuariosDtos().getFirst().setCreator(true);

                    }

                    return formataResponse(
                        HttpStatus.OK, 
                        ResponseObject.builder().success("Usuario removido com sucesso").build()
                    );

                }

                i++;

            }

            robots.getUsuariosDtos().add(usuarioDto);

            return formataResponse(
                HttpStatus.OK, 
                ResponseObject.builder().success("Usuario adicionado com sucesso").build()
            );

        }

        return formataResponse(
            HttpStatus.NOT_FOUND, 
            ResponseObject.builder().error("Tipo de jogo desconhecido").build()
        );

    }

}
