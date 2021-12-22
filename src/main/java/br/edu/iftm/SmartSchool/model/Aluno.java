package br.edu.iftm.SmartSchool.model;

import java.sql.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Aluno implements java.io.Serializable {
    @Valid
    private Usuario usuario;
    private String matricula;
    private String nomeMae;
    @Size(min=3,message="O nome pai com 3 letras")
    private String nomePai;
    private Date dataMatricula;

    @NotNull(message = "O telefone não pode ser vazio!")
    private String telResponsavel;

    public Aluno() {
    }

    public Aluno(Usuario usuario, String matricula, String nomeMae, String nomePai, Date dataMatricula,
            String telResponsavel) {
        this.usuario = usuario;
        this.matricula = matricula;
        this.nomeMae = nomeMae;
        this.nomePai = nomePai;
        this.dataMatricula = dataMatricula;
        this.telResponsavel = telResponsavel;
    }

    /*
     * public Aluno(Usuario usuario, String login, Integer cpf) {
     * this.login = usuario.getLogin();
     * this.cpf = usuario.getCpf();
     * }
     */

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMatricula() {
        return this.matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNomeMae() {
        return this.nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNomePai() {
        return this.nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public Date getDataMatricula() {
        // SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        // return formatter.format(dataMatricula);
        return dataMatricula;
    }

    public void setDataMatricula(Date dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public String getTelResponsavel() {
        return this.telResponsavel;
    }

    public void setTelResponsavel(String telResponsavel) {
        this.telResponsavel = telResponsavel;
    }

}
