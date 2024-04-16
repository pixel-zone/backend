package br.com.pixelzone.pixelzone.dtos.conta;

import static br.com.pixelzone.pixelzone.utils.ResponseUtils.formataResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pixelzone.pixelzone.dtos.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "adiciona_pontuacao_ao_usuario_request",
    description = "REQUEST QUE CONTEM TODOS OS DADOS NECESSARIOS PARA ADICIONAR PONTUAÇÃO A UM USUARIO"
)
public record AlteraPontuacaoDoUsuarioRequest(
    
    @Schema(
        name = "id",
        nullable = false,
        description = "ID DO USUARIO QUE TERA A SUA PONTUAÇÃO ALTERADA"
    )
    @JsonProperty("id") Long id,
    
    @Schema(
        name = "pontuacao",
        nullable = false,
        description = "PONTUACAO QUE SERA ADICIONADA OU REMOVIDA"
    )
    @JsonProperty("pontuacao") Long pontuacao

) {

    public ResponseEntity<ResponseObject> validate(){

        System.out.println("hi: " + id + ", pont: " + pontuacao);

        if(id == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Id do usuario não pode ser null").build()
            );

        }

        if(pontuacao == null){

            return formataResponse(
                HttpStatus.BAD_REQUEST, 
                ResponseObject.builder().error("Pontuação não pode ser null").build()
            );

        }

        return null;

    }

}
