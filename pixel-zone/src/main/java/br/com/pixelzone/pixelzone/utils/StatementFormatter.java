package br.com.pixelzone.pixelzone.utils;

import br.com.pixelzone.pixelzone.dtos.conta.AlteraUsuarioRequest;

public class StatementFormatter {

    public static final String format(AlteraUsuarioRequest request) {

        StringBuilder sb = new StringBuilder();

        formataCall(sb, "email", "'" + request.email() + "'");
        formataCall(sb, "password", "'" + request.senha() + "'");
        formataCall(sb, "username", "'" + request.username() + "'");
        formataCall(sb, "chosen_skin", request.chosenSkin());

        return crop(sb);
        
	}

    private static final String crop(StringBuilder updateStatement){

        int position = updateStatement.lastIndexOf(",");

        return String.valueOf(
            updateStatement.replace(
                position, 
                position + 1, 
                ""
            )
        );

    }

    private static final void formataCall(StringBuilder sb, String nome, Object valor){

        if(((String) valor).contains("null")){

            return;

        }

        sb.append(
            valor != null ? nome + " = " + valor + ",\n" : ""
        );

    }
    
}
