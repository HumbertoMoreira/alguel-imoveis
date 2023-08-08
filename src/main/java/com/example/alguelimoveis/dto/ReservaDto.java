package com.example.alguelimoveis.dto;

import com.example.alguelimoveis.domain.Reserva;
import com.example.alguelimoveis.domain.Status;


import java.io.Serializable;
import java.util.Date;

public class ReservaDto implements Serializable {

    private Integer id;
    private String nomeHospede;

    private Date dataInicio;

    private Date dataFim;
    private Integer quantidadePessoas;
    private Status status;

    public ReservaDto(Reserva obj){
        id = obj.getId();
        nomeHospede = obj.getNomeHospede();
        dataInicio = obj.getDataInicio();
        dataFim = obj.getDataFim();
        quantidadePessoas = obj.getQuantidadePessoas();
        status = obj.getStatus();
    }


    public ReservaDto(Integer id, String nomeHospede, Date dataInicio, Date dataFim, Integer quantidadePessoas, Status status) {

    }

    public ReservaDto() {
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
