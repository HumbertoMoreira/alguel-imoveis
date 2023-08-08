package com.example.alguelimoveis.service;

import com.example.alguelimoveis.domain.Reserva;
import com.example.alguelimoveis.domain.Status;
import com.example.alguelimoveis.dto.ReservaDto;
import com.example.alguelimoveis.repository.ReservaRepository;
import com.example.alguelimoveis.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;


    public Reserva insert(Reserva obj){
        obj.setId(null);
        obj.setStatus(Status.CONFIRMADA);
        obj = reservaRepository.save(obj);
        return obj;
    }

    public Reserva find(Integer id){
        Optional<Reserva> obj = reservaRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + " " + Reserva.class.getName()));

    }

    public List<Reserva> findAll(){
        List<Reserva> list = reservaRepository.findAll();
        return list;
    }

    public Reserva update(Reserva obj){
        Reserva reservaAtualizada = find(obj.getId());
        updateData(reservaAtualizada, obj);
        return reservaRepository.save(reservaAtualizada);

    }

    private void updateData(Reserva reservaNova, Reserva resernaAntiga){
        reservaNova.setNomeHospede(resernaAntiga.getNomeHospede());
        reservaNova.setDataInicio(resernaAntiga.getDataInicio());
        reservaNova.setDataFim(resernaAntiga.getDataFim());
        reservaNova.setQuantidadePessoas(resernaAntiga.getQuantidadePessoas());
        reservaNova.setStatus(resernaAntiga.getStatus());
    }

    public Reserva fromDto(ReservaDto objDto){
        return new Reserva(objDto.getId(), objDto.getNomeHospede(), objDto.getDataInicio(),
                objDto.getDataFim(), objDto.getQuantidadePessoas(), objDto.getStatus());
    }

    public Reserva cancelar(Reserva obj){
        Reserva reservaCancelada = find(obj.getId());
        reservaCancelada.setStatus(Status.CANCELADA);
        return reservaRepository.save(reservaCancelada);
    }

    public List<Reserva> findAllConfirmada(){
        List<Reserva> listConfirmada = reservaRepository.findAllConfirmada();
        return listConfirmada;
    }

    public List<Reserva> findAllPendente(){
        List<Reserva> listPendente = reservaRepository.findAllPendente();
        return listPendente;
    }

    public List<Reserva> findAllCancelada(){
        List<Reserva> listCancelada = reservaRepository.findAllCancelada();
        return listCancelada;
    }
}
