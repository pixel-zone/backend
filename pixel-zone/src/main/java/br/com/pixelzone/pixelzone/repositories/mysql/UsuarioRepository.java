package br.com.pixelzone.pixelzone.repositories.mysql;

import javax.sql.DataSource;

import static br.com.pixelzone.pixelzone.utils.StatementFormatter.format;

import java.math.BigInteger;
import java.util.List;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import br.com.pixelzone.pixelzone.dtos.conta.AlteraUsuarioRequest;
import br.com.pixelzone.pixelzone.dtos.conta.CriaUsuarioRequest;
import br.com.pixelzone.pixelzone.dtos.conta.UsuarioDto;
import br.com.pixelzone.pixelzone.repositories.mysql.rowmappers.UsuarioRowmappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Repository
public class UsuarioRepository {

    @Autowired
    private DataSource dataSource;

    private NamedParameterJdbcTemplate jdbcTemplate;

    public UsuarioDto login(String email, String senha){

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        
        String sql = String.format("""
            SELECT 
                id,
                username,
                email,
                points,
                user_type_id,
                items
            FROM 
                account
            WHERE 
                email = '%s'
                AND password = '%s'
        """, email, senha);

        MapSqlParameterSource map = new MapSqlParameterSource();

        try {

            return jdbcTemplate.queryForObject(sql, map, new UsuarioRowmappers());
            
        } catch (EmptyResultDataAccessException e) {

            return null;

        }

    }

    public BigInteger alteraPontucaoDeUsuario(long pontuacao, long id){

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String sql = """
            UPDATE
                account
            SET
                points = :pontuacao
            WHERE 
                id = :id;
        """;

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("pontuacao", pontuacao);
        map.addValue("id", id);

        KeyHolder keyholder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, map, keyholder);
    
        return keyholder.getKeyAs(BigInteger.class);

    }

    public void removeUsuario(long idUsuario){

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String sql = """
            UPDATE 
                account
            SET
                excluido = UTC_TIMESTAMP()
            WHERE 
                id = :idUsuario
        """;

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("idUsuario", idUsuario);

        jdbcTemplate.update(sql, map);

    }

    public Integer verificaSeOUsuarioEPermitido(String email, String username){

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String sql = String.format("""
            SELECT
                COUNT(id)
            FROM 
                account
            WHERE
                (
                    email LIKE '%s'
                    OR 
                    username LIKE '%s'
                )
                AND excluido IS NULL
        """, email, username);

        MapSqlParameterSource map = new MapSqlParameterSource();

        try {
            
            return jdbcTemplate.queryForObject(sql, map, Integer.class);

        } catch (EmptyResultDataAccessException e) {

            return null;

        }

    }

    public List<UsuarioDto> coletaUsuarioComUsername(String username){

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String sql = String.format("""
            SELECT 
                id,
                username,
                email,
                points,
                user_type_id,
                items
            FROM
                account
            WHERE 
                username LIKE '%s'
                AND excluido IS NULL 
        """, ("%" + username + "%"));

        try {

            return jdbcTemplate.query(sql, new UsuarioRowmappers());
            
        } catch (EmptyResultDataAccessException e) {

            return null;

        }

    }

    public List<UsuarioDto> coletaUsuarioComEmail(String email){

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String sql = String.format("""
            SELECT 
                id,
                username,
                email,
                points,
                user_type_id,
                items
            FROM
                account
            WHERE 
                email LIKE '%s'
                AND excluido IS NULL
        """, ("%" + email + "%"));

        try {

            return jdbcTemplate.query(sql, new UsuarioRowmappers());
            
        } catch (EmptyResultDataAccessException e) {

            return null;

        }

    }

    public List<UsuarioDto> coletaUsuarioComId(long id){

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String sql = """
            SELECT 
                id,
                username,
                email,
                points,
                user_type_id,
                items
            FROM
                account
            WHERE 
                id = :id
                AND excluido IS NULL
        """;

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("id", id);

        try {
        
            return jdbcTemplate.query(sql, map, new UsuarioRowmappers());
            
        } catch (EmptyResultDataAccessException e) {

            return null;

        }

    }

    public BigInteger atualizaUsuario(AlteraUsuarioRequest request){

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String sql = String.format("""
            UPDATE 
                account
            SET 
                %s
            WHERE 
                id = %d;
        """, format(request), request.id());

        MapSqlParameterSource map = new MapSqlParameterSource();

        KeyHolder keyholder = new GeneratedKeyHolder();

        map.addValue("id", request.id());

        jdbcTemplate.update(sql, map, keyholder);

        return keyholder.getKeyAs(BigInteger.class);

    } 

	public BigInteger insereUsuario(CriaUsuarioRequest request){

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String sql = String.format("""
            INSERT INTO account(username, email, password, points, items, user_type_id)
            VALUES('%s', '%s', '%s', 0, 0, :type);
        """, request.username(), request.email(), request.senha());

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("type", request.type());

        KeyHolder keyholder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, map, keyholder);

        return keyholder.getKeyAs(BigInteger.class);

    }

}
