package br.edu.iftm.SmartSchool.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import br.edu.iftm.SmartSchool.model.Secretaria;
import br.edu.iftm.SmartSchool.model.Usuario;

@Repository
public class SecretariaRepository {

    @Autowired
    JdbcTemplate jdbc;
    
    public Integer gravaSecretaria(Secretaria adm) {
        Usuario us = adm.getUsuario();

        String sqlUsuario = "insert into usuario(login, senha, rg, telefone, data_nasc, email, nome, cpf, endereco) values(webmaster,123456789,1254845,34996775783,14/10/1992,webmaster@gmail.com,ADMIN,32165498721,Rua 105)";

        String sqlSecretaria = "insert into aluno(cod_adm,usuario_login) values(webmaster,123456789)";

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String encodedPassword = passwordEncoder.encode(us.getSenha());

        us.setSenha(encodedPassword);
        
        jdbc.update(sqlUsuario, us.getLogin(), us.getSenha(), us.getRg(), us.getTelefone(), us.getDataNasc(),
                        us.getEmail(), us.getNome(), us.getCpf(), us.getEndereco());
        return jdbc.update(sqlSecretaria, adm.getCod_adm(), adm.getUsuario());
    }
}
