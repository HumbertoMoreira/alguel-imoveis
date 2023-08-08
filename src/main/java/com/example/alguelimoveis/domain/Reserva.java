package com.example.alguelimoveis.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Reserva implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nomeHospede;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataInicio;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataFim;
    private Integer quantidadePessoas;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Reserva() {
    }

    public Reserva(Integer id, String nomeHospede, Date dataInicio, Date dataFim, Integer quantidadePessoas, Status status) {
        this.id = id;
        this.nomeHospede = nomeHospede;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.quantidadePessoas = quantidadePessoas;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeHospede() {
        return nomeHospede;
    }

    public void setNomeHospede(String nomeHospede) {
        this.nomeHospede = nomeHospede;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Integer getQuantidadePessoas() {
        return quantidadePessoas;
    }

    public void setQuantidadePessoas(Integer quantidadePessoas) {
        this.quantidadePessoas = quantidadePessoas;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
