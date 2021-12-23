package br.edu.iftm.SmartSchool.model;

import java.sql.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

@Validated
public class Usuario implements java.io.Serializable {
    //@Pattern(regexp = "/\\S+@", message="A palavra de login deve terminar com @")
	@Size(max=10,message = "A palavra de login deve possuir no máximo 10 caracteres")
	private String login;
	private String senha;
	private String rg;
	@NotNull (message = "O telefone não pode ser vazio!")
	private String telefone;
	private Date dataNasc;
	private String email;
	private String nome;
	@Size(min = 11, message = "O CPF deve possuir exatamente 11 digitos, apenas números!")
	private String cpf;
	private String endereco;

	public Usuario() {
	}

	public Usuario(String login, String senha, String rg, String telefone, Date dataNasc, String email, String nome,
			String cpf, String endereco) {
		this.login = login;
		this.senha = senha;
		this.rg = rg;
		this.telefone = telefone;
		this.dataNasc = dataNasc;
		this.email = email;
		this.nome = nome;
		this.cpf = cpf;
		this.endereco = endereco;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getRg() {
		return this.rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDataNasc() {
		return this.dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
}
