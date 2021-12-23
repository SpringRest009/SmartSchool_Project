package br.edu.iftm.SmartSchool.model;

import javax.validation.Valid;

public class Professor {
    @Valid
    private String cod_professor;
    private Usuario usuario;

    public Professor() {
    }

    public Professor(Usuario usuario, String cod_professor) {
        this.usuario = usuario;
        this.cod_professor = cod_professor;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCod_professor() {
        return this.cod_professor;
    }

    public void setCod_professor(String cod_professor) {
        this.cod_professor = cod_professor;
    }
}
