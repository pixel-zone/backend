package br.com.pixelzone.pixelzone.repositories.mysql.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.pixelzone.pixelzone.dtos.conta.UsuarioDto;

public class UsuarioRowmappers implements RowMapper<UsuarioDto>{

	@Override
	public UsuarioDto mapRow(ResultSet rs, int arg1) throws SQLException {

        return new UsuarioDto(
            rs.getLong("id"), 
            rs.getString("username"), 
            rs.getString("email"), 
            rs.getLong("points"), 
            rs.getInt("user_type_id"), 
            rs.getLong("items")
        );

	}
    
}
