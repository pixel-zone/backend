package br.com.pixelzone.pixelzone.repositories.mysql.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.pixelzone.pixelzone.dtos.usertype.UserTypeDto;

public class UserTypeRowMapper implements RowMapper<UserTypeDto>{

	@Override
	public UserTypeDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new UserTypeDto(
            rs.getLong("id"), 
            rs.getString("name")
        );

	}
    
}
