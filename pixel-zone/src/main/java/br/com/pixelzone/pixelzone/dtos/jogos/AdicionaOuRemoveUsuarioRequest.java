package br.com.pixelzone.pixelzone.dtos.jogos;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "adiciona_ou_remove_usuario_request",
    description = "REQUEST QUE CONTEM TODOS OS DADOS NECESSARIOS PARA ADICIONAR OU REMOVE UM USUARIO DE UMA PARTIDA"
)
public record AdicionaOuRemoveUsuarioRequest(

    @Schema(
        name = "id_usuario",
        nullable = false,
        description = "ID DO USUARIO QUE TERA SEU ESTADO ALTERADO"
    )
    @JsonProperty("id_usuario") Long idUsuario,

    @Schema(
        name = "id_partida",
        nullable = false,
        description = "ID DA PARTIDA NO QUAL O USUARIO IS INGRESSAR OU DO QUAL ELE IRA SAIR"
    )
    @JsonProperty("id_partida") Integer idPartida

) {

    public ResponseEntity<ResponseObject> validate(){

        if(idUsuario == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Id do usuario não foi adicionado a request").build()
            );

        }

        if(idPartida == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Id da partida não foi adicionada a request").build()
            );

        }

        return null;

    }
    
}
