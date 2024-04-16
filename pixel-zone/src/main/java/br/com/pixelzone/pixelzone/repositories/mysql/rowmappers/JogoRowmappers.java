package br.com.pixelzone.pixelzone.repositories.mysql.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.pixelzone.pixelzone.dtos.games.JogoDto;

public class JogoRowmappers implements RowMapper<JogoDto>{

	@Override
	public JogoDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new JogoDto(
            rs.getLong("id"), 
            rs.getString("name")
        );

	}

    
}
