package com.projeto.projetoFabinho.Models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CarPartsModel {
    private int id;
    private String nome;
    private String marca;
    private LocalDate dataEntrada;
    private int quantidade;
    private double custo;
    private double valorVenda;
    private double margemLucro;
    private int osId;


	public CarPartsModel(int id, String nome, String marca, int quantidade, double custo, double margemLucro, double valorVenda, LocalDate dataEntrada) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.quantidade = quantidade;
        this.custo = custo;
        this.margemLucro = margemLucro;
        this.valorVenda = valorVenda;
        this.dataEntrada = dataEntrada;
    }
	
	public CarPartsModel(int id, String nome, BigDecimal valorVenda) {
	    this.id = id;
	    this.nome = nome;
	    this.valorVenda = valorVenda.doubleValue(); // Convertendo BigDecimal para double
	}



    public CarPartsModel(int id, String nome, String marca, int quantidade,  double custo, double valorVenda, LocalDate dataEntrada) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.quantidade = quantidade;
        this.custo = custo;
        this.valorVenda = valorVenda;
        this.dataEntrada = dataEntrada;
    }
    
    public CarPartsModel(int id, String nome, String marca,  double valorVenda) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.valorVenda = valorVenda;
    }
    
    public CarPartsModel(int id, String nome, String marca, BigDecimal valorVenda) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.valorVenda = valorVenda != null ? valorVenda.doubleValue() : 0.0; // Conversão segura
    }



	public int getOsId() {
        return osId;
    }

    public void setOsId(int osId) {
        this.osId = osId;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getCusto() { return custo; }
    public void setCusto(double custo) { this.custo = custo; }

    public double getValorVenda() { return valorVenda; }
    public void setValorVenda(double valorVenda) { this.valorVenda = valorVenda; }

    public double getMargemLucro() { return margemLucro; }
	public void setMargemLucro(double margemLucro) { this.margemLucro = margemLucro; }


}
