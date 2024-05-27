package br.com.pixelzone.pixelzone.controller;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;

import java.math.BigInteger;
import java.util.List;

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

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import br.com.pixelzone.pixelzone.dtos.conta.AlteraPontuacaoDoUsuarioRequest;
import br.com.pixelzone.pixelzone.dtos.conta.AlteraUsuarioRequest;
import br.com.pixelzone.pixelzone.dtos.conta.AlteraUsuarioResponse;
import br.com.pixelzone.pixelzone.dtos.conta.ColetaUsuarioRequest;
import br.com.pixelzone.pixelzone.dtos.conta.ColetaUsuariosResponse;
import br.com.pixelzone.pixelzone.dtos.conta.CriaUsuarioRequest;
import br.com.pixelzone.pixelzone.dtos.conta.CriaUsuarioResponse;
import br.com.pixelzone.pixelzone.dtos.conta.LeaderboardsDto;
import br.com.pixelzone.pixelzone.dtos.conta.RemoveUsuarioRequest;
import br.com.pixelzone.pixelzone.dtos.conta.CompraSkinRequest;
import br.com.pixelzone.pixelzone.dtos.conta.LoginRequest;
import br.com.pixelzone.pixelzone.dtos.conta.LoginResponse;
import br.com.pixelzone.pixelzone.dtos.conta.UsuarioDto;
import br.com.pixelzone.pixelzone.repositories.mysql.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import br.com.pixelzone.pixelzone.dtos.conta.CompraSkinResponse;

@RestController
@RequestMapping("/api/v1/pixel_zone/usuario")
@Tag(
    name = "APIs-PIXEL-ZONE: USUARIO",
    description = "CONTEM TODOS OS ENPOINTS RELACIONADOS A CONTA DO USUARIO"
)
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PutMapping("/compra_skin")
    @Operation(
        summary = "API UTILIZADA PARA A COMPRA DE SKINS",
        responses = {
            @ApiResponse(
                responseCode = "200",
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
            ),
            @ApiResponse(
                responseCode = "404",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            )        
        }
    )

    public ResponseEntity<ResponseObject> compraSkin(@RequestBody CompraSkinRequest request){

        ResponseEntity<ResponseObject> validate = request.validate();

        if(validate != null){

            return validate;

        }

        List<UsuarioDto> usuarioDto = usuarioRepository.coletaUsuarioComId(request.idUsuario());

        if(usuarioDto == null || usuarioDto.size() < 1){

            return ResponseObject.error(HttpStatus.NOT_FOUND, "Este usuario não existe");

        }

        usuarioDto.getFirst().getItems().add(request.idSkin());
        usuarioDto.getFirst().setPoints(usuarioDto.getFirst().getPoints() - 500);

        try {

			usuarioRepository.compraSkin(
			    usuarioDto.getFirst().getItems(), 
			    usuarioDto.getFirst().getPoints(), 
			    usuarioDto.getFirst().getId()
			);

		} catch (JsonProcessingException e) {

            return ResponseObject.error(HttpStatus.INTERNAL_SERVER_ERROR, "Problema ao efetuar a compra");

		}

        return CompraSkinResponse.answer(usuarioDto.getFirst());
        
    }

    @GetMapping("/leaderboard")
    @Operation(
        summary = "API UTILIZADA PARA A CRIAÇÃO DE UMA LEADERBOARD",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content = @Content(
                    schema = @Schema(implementation = LeaderboardsDto.class)
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
    public ResponseEntity<ResponseObject> criaLeaderboard(){

        List<UsuarioDto> usuarios = usuarioRepository.coletaLeaderboard();

        if(usuarios == null || usuarios.size() < 1){

            return ResponseObject.error(HttpStatus.NOT_FOUND, "Nenhum usuario esta presente no leaderboard");

        }

        return LeaderboardsDto.success(usuarios);

    }

    @PostMapping("/login")
    @Operation(
        summary = "API UTILIZADA PARA O LOGIN DO USUARIO",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "USUARIO FOI LOGADO COM SUCESSO",
                content = @Content(
                    schema = @Schema(implementation = LoginResponse.class)
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
                responseCode = "403",
                description = "USUARIO NÃO FOI ENCONTRADO",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            )
        }
    )
    public ResponseEntity<ResponseObject> login(
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        @RequestBody LoginRequest request){

        ResponseEntity<ResponseObject> validate = request.validate();

        if(validate != null){

            return validate;

        }

        UsuarioDto usuarioDto = usuarioRepository.login(request.email(), request.senha());

        if(usuarioDto == null){

            return formataResponse(
                HttpStatus.FORBIDDEN, 
                ResponseObject.builder().error("Usuario não encontrado").build()
            );

        }

        return formataResponse(
            HttpStatus.OK, 
            new LoginResponse(usuarioDto, "Usuario logado com sucesso")
        );

    }

    @PostMapping("/cria")
    @Operation(
        summary = "API UTILIZADA PARA A CRIAÇÃO DE UMA CONTAS",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "USUARIO FOI CRIADO COM SUCESSO",
                content = @Content(
                    schema = @Schema(implementation = CriaUsuarioResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "409",
                description = "ALGUM DOS ITENS DESEJADOS PELO USUARIO POSSUI UM CONFLITO",
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
    public ResponseEntity<ResponseObject> criaUsuario(
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        @RequestBody CriaUsuarioRequest request){

        ResponseEntity<ResponseObject> validate = request.validate();

        if(validate != null){

            return validate;

        }

        Integer numeroDeOcorrencias = usuarioRepository.verificaSeOUsuarioEPermitido(
            request.email(), 
            request.username()
        );

        if(numeroDeOcorrencias == null){

            return formataResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                ResponseObject.builder().error("Problema na conexão com o mysql").build()
            );

        }

        if(numeroDeOcorrencias > 0){

            return formataResponse(
                HttpStatus.CONFLICT, 
                ResponseObject.builder().error("Usuario ou email ja utilizados").build()
            );

        }

        BigInteger idUsuario = usuarioRepository.insereUsuario(request);

        if(idUsuario == null){

            return formataResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                ResponseObject.builder().error("O id do usuario criado retornou null").build()
            );

        }

        return formataResponse(
            HttpStatus.OK, 
            new CriaUsuarioResponse(idUsuario, "Tivemos sucesso chefe")
        );

    }

    @PutMapping("/altera")
    @Operation(
        summary = "API UTILIZADA PARA A ALTERAÇÃO DE UMA CONTAS",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "USUARIO FOI ALTERADO COM SUCESSO",
                content = @Content(
                    schema = @Schema(implementation = AlteraUsuarioResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "409",
                description = "ALGUM DOS ITENS DESEJADOS PELO USUARIO POSSUI UM CONFLITO",
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
    public ResponseEntity<ResponseObject> alteraUsuario(
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        @RequestBody AlteraUsuarioRequest request){

        ResponseEntity<ResponseObject> validate = request.validate();

        if(validate != null){

            return validate;

        }

        Integer numeroDeOcorrencias = usuarioRepository.verificaSeOUsuarioEPermitido(
            request.email(), 
            request.username()
        );

        if(numeroDeOcorrencias == null){

            return formataResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                ResponseObject.builder().error("Problema na conexão com o mysql").build()
            );

        }

        if(numeroDeOcorrencias > 0){

            return formataResponse(
                HttpStatus.CONFLICT, 
                ResponseObject.builder().error("Usuario ou email ja utilizados").build()
            );

        }

        /*BigInteger idUsuario =*/ usuarioRepository.atualizaUsuario(request);

        // if(idUsuario == null){

        //     return formataResponse(
        //         HttpStatus.INTERNAL_SERVER_ERROR, 
        //         ResponseObject.builder().error("O id do usuario alterado retornou null").build()
        //     );

        // }

        return formataResponse(
            HttpStatus.OK, 
            new AlteraUsuarioResponse(new BigInteger(request.id() + ""), "Usuario alterado com sucesso")
        );

    }

    @DeleteMapping("/remove")
    @Operation(
        summary = "API UTILIZADA PARA A REMOCAO DE CONTAS",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "USUARIO FOI REMOVIDO COM SUCESSO",
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
            )
        }
    )
    public ResponseEntity<ResponseObject> removeUsuario(
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        @RequestBody RemoveUsuarioRequest request){

        ResponseEntity<ResponseObject> validate = request.validate();

        if(validate != null){

            return validate;

        }
        
        usuarioRepository.removeUsuario(request.id());

        return formataResponse(
            HttpStatus.OK, 
            ResponseObject.builder().success("Usuario removido com sucesso").build()
        );

    }

    @PostMapping("/coleta")
    @Operation(
        summary = "API UTILIZADA PARA A COLETA DE USUARIOS",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "USUARIO COLETADO COM SUCESSO",
                content = @Content(
                    schema = @Schema(implementation = ColetaUsuariosResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "ALGUM DOS ITENS DA REQUEST POSSUI ALGUM ERRO",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "NENHUM USUARIO FOI ENCONTRADO",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            )
        }
    )
    public ResponseEntity<ResponseObject> coletaUsuario(
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        @RequestBody ColetaUsuarioRequest request){

        ResponseEntity<ResponseObject> validate = request.validate();

        if(validate != null){

            return validate;

        }

        List<UsuarioDto> usuarioDtos = null;

        if(request.id() != null){

            usuarioDtos = usuarioRepository.coletaUsuarioComId(request.id());

        }

        if(request.email() != null){

            usuarioDtos = usuarioRepository.coletaUsuarioComEmail(request.email());

        }

        if(request.username() != null){

            usuarioDtos = usuarioRepository.coletaUsuarioComUsername(request.username());

        }

        if(usuarioDtos == null || usuarioDtos.size() < 1){

            return formataResponse(
                HttpStatus.NOT_FOUND, 
                ResponseObject.builder().error("Nenhum usuario foi encontrado").build()
            );

        }

        return formataResponse(
            HttpStatus.OK, 
            new ColetaUsuariosResponse(usuarioDtos, "Usuario(s) encontrado(s)")
        );

    }
 
    @PutMapping("/adiciona_pontos")
    @Operation(
        summary = "API UTILIZADA PARA A ALTERAÇÃO DE PONTOS DE UM USUARIO",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "SUCESSO NA ALTERAÇÃO",
                content = @Content(
                    schema = @Schema(implementation = AlteraUsuarioResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "ALGUM ITEM DA REQUEST POSSUI UM ERRO",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "USUARIO NÃO FOI ENCONTRADO",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            )
        }
    )
    public ResponseEntity<ResponseObject> alteraPontuacaoDoUsuario(
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        @RequestBody AlteraPontuacaoDoUsuarioRequest request){

        ResponseEntity<ResponseObject> validate = request.validate();

        if(validate != null){

            return validate;

        }

        List<UsuarioDto> usuarios = usuarioRepository.coletaUsuarioComId(request.id());

        if(usuarios == null || usuarios.size() == 0){

            return formataResponse(
                HttpStatus.NOT_FOUND, 
                ResponseObject.builder().error("Usuario não encontrado").build()
            );

        }

        /*BigInteger idUsuario =*/ usuarioRepository.alteraPontucaoDeUsuario(
            (usuarios.get(0).points() + request.pontuacao()), 
            request.id()
        );

        // if(idUsuario == null){

        //     return formataResponse(
        //         HttpStatus.NOT_FOUND, 
        //         ResponseObject.builder().error("Id do usuario não retornado após execução da query").build()
        //     );

        // }

        return formataResponse(
            HttpStatus.OK, 
            new AlteraUsuarioResponse(new BigInteger(request.id() + ""), "Pontuação atualizaca com sucesso")
        );

    }

}
