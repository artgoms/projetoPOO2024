package com.projeto.projetoFabinho.Models;

public class CarModel {
    private int id;  // ID do carro
    private int codigo;  // Agora refere-se ao cliente, substituindo "idCliente"
    private String situacao;  // Status do carro
    private String marca;
    private String modelo;
    private String anoFabricacao;
    private String placa;
    private String observacoes;

    // 🔹 Construtor Completo
    public CarModel(int id, int codigo, String situacao, String marca, String modelo, String anoFabricacao, String placa, String observacoes) {
        this.id = id;
        this.codigo = codigo;
        this.situacao = situacao;
        this.marca = marca;
        this.modelo = modelo;
        this.anoFabricacao = anoFabricacao;
        this.placa = placa;
        this.observacoes = observacoes;
    }

    // 🔹 Construtor Vazio
    public CarModel() { }

    // 🔹 Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getAnoFabricacao() { return anoFabricacao; }
    public void setAnoFabricacao(String anoFabricacao) { this.anoFabricacao = anoFabricacao; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}
