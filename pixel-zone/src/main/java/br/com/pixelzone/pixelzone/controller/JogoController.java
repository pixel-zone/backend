package br.com.pixelzone.pixelzone.controller;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import br.com.pixelzone.pixelzone.dtos.games.AlteraJogoRequest;
import br.com.pixelzone.pixelzone.dtos.games.AlteraJogoResponse;
import br.com.pixelzone.pixelzone.dtos.games.ColetaJogoRequest;
import br.com.pixelzone.pixelzone.dtos.games.ColetaJogosResponse;
import br.com.pixelzone.pixelzone.dtos.games.CriaJogoRequest;
import br.com.pixelzone.pixelzone.dtos.games.CriaJogoResponse;
import br.com.pixelzone.pixelzone.dtos.games.JogoDto;
import br.com.pixelzone.pixelzone.dtos.games.RemoveJogoRequest;
import br.com.pixelzone.pixelzone.repositories.mysql.JogoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/pixel_zone/jogo")
@Tag(
    name = "APIs-PIXEL-ZONE: JOGO",
    description = "CONTEM TODOS OS ENPOINTS RELACIONADOS A TABELA DE TIPOS DE JOGOS"
)
public class JogoController {
 
    @Autowired
    private JogoRepository jogoRepository;

    @PostMapping("/coleta")
    @Operation(
        summary = "API UTILIZADA PARA A COLETA DE UM JOGO",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "JOGO FOI COLETADO COM SUCESSO",
                content = @Content(
                    schema = @Schema(implementation = ColetaJogosResponse.class)
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
                responseCode = "500",
                description = "ALGUM PROBLEMA OCORREU NO SERVIDOR",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "JOGO NÃO PODE SER COLETADO POIS NÃO FOI ENCONTRADO",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            )
        }
    )
    public ResponseEntity<ResponseObject> coletaJogo(
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        @RequestBody ColetaJogoRequest request){

        List<JogoDto> jogos = null;

        if(request.id() != null){

            jogos = jogoRepository.coletaJogo(request.id());

        } else {

            jogos = jogoRepository.coletaJogos();

        }

        if(jogos == null){

            return formataResponse(
                HttpStatus.NOT_FOUND, 
                ResponseObject.builder().error("Nenhum jogo foi encontrado").build()
            );

        }

        return formataResponse(
            HttpStatus.OK, 
            new ColetaJogosResponse(jogos, "Jogos encontrados com sucesso")
        );

    }

    @DeleteMapping("/remove")
    @Operation(
        summary = "API UTILIZADA PARA A REMOÇÃO DE UM JOGO",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "JOGO FOI REMOVIDO COM SUCESSO",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
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
                responseCode = "500",
                description = "ALGUM PROBLEMA OCORREU NO SERVIDOR",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            )
        }
    )
    public ResponseEntity<ResponseObject> removeJogo(
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        @RequestBody RemoveJogoRequest request){

        ResponseEntity<ResponseObject> validate = request.validate();

        if(validate != null){

            return validate;

        }

        jogoRepository.excluiJogo(request.id());

        return formataResponse(
            HttpStatus.OK, 
            ResponseObject.success("Jogo removido")
        );

    }

    @PutMapping("/altera")
    @Operation(
        summary = "API UTILIZADA PARA A ALTERAÇÃO DE UM JOGO",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "JOGO FOI ALTERADO COM SUCESSO",
                content = @Content(
                    schema = @Schema(implementation = AlteraJogoResponse.class)
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
                responseCode = "500",
                description = "ALGUM PROBLEMA OCORREU NO SERVIDOR",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "JOGO NÃO PODE SER ALTERADO POIS NÃO FOI ENCONTRADO",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            )
        }
    )
    public ResponseEntity<ResponseObject> alteraJogo(
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        @RequestBody AlteraJogoRequest request){

        ResponseEntity<ResponseObject> validate = request.validate();

        if(validate != null){

            return validate;

        }

        /*BigInteger idJogo =*/ jogoRepository.atualizaJogo(request.nome(), request.id());

        // if(idJogo == null){

        //     return formataResponse(
        //         HttpStatus.NOT_FOUND, 
        //         ResponseObject.builder().error("Jogo não foi encontrado").build()
        //     );

        // }

        return formataResponse(
            HttpStatus.OK, 
            new AlteraJogoResponse(new BigInteger(request.id() + ""), "Jogo alterado com sucesso")
        );

    }

    @PostMapping("/cria")
    @Operation(
        summary = "API UTILIZADA PARA A CRIAÇÃO DE UM JOGO",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "JOGO FOI CRIADO COM SUCESSO",
                content = @Content(
                    schema = @Schema(implementation = CriaJogoResponse.class)
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
                responseCode = "500",
                description = "ALGUM PROBLEMA OCORREU NO SERVIDOR",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            )
        }
    )
    public ResponseEntity<ResponseObject> criaJogo(
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        @RequestBody CriaJogoRequest request){

        ResponseEntity<ResponseObject> validate = request.validate();

        if(validate != null){

            return validate;

        }

        BigInteger idJogo = jogoRepository.criaJogo(request.nome());

        if(idJogo == null){

            return formataResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                ResponseObject.builder().error("Jogo não criado").build()
            );

        }

        return formataResponse(
            HttpStatus.OK, 
            new CriaJogoResponse(idJogo, "Jogo criado com sucesso")
        );

    }

}