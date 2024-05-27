package br.com.pixelzone.pixelzone.repositories.mysql.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.pixelzone.pixelzone.dtos.anuncio.AnuncioDto;

public class AnuncioDtoRowmappers implements RowMapper<AnuncioDto> {

	@Override
	public AnuncioDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new AnuncioDto(
            rs.getLong("id"),
            rs.getLong("id_usuario"),
            rs.getString("ad")
        );

	}
    
}
