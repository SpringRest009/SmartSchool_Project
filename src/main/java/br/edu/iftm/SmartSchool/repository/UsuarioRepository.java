package br.edu.iftm.SmartSchool.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.edu.iftm.SmartSchool.model.Usuario;

@Repository
public class UsuarioRepository {
    @Autowired
    JdbcTemplate jdbc;
    public Usuario buscaPorLogin(String username) {
        System.out.println("---------------------------------->" + username);
        return jdbc.queryForObject(
                "select * from usuario where login = ?",
                (res, linha) -> {
                    return new Usuario(res.getString("login"), res.getString("senha"),
                    res.getString("rg"),
                    res.getString("telefone"), res.getDate("data_nasc"),
                    res.getString("email"),
                    res.getString("nome"), res.getString("cpf"),
                    res.getString("endereco"));},
                username);
    }
}
