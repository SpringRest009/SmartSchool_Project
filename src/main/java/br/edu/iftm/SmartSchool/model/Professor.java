package br.edu.iftm.SmartSchool.model;

public class Professor {
    private Integer cod_professor;
    private Usuario usuario;

    public Professor() {
    }

    public Professor(Usuario usuario, Integer cod_professor) {
        this.usuario = usuario;
        this.cod_professor = cod_professor;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getCod_professor() {
        return this.cod_professor;
    }

    public void setCod_professor(Integer cod_professor) {
        this.cod_professor = cod_professor;
    }
}
