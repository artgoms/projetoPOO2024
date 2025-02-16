package com.projeto.projetoFabinho.Models;

public class OSModel {
    private String numeroOS;
    private String cliente;
    private String veiculoID;
    private String modelo;
    private String situacao;

    public OSModel(String numeroOS, String cliente, String veiculoID, String modelo, String situacao) {
        this.numeroOS = numeroOS;
        this.cliente = cliente;
        this.veiculoID = veiculoID;
        this.modelo = modelo;
        this.situacao = situacao;
    }


    public String getNumeroOS() { return numeroOS; }
    public String getCliente() { return cliente; }
    public String getVeiculoID() { return veiculoID; }
    public String getModelo() { return modelo; }
    public String getSituacao() { return situacao; }


    public void setNumeroOS(String numeroOS) { this.numeroOS = numeroOS; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public void setVeiculoID(String veiculoID) { this.veiculoID = veiculoID; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setSituacao(String situacao) { this.situacao = situacao; }
}
