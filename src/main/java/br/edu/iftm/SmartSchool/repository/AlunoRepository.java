package br.edu.iftm.SmartSchool.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import br.edu.iftm.SmartSchool.model.Aluno;
import br.edu.iftm.SmartSchool.model.Usuario;

@Repository
public class AlunoRepository {

        @Autowired
        JdbcTemplate jdbc;

        public AlunoRepository() {

        }

        public List<Aluno> buscaTodosAlunos() {
                String consulta = "SELECT * FROM aluno, usuario where usuario.login = aluno.usuario_login;";
                return jdbc.query(consulta,
                                (res, linha) -> new Aluno(
                                                new Usuario(res.getString("login"), res.getString("senha"),
                                                                res.getString("rg"),
                                                                res.getString("telefone"), res.getDate("data_nasc"),
                                                                res.getString("email"),
                                                                res.getString("nome"), res.getString("cpf"),
                                                                res.getString("endereco")),
                                                res.getString("matricula"), res.getString("nome_mae"),
                                                res.getString("nome_pai"),
                                                res.getDate("data_matricula"), res.getString("tel_responsavel")));
        }

        public Integer gravaAluno(Aluno aluno) {
                Usuario us = aluno.getUsuario();
                String sqlUsuario = "insert into usuario(login, senha, rg, telefone, data_nasc, email, nome, cpf, endereco) values(?,?,?,?,?,?,?,?,?)";
                String sqlAluno = "insert into aluno(matricula,nome_mae,nome_pai,data_matricula,tel_responsavel,usuario_login) values(?,?,?,?,?,?)";

                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

                String encodedPassword = passwordEncoder.encode(us.getSenha());

                us.setSenha(encodedPassword);
                
                jdbc.update(sqlUsuario, us.getLogin(), us.getSenha(), us.getRg(), us.getTelefone(), us.getDataNasc(),
                                us.getEmail(), us.getNome(), us.getCpf(), us.getEndereco());
                return jdbc.update(sqlAluno, aluno.getMatricula(), aluno.getNomeMae(), aluno.getNomePai(),
                                aluno.getDataMatricula(), aluno.getTelResponsavel(), aluno.getUsuario().getLogin());
        }

        public Integer excluirAluno(String id) {
                String sqlAluno = "delete from aluno where usuario_login = ?";
                String sqlUsuario = "delete from usuario where login= ?";
                jdbc.update(sqlAluno, id);
                return jdbc.update(sqlUsuario, id);
        }

        public Integer atualizarAluno(String cpf, Aluno aluno) {
                Usuario us = aluno.getUsuario();
                String sqlAluno = "update aluno set matricula = ?,nome_mae = ?,nome_pai = ?,data_matricula = ?,tel_responsavel = ? where usuario_login = ?";
                String sqlUsuario = "update usuario set rg = ?, telefone = ?, data_nasc = ?, email = ?, nome = ?, cpf = ?, endereco = ? where login = ?";
                jdbc.update(sqlAluno, aluno.getMatricula(), aluno.getNomeMae(), aluno.getNomePai(),
                                aluno.getDataMatricula(), aluno.getTelResponsavel(), aluno.getUsuario().getLogin());
                return jdbc.update(sqlUsuario, us.getRg(), us.getTelefone(), us.getDataNasc(), us.getEmail(),
                                us.getNome(), us.getCpf(), us.getEndereco(), us.getLogin());
        }

        public Aluno buscaPorLogin(String login) {
                return jdbc.queryForObject(
                                "SELECT * FROM aluno, usuario where usuario.login = aluno.usuario_login and usuario.login = ? ;",
                                (res, linha) -> new Aluno(
                                                new Usuario(res.getString("login"), res.getString("senha"),
                                                                res.getString("rg"),
                                                                res.getString("telefone"), res.getDate("data_nasc"),
                                                                res.getString("email"),
                                                                res.getString("nome"), res.getString("cpf"),
                                                                res.getString("endereco")),
                                                res.getString("matricula"), res.getString("nome_mae"),
                                                res.getString("nome_pai"),
                                                res.getDate("data_matricula"), res.getString("tel_responsavel")),
                                login);
        }

        public Aluno buscaPorCpf(String cpf) {
                Aluno aluno = null;
                try {
                        aluno = jdbc.queryForObject(
                                        "select * from aluno, usuario where usuario.login = aluno.usuario_login and usuario.cpf = ? ;",
                                        (res, rowNum) -> {
                                                return new Aluno(
                                                                new Usuario(res.getString("login"),
                                                                                res.getString("senha"),
                                                                                res.getString("rg"),
                                                                                res.getString("telefone"),
                                                                                res.getDate("data_nasc"),
                                                                                res.getString("email"),
                                                                                res.getString("nome"),
                                                                                res.getString("cpf"),
                                                                                res.getString("endereco")),
                                                                res.getString("matricula"), res.getString("nome_mae"),
                                                                res.getString("nome_pai"),
                                                                res.getDate("data_matricula"),
                                                                res.getString("tel_responsavel"));
                                        }, new Object[] { cpf });
                } catch (Exception e) {
                        System.out.println(e.getLocalizedMessage());
                }
                return aluno;
        }
}
