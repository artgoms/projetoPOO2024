package com.projeto.projetoFabinho.Models;

import java.time.LocalDate;

public class CarPartsModel {
    private int id;
    private String nome;
    private String modelo;
    private LocalDate dataEntrada;
    private int quantidade;
    private double custo;
    private double margem;
    private double valorVenda;

    public CarPartsModel(int id, String nome, String modelo, LocalDate dataEntrada, int quantidade, double custo, double margem, double valorVenda) {
        this.id = id;
        this.nome = nome;
        this.modelo = modelo;
        this.dataEntrada = dataEntrada;
        this.quantidade = quantidade;
        this.custo = custo;
        this.margem = margem;
        this.valorVenda = valorVenda;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getCusto() { return custo; }
    public void setCusto(double custo) { this.custo = custo; }

    public double getMargem() { return margem; }
    public void setMargem(double margem) { this.margem = margem; }

    public double getValorVenda() { return valorVenda; }
    public void setValorVenda(double valorVenda) { this.valorVenda = valorVenda; }
}
