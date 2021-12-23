package br.edu.iftm.SmartSchool.model;

import javax.validation.Valid;

public class Secretaria {

    @Valid
    private String cod_adm;
    private Usuario usuario;

    public Secretaria() {
    }

    public Secretaria(String cod_adm, Usuario usuario) {
        this.cod_adm = cod_adm;
        this.usuario = usuario;
    }

    public String getCod_adm() {
        return this.cod_adm;
    }

    public void setCod_adm(String cod_adm) {
        this.cod_adm = cod_adm;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
