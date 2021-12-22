package br.edu.iftm.SmartSchool.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.edu.iftm.SmartSchool.model.Sala;

@Repository
public class SalaRepository {

    @Autowired
    JdbcTemplate jdbc;

    public SalaRepository() {

    }

    public List<Sala> buscaTodasSalas() {
        String consulta = "SELECT * FROM sala";
        return jdbc.query(consulta,
                        (res, linha) -> new Sala(
                            res.getInt("cod_sala"),
                            res.getString("turma"),
                            res.getString("local_sala"),
                            res.getInt("qtd_alunos")
                        ));
    }

    public Integer gravarSala(Sala sala) {
        String consulta = "INSERT INTO SALA (cod_sala, turma, local_sala, qtd_alunos) VALUES (?, ?, ?, ?)";
        return jdbc.update(consulta, sala.getCod_sala(), sala.getTurma(), sala.getLocal_sala(), sala.getQtd_alunos());
    }

    public Integer excluirSala(Integer id) {
        String consulta = "delete from sala where cod_sala = ?";
        return jdbc.update(consulta, id);
    }

    public Integer atualizarSala(Sala sala) {
        String consulta = "UPDATE SALA SET cod_sala = ?, turma = ?, local_sala = ?, qtd_alunos = ?";
        return jdbc.update(consulta, sala.getCod_sala(), sala.getTurma(), sala.getLocal_sala(), sala.getQtd_alunos());
    }

    public Sala buscaPorCodSala(Integer cod_sala) {
        Sala sala = null;
            try {
                    sala = jdbc.queryForObject(
                                    "select * from sala, cod_sala where cod_sala = ?",
                                    (res, rowNum) -> {
                                            return new Sala(
                                                res.getInt("cod_sala"),
                                                res.getString("turma"),
                                                res.getString("local_sala"),
                                                res.getInt("qtd_alunos"));
                                    }, new Object[] { cod_sala });
            } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
            }
            return sala;
    }
}
