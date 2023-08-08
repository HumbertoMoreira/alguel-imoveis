package com.example.alguelimoveis.service;

import com.example.alguelimoveis.domain.Reserva;
import com.example.alguelimoveis.domain.Status;
import com.example.alguelimoveis.repository.ReservaRepository;
import com.example.alguelimoveis.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    public void testInsertReserva(){
        Reserva reserva = new Reserva(1, "nome", new Date(2023-10-10), new Date(2023-10-15), 1, Status.CONFIRMADA);

        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);
        Reserva reservaInserida = reservaService.insert(reserva);

        assertNotNull(reservaInserida);
        assertEquals(reserva.getNomeHospede(), reservaInserida.getNomeHospede());
        assertEquals(reserva.getDataInicio(), reservaInserida.getDataInicio());
        assertEquals(reserva.getDataFim(), reservaInserida.getDataFim());
        assertEquals(reserva.getQuantidadePessoas(), reservaInserida.getQuantidadePessoas());
        assertEquals(reserva.getStatus(), reservaInserida.getStatus());
        assertNull(reservaInserida.getId());


        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    public void testFindReservaByIdExistente(){
        Integer reservaId = 1;
        Reserva reserva = new Reserva(reservaId, "nome", new Date(2023-10-10), new Date(2023-10-15), 1, Status.CONFIRMADA);

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reserva));

        Reserva reservaEncontrada = reservaService.find(reservaId);

        assertNotNull(reservaEncontrada);
        assertEquals(reserva.getId(), reservaEncontrada.getId());
        assertEquals(reserva.getNomeHospede(), reservaEncontrada.getNomeHospede());
        assertEquals(reserva.getDataInicio(), reservaEncontrada.getDataInicio());
        assertEquals(reserva.getDataFim(), reservaEncontrada.getDataFim());
        assertEquals(reserva.getQuantidadePessoas(), reservaEncontrada.getQuantidadePessoas());
        assertEquals(reserva.getStatus(), reservaEncontrada.getStatus());

        verify(reservaRepository, times(1)).findById(reservaId);

    }

    @Test
    public void testFindReservaByIdNaoExistente(){
        Integer reservaId = 99;

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> reservaService.find(reservaId));

        verify(reservaRepository, times(1)).findById(reservaId);
    }

    @Test
    public void testCancelarReserva(){
        Integer reservaId = 1;
        Reserva reserva = new Reserva(reservaId, "nome", new Date(2023-10-10), new Date(2023-10-15), 1, Status.CONFIRMADA);
        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reserva));

        Reserva reservaCancelada = reservaService.find(reservaId);

        reservaService.cancelar(reserva);

        verify(reservaRepository).save(reserva);
        assertEquals(Status.CANCELADA, reserva.getStatus());
    }

    @Test
    public void testUpdateReserva(){
        Integer reservaId = 1;
        Reserva reservaAtualizada = new Reserva(reservaId, "nome", new Date(2023-10-10), new Date(2023-10-15), 1, Status.CONFIRMADA);
        Reserva reservaExistente = new Reserva(reservaId, "nome2", new Date(2023-10-20), new Date(2023-10-30), 3, Status.PENDENTE);

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reservaExistente));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaAtualizada);

        Reserva resultado = reservaService.update(reservaAtualizada);

        assertNotNull(resultado);
        assertEquals(reservaAtualizada.getId(), resultado.getId());
        assertEquals(reservaAtualizada.getNomeHospede(), resultado.getNomeHospede());
        assertEquals(reservaAtualizada.getDataInicio(), resultado.getDataInicio());
        assertEquals(reservaAtualizada.getDataFim(), resultado.getDataFim());
        assertEquals(reservaAtualizada.getQuantidadePessoas(), resultado.getQuantidadePessoas());
        assertEquals(reservaAtualizada.getStatus(), resultado.getStatus());

        verify(reservaRepository, times(1)).findById(reservaId);
        verify(reservaRepository, times(1)).save(any(Reserva.class));

    }

    @Test
    public void testFindAll(){
        Integer reservaId = 1;
        Integer reservaId1 = 2;
        Reserva reserva = new Reserva(reservaId, "nome", new Date(2023-10-10), new Date(2023-10-15), 1, Status.CONFIRMADA);
        Reserva reserva1= new Reserva(reservaId1, "nome2", new Date(2023-10-20), new Date(2023-10-30), 3, Status.PENDENTE);
        List<Reserva> reservaList = new ArrayList<>();
        reservaList.add(reserva);
        reservaList.add(reserva1);
        when(reservaRepository.findAll()).thenReturn(reservaList);

        List<Reserva> reservaEncontrada = reservaService.findAll();

        assertNotNull(reservaEncontrada);

        assertEquals(2, reservaEncontrada.size());
        assertEquals(reserva.getId(), reservaEncontrada.get(0).getId());
        assertEquals(reserva.getNomeHospede(), reservaEncontrada.get(0).getNomeHospede());
        assertEquals(reserva.getDataInicio(), reservaEncontrada.get(0).getDataInicio());
        assertEquals(reserva.getDataFim(), reservaEncontrada.get(0).getDataFim());
        assertEquals(reserva.getQuantidadePessoas(), reservaEncontrada.get(0).getQuantidadePessoas());
        assertEquals(reserva.getStatus(), reservaEncontrada.get(0).getStatus());

        assertEquals(reserva1.getId(), reservaEncontrada.get(1).getId());
        assertEquals(reserva1.getNomeHospede(), reservaEncontrada.get(1).getNomeHospede());
        assertEquals(reserva1.getDataInicio(), reservaEncontrada.get(1).getDataInicio());
        assertEquals(reserva1.getDataFim(), reservaEncontrada.get(1).getDataFim());
        assertEquals(reserva1.getQuantidadePessoas(), reservaEncontrada.get(1).getQuantidadePessoas());
        assertEquals(reserva1.getStatus(), reservaEncontrada.get(1).getStatus());

        verify(reservaRepository, times(1)).findAll();

    }

    @Test
    public void testFindAllConfirmada(){
        Integer reservaId = 1;
        Integer reservaId1 = 2;
        Reserva reserva = new Reserva(reservaId, "nome", new Date(2023-10-10), new Date(2023-10-15), 1, Status.CONFIRMADA);
        Reserva reserva1= new Reserva(reservaId1, "nome2", new Date(2023-10-20), new Date(2023-10-30), 3, Status.CONFIRMADA);
        List<Reserva> reservaList = new ArrayList<>();
        reservaList.add(reserva);
        reservaList.add(reserva1);
        when(reservaRepository.findAllConfirmada()).thenReturn(reservaList);

        List<Reserva> reservaEncontrada = reservaService.findAllConfirmada();

        assertNotNull(reservaEncontrada);

        assertEquals(2, reservaEncontrada.size());
        assertEquals(reserva.getId(), reservaEncontrada.get(0).getId());
        assertEquals(reserva.getNomeHospede(), reservaEncontrada.get(0).getNomeHospede());
        assertEquals(reserva.getDataInicio(), reservaEncontrada.get(0).getDataInicio());
        assertEquals(reserva.getDataFim(), reservaEncontrada.get(0).getDataFim());
        assertEquals(reserva.getQuantidadePessoas(), reservaEncontrada.get(0).getQuantidadePessoas());
        assertEquals(reserva.getStatus(), reservaEncontrada.get(0).getStatus());

        assertEquals(reserva1.getId(), reservaEncontrada.get(1).getId());
        assertEquals(reserva1.getNomeHospede(), reservaEncontrada.get(1).getNomeHospede());
        assertEquals(reserva1.getDataInicio(), reservaEncontrada.get(1).getDataInicio());
        assertEquals(reserva1.getDataFim(), reservaEncontrada.get(1).getDataFim());
        assertEquals(reserva1.getQuantidadePessoas(), reservaEncontrada.get(1).getQuantidadePessoas());
        assertEquals(reserva1.getStatus(), reservaEncontrada.get(1).getStatus());

        verify(reservaRepository, times(1)).findAllConfirmada();
    }

    @Test
    public void testFindAllPendente(){
        Integer reservaId = 1;
        Integer reservaId1 = 2;
        Reserva reserva = new Reserva(reservaId, "nome", new Date(2023-10-10), new Date(2023-10-15), 1, Status.PENDENTE);
        Reserva reserva1= new Reserva(reservaId1, "nome2", new Date(2023-10-20), new Date(2023-10-30), 3, Status.PENDENTE);
        List<Reserva> reservaList = new ArrayList<>();
        reservaList.add(reserva);
        reservaList.add(reserva1);
        when(reservaRepository.findAllPendente()).thenReturn(reservaList);

        List<Reserva> reservaEncontrada = reservaService.findAllPendente();

        assertNotNull(reservaEncontrada);

        assertEquals(2, reservaEncontrada.size());
        assertEquals(reserva.getId(), reservaEncontrada.get(0).getId());
        assertEquals(reserva.getNomeHospede(), reservaEncontrada.get(0).getNomeHospede());
        assertEquals(reserva.getDataInicio(), reservaEncontrada.get(0).getDataInicio());
        assertEquals(reserva.getDataFim(), reservaEncontrada.get(0).getDataFim());
        assertEquals(reserva.getQuantidadePessoas(), reservaEncontrada.get(0).getQuantidadePessoas());
        assertEquals(reserva.getStatus(), reservaEncontrada.get(0).getStatus());

        assertEquals(reserva1.getId(), reservaEncontrada.get(1).getId());
        assertEquals(reserva1.getNomeHospede(), reservaEncontrada.get(1).getNomeHospede());
        assertEquals(reserva1.getDataInicio(), reservaEncontrada.get(1).getDataInicio());
        assertEquals(reserva1.getDataFim(), reservaEncontrada.get(1).getDataFim());
        assertEquals(reserva1.getQuantidadePessoas(), reservaEncontrada.get(1).getQuantidadePessoas());
        assertEquals(reserva1.getStatus(), reservaEncontrada.get(1).getStatus());

        verify(reservaRepository, times(1)).findAllPendente();
    }

    @Test
    public void testFindAllCancelada(){
        Integer reservaId = 1;
        Integer reservaId1 = 2;
        Reserva reserva = new Reserva(reservaId, "nome", new Date(2023-10-10), new Date(2023-10-15), 1, Status.CANCELADA);
        Reserva reserva1= new Reserva(reservaId1, "nome2", new Date(2023-10-20), new Date(2023-10-30), 3, Status.CANCELADA);
        List<Reserva> reservaList = new ArrayList<>();
        reservaList.add(reserva);
        reservaList.add(reserva1);
        when(reservaRepository.findAllCancelada()).thenReturn(reservaList);

        List<Reserva> reservaEncontrada = reservaService.findAllCancelada();

        assertNotNull(reservaEncontrada);

        assertEquals(2, reservaEncontrada.size());
        assertEquals(reserva.getId(), reservaEncontrada.get(0).getId());
        assertEquals(reserva.getNomeHospede(), reservaEncontrada.get(0).getNomeHospede());
        assertEquals(reserva.getDataInicio(), reservaEncontrada.get(0).getDataInicio());
        assertEquals(reserva.getDataFim(), reservaEncontrada.get(0).getDataFim());
        assertEquals(reserva.getQuantidadePessoas(), reservaEncontrada.get(0).getQuantidadePessoas());
        assertEquals(reserva.getStatus(), reservaEncontrada.get(0).getStatus());

        assertEquals(reserva1.getId(), reservaEncontrada.get(1).getId());
        assertEquals(reserva1.getNomeHospede(), reservaEncontrada.get(1).getNomeHospede());
        assertEquals(reserva1.getDataInicio(), reservaEncontrada.get(1).getDataInicio());
        assertEquals(reserva1.getDataFim(), reservaEncontrada.get(1).getDataFim());
        assertEquals(reserva1.getQuantidadePessoas(), reservaEncontrada.get(1).getQuantidadePessoas());
        assertEquals(reserva1.getStatus(), reservaEncontrada.get(1).getStatus());

        verify(reservaRepository, times(1)).findAllCancelada();
    }

}
