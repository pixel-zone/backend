package br.com.pixelzone.pixelzone.repositories.mysql.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pixelzone.pixelzone.dtos.conta.UsuarioDto;

public class UsuarioRowmappers implements RowMapper<UsuarioDto>{

	@Override
	public UsuarioDto mapRow(ResultSet rs, int arg1) throws SQLException {

        String items = rs.getString("items");

        try {
			return new UsuarioDto(
			    rs.getLong("id"), 
			    rs.getString("username"), 
			    rs.getString("email"), 
			    rs.getLong("points"), 
			    rs.getInt("type"), 
			    items == null ? new ArrayList<Long>() : new ObjectMapper().readValue(items, new TypeReference<List<Long>>(){}),
                false
			);
		} catch (JsonProcessingException e) {

            System.out.println("Erro ao deserializar o campo");

            return null;

		}

	}
    
}
