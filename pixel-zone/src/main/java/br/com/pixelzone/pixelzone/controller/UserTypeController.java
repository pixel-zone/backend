package br.com.pixelzone.pixelzone.controller;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;
import br.com.pixelzone.pixelzone.dtos.usertype.AlteraUserTypeResponse;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import br.com.pixelzone.pixelzone.dtos.usertype.AlteraUserTypeRequest;
import br.com.pixelzone.pixelzone.dtos.usertype.ColetaUserTypesRequest;
import br.com.pixelzone.pixelzone.dtos.usertype.ColetaUserTypesResponse;
import br.com.pixelzone.pixelzone.dtos.usertype.CriaUserTypeRequest;
import br.com.pixelzone.pixelzone.dtos.usertype.CriaUserTypeResponse;
import br.com.pixelzone.pixelzone.dtos.usertype.UserTypeDto;
import br.com.pixelzone.pixelzone.repositories.mysql.UserTypeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET, RequestMethod.DELETE})
@RequestMapping("/api/v1/pixel_zone/user_type")
@Tag(
    name = "APIs-PIXEL-ZONE: USER TYPE",
    description = "CONTEM TODOS OS ENPOINTS RELACIONADOS A TABELA DE USER TYPE"
)
public class UserTypeController {

    @Autowired
    private UserTypeRepository userTypeRepository;

    @PostMapping("/altera")
    @Operation(
        summary = "API UTILIZADA PARA A ALTERAÇÃO DE UM USER TYPE",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "USER TYPE FOI COLETADO COM SUCESSO",
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
                description = "CHAVE NÃO FOI RETORNADA APÓS OPERAÇÃO",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            )
        }
    )
    public ResponseEntity<ResponseObject> alteraUserType(
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        @RequestBody AlteraUserTypeRequest request){

        ResponseEntity<ResponseObject> validate = request.validate();

        if(validate != null){

            return validate;

        }

        /*BigInteger id =*/ userTypeRepository.alteraUserType(request.id(), request.name());

        // if(id == null){

        //     return formataResponse(
        //         HttpStatus.INTERNAL_SERVER_ERROR, 
        //         ResponseObject.error("O servidor não retornou uma chave após a operação, isto pode ou não ter funcionado")
        //     );

        // }

        return formataResponse(
            HttpStatus.OK, 
            AlteraUserTypeResponse.success(new BigInteger(request.id() + ""), "User type alterado com sucesso")
        );

    }

    @PostMapping("/cria")
    @Operation(
        summary = "API UTILIZADA PARA A CRIAÇÃO DE UM USER TYPE",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "USER TYPE FOI CRIADO COM SUCESSO",
                content = @Content(
                    schema = @Schema(implementation = CriaUserTypeResponse.class)
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
    public ResponseEntity<ResponseObject> criaUserType(
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        @RequestBody CriaUserTypeRequest request){

        ResponseEntity<ResponseObject> validate = request.validate();

        if(validate != null){

            return validate;

        }

        BigInteger id = userTypeRepository.criaNovoUserType(request.name());

        if(id == null){

            return formataResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                ResponseObject.error("Nenhuma key foi retornada do servidos ao criar o usuario")
            );

        }

        return formataResponse(
            HttpStatus.OK, 
            CriaUserTypeResponse.success(id, "Tipo de usuario criado com sucesso")
        );

    }

    @GetMapping("/coleta")
    @Operation(
        summary = "API UTILIZADA PARA A COLETA DE UM USER TYPE",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "USER TYPE FOI COLETADO COM SUCESSO",
                content = @Content(
                    schema = @Schema(implementation = ColetaUserTypesResponse.class)
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
                description = "USER TYPE NÃO ENCONTRADO",
                content = @Content(
                    schema = @Schema(implementation = ResponseObject.class)
                )
            )
        }
    )
    public ResponseEntity<ResponseObject> coletaUserTypes(
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        @RequestBody ColetaUserTypesRequest request){

        List<UserTypeDto> userTypes = null;

        if(request.id() == null){

            userTypes = userTypeRepository.coletaUserTypes();

        } else {

            userTypes = userTypeRepository.coletaUserType(request.id());

        }

        if(userTypes == null){

            return formataResponse(
                HttpStatus.NOT_FOUND, 
                ResponseObject.error("Nenhum user type foi encontrado")
            );

        }

        return formataResponse(
            HttpStatus.OK, 
            ColetaUserTypesResponse.success(userTypes, "Usuarios coletados com sucesso")
        );

    }

}
