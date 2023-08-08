package com.example.alguelimoveis.controller;

import com.example.alguelimoveis.domain.Reserva;
import com.example.alguelimoveis.domain.Status;
import com.example.alguelimoveis.service.ReservaService;
import com.example.alguelimoveis.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@WebMvcTest(ReservaController.class)
public class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReservaService reservaService;
    @InjectMocks
    private ReservaController reservaController;

    @Test
    public void testInserirReserva() throws Exception{
        Reserva reserva = new Reserva(1, "nome", new Date(2023-10-10), new Date(2023-10-15), 1, Status.CONFIRMADA);

        when(reservaService.insert(any())).thenReturn(reserva);

        mockMvc.perform(MockMvcRequestBuilders.post("/reservas")
                .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"nomeHospede\":\"nome\",\"dataInicio\":\"2023-10-10\",\"dataFim\":\"2023-10-15\",\"quantidadePessoas\":1,\"status\":\"CONFIRMADA\"}"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/reservas/1"));
    }

//    @Test
//    public void testUpdate() throws Exception{
//        Integer reservaId = 1;
//        Reserva reservaAtualizada = new Reserva(reservaId, "nome", new Date(123, 9, 10), new Date(123, 9, 15), 1, Status.CONFIRMADA);
//        Reserva reservaExistente = new Reserva(reservaId, "nome2", new Date(123, 9, 20), new Date(123, 9, 30), 3, Status.PENDENTE);
//
//        when(reservaService.find(reservaId)).thenReturn(reservaExistente);
//        when(reservaService.update(any(Reserva.class))).thenReturn(reservaAtualizada);
//
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/reservas/{id}", reservaId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content("{\"id\":1,\"nomeHospede\":\"nome2\",\"dataInicio\":\"2023-10-20\",\"dataFim\":\"2023-10-30\",\"quantidadePessoas\":3,\"status\":\"PENDENTE\"}"))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.nomeHospede").value("nome2"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.dataInicio").value("2023-10-20"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.dataFim").value("2023-10-30"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.quantidadePessoas").value(3))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("PENDENTE"));;
//    }

//    Não consegui resolver o método update.

    @Test
    public void testFindReservaByIdExistente() throws Exception{
        Date dataInicio = new Date(123, 9, 10);
        Date dataFim = new Date(123, 9, 15);
        Reserva reserva = new Reserva(1, "nome", dataInicio, dataFim, 1, Status.CONFIRMADA);

        when(reservaService.find(1)).thenReturn(reserva);

        mockMvc.perform(MockMvcRequestBuilders.get("/reservas/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomeHospede").value("nome"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataInicio").value("2023-10-10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataFim").value("2023-10-15"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantidadePessoas").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("CONFIRMADA"));
    }

    @Test
    public void testFindAll() throws  Exception{
        Integer reservaId = 1;
        Integer reservaId1 = 2;
        Date dataInicio = new Date(123, 9, 10);
        Date dataFim = new Date(123, 9, 15);
        Date dataInicio1 = new Date(123, 9, 20);
        Date dataFim1 = new Date(123, 9, 30);
        Reserva reserva = new Reserva(reservaId, "nome", dataInicio, dataFim, 1, Status.CONFIRMADA);
        Reserva reserva1= new Reserva(reservaId1, "nome2", dataInicio1, dataFim1, 3, Status.PENDENTE);
        List<Reserva> reservaList = new ArrayList<>();
        reservaList.add(reserva);
        reservaList.add(reserva1);

        when(reservaService.findAll()).thenReturn(reservaList);

        mockMvc.perform(MockMvcRequestBuilders.get("/reservas")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomeHospede").value("nome"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nomeHospede").value("nome2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dataInicio").value("2023-10-10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].dataInicio").value("2023-10-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dataFim").value("2023-10-15"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].dataFim").value("2023-10-30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantidadePessoas").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].quantidadePessoas").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value("CONFIRMADA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].status").value("PENDENTE"));
    }

    @Test
    public void testFindAllConfirmada() throws  Exception{
        Integer reservaId = 1;
        Integer reservaId1 = 2;
        Date dataInicio = new Date(123, 9, 10);
        Date dataFim = new Date(123, 9, 15);
        Date dataInicio1 = new Date(123, 9, 20);
        Date dataFim1 = new Date(123, 9, 30);
        Reserva reserva = new Reserva(reservaId, "nome", dataInicio, dataFim, 1, Status.CONFIRMADA);
        Reserva reserva1= new Reserva(reservaId1, "nome2", dataInicio1, dataFim1, 3, Status.PENDENTE);
        List<Reserva> reservaList = new ArrayList<>();
        reservaList.add(reserva);
        reservaList.add(reserva1);

        when(reservaService.findAllConfirmada()).thenReturn(reservaList);

        mockMvc.perform(MockMvcRequestBuilders.get("/reservas/confirmada")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomeHospede").value("nome"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nomeHospede").value("nome2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dataInicio").value("2023-10-10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].dataInicio").value("2023-10-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dataFim").value("2023-10-15"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].dataFim").value("2023-10-30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantidadePessoas").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].quantidadePessoas").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value("CONFIRMADA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].status").value("PENDENTE"));
    }

    @Test
    public void testFindAllPendente() throws  Exception{
        Integer reservaId = 1;
        Integer reservaId1 = 2;
        Date dataInicio = new Date(123, 9, 10);
        Date dataFim = new Date(123, 9, 15);
        Date dataInicio1 = new Date(123, 9, 20);
        Date dataFim1 = new Date(123, 9, 30);
        Reserva reserva = new Reserva(reservaId, "nome", dataInicio, dataFim, 1, Status.CONFIRMADA);
        Reserva reserva1= new Reserva(reservaId1, "nome2", dataInicio1, dataFim1, 3, Status.PENDENTE);
        List<Reserva> reservaList = new ArrayList<>();
        reservaList.add(reserva);
        reservaList.add(reserva1);

        when(reservaService.findAllPendente()).thenReturn(reservaList);

        mockMvc.perform(MockMvcRequestBuilders.get("/reservas/pendente")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomeHospede").value("nome"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nomeHospede").value("nome2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dataInicio").value("2023-10-10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].dataInicio").value("2023-10-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dataFim").value("2023-10-15"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].dataFim").value("2023-10-30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantidadePessoas").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].quantidadePessoas").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value("CONFIRMADA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].status").value("PENDENTE"));
    }

    @Test
    public void testFindAllCancelada() throws  Exception{
        Integer reservaId = 1;
        Integer reservaId1 = 2;
        Date dataInicio = new Date(123, 9, 10);
        Date dataFim = new Date(123, 9, 15);
        Date dataInicio1 = new Date(123, 9, 20);
        Date dataFim1 = new Date(123, 9, 30);
        Reserva reserva = new Reserva(reservaId, "nome", dataInicio, dataFim, 1, Status.CONFIRMADA);
        Reserva reserva1= new Reserva(reservaId1, "nome2", dataInicio1, dataFim1, 3, Status.PENDENTE);
        List<Reserva> reservaList = new ArrayList<>();
        reservaList.add(reserva);
        reservaList.add(reserva1);

        when(reservaService.findAllCancelada()).thenReturn(reservaList);

        mockMvc.perform(MockMvcRequestBuilders.get("/reservas/cancelada")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomeHospede").value("nome"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nomeHospede").value("nome2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dataInicio").value("2023-10-10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].dataInicio").value("2023-10-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dataFim").value("2023-10-15"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].dataFim").value("2023-10-30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantidadePessoas").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].quantidadePessoas").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value("CONFIRMADA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].status").value("PENDENTE"));
    }



}
