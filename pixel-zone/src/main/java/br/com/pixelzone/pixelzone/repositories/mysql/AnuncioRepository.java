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

import br.com.pixelzone.pixelzone.dtos.anuncio.AnuncioDto;
import br.com.pixelzone.pixelzone.repositories.mysql.rowmappers.AnuncioDtoRowmappers;

@Repository
public class AnuncioRepository {

    @Autowired
    private DataSource dataSource;

    private NamedParameterJdbcTemplate jdbcTemplate;

    public BigInteger criaAnuncio(long idUsuario, String imagem){

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String sql = """
            INSERT INTO ads(id_usuario, ad)
            VALUES(:idUsuario, :imagem)
        """;

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("idUsuario", idUsuario);
        map.addValue("imagem", imagem);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, map, keyHolder);

        return keyHolder.getKeyAs(BigInteger.class);

    }

	public BigInteger alteraAnuncio(Long id, String anuncio) {

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String sql = String.format("""
            UPDATE ads 
            SET ad = '%s'
        """, anuncio);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource map = new MapSqlParameterSource();

        jdbcTemplate.update(sql, map, keyHolder);

        return keyHolder.getKeyAs(BigInteger.class);

	}

	public void removeAnuncio(Long id) {

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String sql = """
            UPDATE ads 
            SET excluido = UTC_TIMESTAMP()
            WHERE id = :id
        """;

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("id", id);

        jdbcTemplate.update(sql, map);

	}

	public List<AnuncioDto> coletaAnunciosComIdUsuario(Long idUsuario) {

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String sql = """
            SELECT 
                id,
                id_usuario,
                ad
            FROM
                ads
            WHERE 
                id_usuario = :idUsuario
                WHERE excluido IS NULL
        """;

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("idUsuario", idUsuario);

        try {

            return jdbcTemplate.query(sql, map, new AnuncioDtoRowmappers());
            
        } catch (EmptyResultDataAccessException e) {

            return null;

        }

	}

	public List<AnuncioDto> coletaAnunciosComIdAnuncio(Long idAnuncio) {

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String sql = """
            SELECT
                id,
                id_usuario,
                ad
            FROM 
                ads
            WHERE 
                id = :id
                AND excluido IS NULL
        """;

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("id", idAnuncio);

        try {

            return jdbcTemplate.query(sql, map, new AnuncioDtoRowmappers());
            
        } catch (EmptyResultDataAccessException e) {

            return null;

        }

	}
    
}
