package com.projeto.projetoFabinho.Models;

public class CarModel {
	private int id; // ID do carro
	private int clienteId; // Agora refere-se ao cliente, substituindo "idCliente"
	private String situacao; // Status do carro
	private String marca;
	private String modelo;
	private String anoFabricacao;
	private String placa;
	private String observacoes;
	private int ultimaOS;

	// Construtor Completo
	public CarModel(int id, int clienteId, String situacao, String marca, String modelo, String anoFabricacao,
			String placa, String observacoes, int codigoCliente) {
		this.id = id;
		this.clienteId = clienteId;
		this.situacao = situacao;
		this.marca = marca;
		this.modelo = modelo;
		this.anoFabricacao = anoFabricacao;
		this.placa = placa;
		this.observacoes = observacoes;
	}

	// Construtor com 8 parâmetros
	public CarModel(int id, int clienteId, String situacao, String marca, String modelo, String placa, String anoFabricacao,
			String observacoes) {
		this.id = id;
		this.clienteId = clienteId;
		this.situacao = situacao;
		this.marca = marca;
		this.modelo = modelo;
		this.anoFabricacao = anoFabricacao;
		this.placa = placa;
		this.observacoes = observacoes;
	}

	// Construtor Vazio
	public CarModel() {
	}

	// getters e setters
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClienteId() {
		return clienteId;
	}

	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getAnoFabricacao() {
		return anoFabricacao;
	}

	public void setAnoFabricacao(String anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public int getUltimaOS() {
	    return ultimaOS;
	}

	public void setUltimaOS(int ultimaOS) {
	    this.ultimaOS = ultimaOS;
	}
}
