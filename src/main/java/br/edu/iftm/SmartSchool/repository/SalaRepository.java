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
                            res.getString("cod_sala"),
                            res.getString("turma"),
                            res.getString("local_sala"),
                            res.getInt("qtd_alunos")
                        ));
    }

    public Integer gravarSala(Sala sala) {
        String consulta = "INSERT INTO SALA (cod_sala, turma, local_sala, qtd_alunos) VALUES (?, ?, ?, ?)";
        return jdbc.update(consulta, sala.getCod_sala(), sala.getTurma(), sala.getLocal_sala(), sala.getQtd_alunos());
    }

    public Integer excluirSala(String cod_sala) {
        String consulta = "delete from sala where cod_sala = ?";
        return jdbc.update(consulta, cod_sala);
    }

    public Integer atualizarSala(String cod_sala, Sala sala) {
        String consulta = "UPDATE SALA SET cod_sala = ?, turma = ?, local_sala = ?, qtd_alunos = ?";
        return jdbc.update(consulta, sala.getCod_sala(), sala.getTurma(), sala.getLocal_sala(), sala.getQtd_alunos());
    }

    public Sala buscaPorCodSala(String cod_sala) {
        return jdbc.queryForObject(
                "SELECT * FROM SALA WHERE cod_sala = ?",
                (res, rowNum) -> {
                    return new Sala(
                        res.getString("cod_sala"),
                        res.getString("turma"),
                        res.getString("local_sala"),
                        res.getInt("qtd_alunos")
                            );},
                            cod_sala);
    }
}
