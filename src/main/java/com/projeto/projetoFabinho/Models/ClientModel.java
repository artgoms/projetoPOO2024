package com.projeto.projetoFabinho.Models;

public class ClientModel {

	public enum TipoEndereco {
	    COMERCIAL, RESIDENCIAL, COBRANCA, ENTREGA, RURAL
	}

	    private int codigo;
	    private String tipoInscricao; // CPF, CNPJ, RG
	    private String inscricaoNumero; // Número da inscrição
	    private String nome;
	    private String situacao; // Ativo, Inativo, etc.
	    private TipoEndereco tipoEndereco; // Enum: COMERCIAL, RESIDENCIAL, etc.
	    private String logradouro;
	    private String numero;
	    private String bairro;
	    private String municipio;
	    private String uf;
	    private String cep;
	    private String complemento;
	    private String responsavel;
	    private String telefone1;
	    private String telefone2;

	    // Construtor vazio
	    public ClientModel() {}

	    // Construtor com todos os campos
	    public ClientModel(int codigo, String tipoInscricao, String inscricaoNumero, String nome, String situacao, TipoEndereco tipoEndereco, String logradouro, String numero, String bairro, String municipio, String uf, String cep, String complemento, String responsavel, String telefone1, String telefone2) {
	        this.codigo = codigo;
	        this.tipoInscricao = tipoInscricao;
	        this.inscricaoNumero = inscricaoNumero;
	        this.nome = nome;
	        this.situacao = situacao;
	        this.tipoEndereco = tipoEndereco;
	        this.logradouro = logradouro;
	        this.numero = numero;
	        this.bairro = bairro;
	        this.municipio = municipio;
	        this.uf = uf;
	        this.cep = cep;
	        this.complemento = complemento;
	        this.responsavel = responsavel;
	        this.telefone1 = telefone1;
	        this.telefone2 = telefone2;
	    }

	    // Getters e Setters
	    public int getCodigo() {
	        return codigo;
	    }

	    public void setCodigo(int codigo) {
	        this.codigo = codigo;
	    }

	    public String getTipoInscricao() {
	        return tipoInscricao;
	    }

	    public void setTipoInscricao(String tipoInscricao) {
	        this.tipoInscricao = tipoInscricao;
	    }

	    public String getInscricaoNumero() {
	        return inscricaoNumero;
	    }

	    public void setInscricaoNumero(String inscricaoNumero) {
	        this.inscricaoNumero = inscricaoNumero;
	    }

	    public String getNome() {
	        return nome;
	    }

	    public void setNome(String nome) {
	        this.nome = nome;
	    }

	    public String getSituacao() {
	        return situacao;
	    }

	    public void setSituacao(String situacao) {
	        this.situacao = situacao;
	    }

	    public TipoEndereco getTipoEndereco() {
	        return tipoEndereco;
	    }

	    public void setTipoEndereco(TipoEndereco tipoEndereco) {
	        this.tipoEndereco = tipoEndereco;
	    }

	    public String getLogradouro() {
	        return logradouro;
	    }

	    public void setLogradouro(String logradouro) {
	        this.logradouro = logradouro;
	    }

	    public String getNumero() {
	        return numero;
	    }

	    public void setNumero(String numero) {
	        this.numero = numero;
	    }

	    public String getBairro() {
	        return bairro;
	    }

	    public void setBairro(String bairro) {
	        this.bairro = bairro;
	    }

	    public String getMunicipio() {
	        return municipio;
	    }

	    public void setMunicipio(String municipio) {
	        this.municipio = municipio;
	    }

	    public String getUf() {
	        return uf;
	    }

	    public void setUf(String uf) {
	        this.uf = uf;
	    }

	    public String getCep() {
	        return cep;
	    }

	    public void setCep(String cep) {
	        this.cep = cep;
	    }

	    public String getComplemento() {
	        return complemento;
	    }

	    public void setComplemento(String complemento) {
	        this.complemento = complemento;
	    }

	    public String getResponsavel() {
	        return responsavel;
	    }

	    public void setResponsavel(String responsavel) {
	        this.responsavel = responsavel;
	    }

	    public String getTelefone1() {
	        return telefone1;
	    }

	    public void setTelefone1(String telefone1) {
	        this.telefone1 = telefone1;
	    }

	    public String getTelefone2() {
	        return telefone2;
	    }

	    public void setTelefone2(String telefone2) {
	        this.telefone2 = telefone2;
	    }
	}
	

