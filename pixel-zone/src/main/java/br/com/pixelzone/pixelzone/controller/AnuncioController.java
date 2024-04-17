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
import br.com.pixelzone.pixelzone.dtos.anuncio.AlteraAnuncioRequest;
import br.com.pixelzone.pixelzone.dtos.anuncio.AlteraAnuncioResponse;
import br.com.pixelzone.pixelzone.dtos.anuncio.AnuncioDto;
import br.com.pixelzone.pixelzone.dtos.anuncio.ColetaAnuncioRequest;
import br.com.pixelzone.pixelzone.dtos.anuncio.ColetaAnuncioResponse;
import br.com.pixelzone.pixelzone.dtos.anuncio.CriaAnuncioRequest;
import br.com.pixelzone.pixelzone.dtos.anuncio.CriaAnuncioResponse;
import br.com.pixelzone.pixelzone.dtos.anuncio.RemoveAnuncioRequest;
import br.com.pixelzone.pixelzone.repositories.mysql.AnuncioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/pixel_zone/anuncio")
@Tag(
    name = "APIs-PIXEL-ZONE: ANUNCIO",
    description = "CONTEM TODOS OS ENPOINTS RELACIONADOS AOS ANUNCIOS"
)
public class AnuncioController {

    @Autowired
    private AnuncioRepository anuncioRepository;

    @PostMapping("/coleta")
    @Operation(
        summary = "API UTILIZADA PARA A COLETA DE UM ANUNCIO",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "ANUNCIO FOI COLETADO COM SUCESSO",
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
                responseCode = "404",
                description = "ANUNCIO NÃO FOI ENCONTRADO",
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
    public ResponseEntity<ResponseObject> coletaAnuncio(
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        @RequestBody ColetaAnuncioRequest request){

        ResponseEntity<ResponseObject> validate = request.validate();

        if(validate != null){

            return validate;

        }

        List<AnuncioDto> anuncios = null;

        if(request.idAnuncio() != null){

            anuncios = anuncioRepository.coletaAnunciosComIdAnuncio(request.idAnuncio());

        }

        if(request.idUsuario() != null){

            anuncios = anuncioRepository.coletaAnunciosComIdUsuario(request.idUsuario());

        }

        if(anuncios != null){

            return formataResponse(
                HttpStatus.OK, 
                new ColetaAnuncioResponse(anuncios, "Anuncios encontrados")
            );

        }

        return formataResponse(
            HttpStatus.NOT_FOUND, 
            ResponseObject.builder().error("Nenhum anuncio foi encontrado").build()
        );

    }

    @DeleteMapping("/remove")
    @Operation(
        summary = "API UTILIZADA PARA A REMOÇÃO DE UM ANUNCIO",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "ANUNCIO FOI REMOVIDO COM SUCESSO",
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
                responseCode = "404",
                description = "ANUNCIO NÃO FOI ENCONTRADO",
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
    public ResponseEntity<ResponseObject> removeAnuncio(
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        @RequestBody RemoveAnuncioRequest request){

        ResponseEntity<ResponseObject> validate = request.validate();

        if(validate != null){

            return validate;

        }

        List<AnuncioDto> anuncios = anuncioRepository.coletaAnunciosComIdAnuncio(request.id());

        if(anuncios == null || anuncios.size() < 1){

            return formataResponse(
                HttpStatus.NOT_FOUND, 
                ResponseObject.error("Nenhum anuncio foi encontrado")
            );

        }

        anuncioRepository.removeAnuncio(request.id());

        return formataResponse(
            HttpStatus.OK, 
            ResponseObject.builder().success("Anuncio removido com sucesso").build()
        );

    }

    @PutMapping("/altera")
    @Operation(
        summary = "API UTILIZADA PARA A ALTERAÇÃO DE UM ANUNCIO",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "ANUNCIO FOI ALTERADO COM SUCESSO",
                content = @Content(
                    schema = @Schema(implementation = AlteraAnuncioResponse.class)
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
                description = "ANUNCIO NÃO FOI ENCONTRADO",
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
    public ResponseEntity<ResponseObject> alteraAnuncio(
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        @RequestBody AlteraAnuncioRequest request){

        ResponseEntity<ResponseObject> validate = request.validate();

        if(validate != null){

            return validate;

        }

        
        List<AnuncioDto> anunciosDto = anuncioRepository.coletaAnunciosComIdAnuncio(request.id());
        
        if(anunciosDto == null){
            
            return formataResponse(
                HttpStatus.NOT_FOUND,
                ResponseObject.error("Anuncio não encontrado")
            );
                
        }

        /*BigInteger idAnuncio =*/ anuncioRepository.alteraAnuncio(request.id(), request.anuncio());

        return formataResponse(
            HttpStatus.OK, 
            new AlteraAnuncioResponse(new BigInteger(request.id() + ""), "Anuncio alterado")
        );

    }

    @PostMapping("/cria")
    @Operation(
        summary = "API UTILIZADA PARA A CRIAÇÃO DE UM ANUNCIO",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "ANUNCIO FOI CRIADO COM SUCESSO",
                content = @Content(
                    schema = @Schema(implementation = CriaAnuncioResponse.class)
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
    public ResponseEntity<ResponseObject> criaAnuncio(
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        @RequestBody CriaAnuncioRequest request){

        ResponseEntity<ResponseObject> validate = request.validate();

        if(validate != null){

            return validate;
            
        }

        BigInteger idAnuncio = anuncioRepository.criaAnuncio(request.idUsuario(), request.anuncio());

        if(idAnuncio == null){

            return formataResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                ResponseObject.builder().error("Anuncio não pode ser criado").build()
            );

        }

        return formataResponse(
            HttpStatus.CREATED, 
            new CriaAnuncioResponse(idAnuncio, "Anuncio criado com sucesso")
        );

    }

}
