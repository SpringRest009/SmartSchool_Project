package br.edu.iftm.SmartSchool.model;

public class Sala {
    private String cod_sala;
    private String turma;
    private String local_sala;
    private Integer qtd_alunos;

    public Sala() {
    }

    public Sala(String cod_sala, String turma, String local_sala, Integer qtd_alunos) {
        this.cod_sala = cod_sala;
        this.turma = turma;
        this.local_sala = local_sala;
        this.qtd_alunos = qtd_alunos;
    }

    public String getCod_sala() {
        return this.cod_sala;
    }

    public void setCod_sala(String cod_sala) {
        this.cod_sala = cod_sala;
    }

    public String getTurma() {
        return this.turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getLocal_sala() {
        return this.local_sala;
    }

    public void setLocal_sala(String local_sala) {
        this.local_sala = local_sala;
    }

    public Integer getQtd_alunos() {
        return this.qtd_alunos;
    }

    public void setQtd_alunos(Integer qtd_alunos) {
        this.qtd_alunos = qtd_alunos;
    }
}
