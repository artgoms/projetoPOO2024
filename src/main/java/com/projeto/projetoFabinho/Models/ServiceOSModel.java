package com.projeto.projetoFabinho.Models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ServiceOSModel {
    private int id;
    private int clienteId;
    private int veiculoId;
    private String tipoOS;
    private String descricao;
    private BigDecimal valor;
    private String situacao;
    private LocalDate dataEntrada;
    private LocalDate dataPrevisao;
    private LocalDate dataConclusao;

    // Construtor completo
    public ServiceOSModel(int id, int clienteId, int veiculoId, String tipoOS, String descricao, BigDecimal valor,
                          String situacao, LocalDate dataEntrada, LocalDate dataPrevisao, LocalDate dataConclusao) {
        this.id = id;
        this.clienteId = clienteId;
        this.veiculoId = veiculoId;
        this.tipoOS = tipoOS;
        this.descricao = descricao;
        this.valor = valor;
        this.situacao = situacao;
        this.dataEntrada = dataEntrada;
        this.dataPrevisao = dataPrevisao;
        this.dataConclusao = dataConclusao;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }

    public int getVeiculoId() { return veiculoId; }
    public void setVeiculoId(int veiculoId) { this.veiculoId = veiculoId; }

    public String getTipoOS() { return tipoOS; }
    public void setTipoOS(String tipoOS) { this.tipoOS = tipoOS; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }

    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }

    public LocalDate getDataPrevisao() { return dataPrevisao; }
    public void setDataPrevisao(LocalDate dataPrevisao) { this.dataPrevisao = dataPrevisao; }

    public LocalDate getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(LocalDate dataConclusao) { this.dataConclusao = dataConclusao; }
}
