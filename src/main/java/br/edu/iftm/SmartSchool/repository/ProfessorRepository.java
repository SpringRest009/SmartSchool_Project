package br.edu.iftm.SmartSchool.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.edu.iftm.SmartSchool.model.Professor;
import br.edu.iftm.SmartSchool.model.Usuario;

@Repository
public class ProfessorRepository {

        @Autowired
        JdbcTemplate jdbc;

        public ProfessorRepository() {

        }

        public List<Professor> buscaTodosProfessores() {
                String consulta = "SELECT * FROM professor, usuario where usuario.login = professor.usuario_login;";
                return jdbc.query(consulta,
                                (res, linha) -> new Professor(
                                                new Usuario(res.getString("login"), res.getString("senha"),
                                                                res.getString("rg"),
                                                                res.getString("telefone"), res.getDate("data_nasc"),
                                                                res.getString("email"),
                                                                res.getString("nome"), res.getString("cpf"),
                                                                res.getString("endereco")),
                                                res.getString("cod_professor")));
        }

        public Integer gravarProfessor(Professor professor) {
                Usuario us = professor.getUsuario();
                String sqlUsuario = "insert into usuario(login, senha, rg, telefone, data_nasc, email, nome, cpf, endereco) values(?,?,?,?,?,?,?,?,?)";
                String sqlProfessor = "insert into professor(cod_professor, usuario_login) values(?,?)";
                jdbc.update(sqlUsuario, us.getLogin(), us.getSenha(), us.getRg(), us.getTelefone(), us.getDataNasc(),
                                us.getEmail(), us.getNome(), us.getCpf(), us.getEndereco());
                                
                return jdbc.update(sqlProfessor, professor.getCod_professor(), professor.getUsuario().getLogin());
        }

        public Integer excluirProfessor(String id) {
                String sqlProfessor = "delete from professor where usuario_login = ?";
                String sqlUsuario = "delete from usuario where login = ?";
                jdbc.update(sqlProfessor, id);
                return jdbc.update(sqlUsuario, id);
        }

        public Integer atualizarProfessor(String cpf, Professor professor) {
                Usuario us = professor.getUsuario();
                String sqlProfessor = "update professor set cod_professor = ? where usuario_login = ?";
                String sqlUsuario = "update usuario set rg = ?, telefone = ?, data_nasc = ?, email = ?, nome = ?, cpf = ?, endereco = ? where login = ?";
                jdbc.update(sqlProfessor, professor.getCod_professor(), professor.getUsuario().getLogin());
                return jdbc.update(sqlUsuario, us.getRg(), us.getTelefone(), us.getDataNasc(), us.getEmail(),
                                us.getNome(), us.getCpf(), us.getEndereco(), us.getLogin());
        }

        public Professor buscaPorLoginP(String login) {
                return jdbc.queryForObject(
                                "SELECT * FROM professor, usuario where usuario.login = professor.usuario_login and usuario.login = ? ;",
                                (res, linha) -> new Professor(
                                                new Usuario(res.getString("login"), res.getString("senha"),
                                                                res.getString("rg"),
                                                                res.getString("telefone"), res.getDate("data_nasc"),
                                                                res.getString("email"),
                                                                res.getString("nome"), res.getString("cpf"),
                                                                res.getString("endereco")),
                                                res.getString("cod_professor")),
                                login);
        }

        public Professor buscaPorCpfP(String cpf) {
                Professor professor = null;
                try {
                        professor = jdbc.queryForObject(
                                        "select * from professor, usuario where usuario.login = professor.usuario_login and usuario.cpf = ?",
                                        (res, rowNum) -> {
                                                return new Professor(
                                                                new Usuario(res.getString("login"),
                                                                                res.getString("senha"),
                                                                                res.getString("rg"),
                                                                                res.getString("telefone"),
                                                                                res.getDate("data_nasc"),
                                                                                res.getString("email"),
                                                                                res.getString("nome"),
                                                                                res.getString("cpf"),
                                                                                res.getString("endereco")),
                                                                res.getString("cod_professor"));
                                        }, new Object[] { cpf });
                } catch (Exception e) {
                        System.out.println(e.getLocalizedMessage());
                }
                return professor;
        }
}
