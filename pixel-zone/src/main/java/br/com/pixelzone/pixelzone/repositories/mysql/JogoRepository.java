package br.com.pixelzone.pixelzone.repositories.mysql;

import java.math.BigInteger;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import br.com.pixelzone.pixelzone.dtos.games.JogoDto;
import br.com.pixelzone.pixelzone.repositories.mysql.rowmappers.JogoRowmappers;

@Repository
public class JogoRepository {

    @Autowired
    private DataSource dataSource;

    private NamedParameterJdbcTemplate jdbcTemplate;

    public BigInteger criaJogo(String name){

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String sql = String.format("""
            INSERT INTO games (name)
            VALUES ('%s')
        """, name);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource map = new MapSqlParameterSource();

        jdbcTemplate.update(sql, map, keyHolder);

        return keyHolder.getKeyAs(BigInteger.class);

    }

    public BigInteger atualizaJogo(String name, long id){

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String sql = String.format("""
            UPDATE 
                games
            SET
                name = '%s'
            WHERE 
                id = :id
        """, name);

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("id", id);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, map, keyHolder);

        return keyHolder.getKeyAs(BigInteger.class);

    }

    public void excluiJogo(long id){

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String sql = """
            UPDATE
                games
            SET
                excluido = UTC_TIMESTAMP()
            WHERE 
                id = :id;
        """;

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("id", id);

        jdbcTemplate.update(sql, map);

    }

    public List<JogoDto> coletaJogos(){

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String sql = """
            SELECT 
                id, 
                name
            FROM 
                games
        """;

        try {

            return jdbcTemplate.query(sql, new JogoRowmappers());
            
        } catch (EmptyResultDataAccessException e) {

            return null;

        }

    }

    public List<JogoDto> coletaJogo(long id){

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String sql = """
            SELECT
                id,
                name
            FROM
                games
            WHERE 
                id = :id;
        """;

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("id", id);

        try {

            return jdbcTemplate.query(sql, map, new JogoRowmappers());
            
        } catch (EmptyResultDataAccessException e) {

            return null;

        }

    }
    
}
