package com.example.alguelimoveis.controller;

import com.example.alguelimoveis.domain.Reserva;
import com.example.alguelimoveis.dto.ReservaDto;
import com.example.alguelimoveis.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;



    @PostMapping
    public ResponseEntity<Void> insert(@Validated @RequestBody Reserva reserva){

        Reserva obj = reservaService.insert(reserva);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> find(@PathVariable Integer id){
        Reserva reserva = reservaService.find(id);
        return ResponseEntity.ok(reserva);
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> findAll(){
        List<Reserva> list = reservaService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody ReservaDto dto, @PathVariable Integer id){
        Reserva obj = reservaService.fromDto(dto);
        obj.setId(id);
        reservaService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@RequestBody ReservaDto dto, @PathVariable Integer id){
        Reserva obj = reservaService.fromDto(dto);
        obj.setId(id);
        reservaService.cancelar(obj);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/confirmada")
    public ResponseEntity<List<Reserva>> findAllConfirmada(){
        List<Reserva> list = reservaService.findAllConfirmada();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/pendente")
    public ResponseEntity<List<Reserva>> findAllPendente(){
        List<Reserva> list = reservaService.findAllPendente();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/cancelada")
    public ResponseEntity<List<Reserva>> findAllCancelada(){
        List<Reserva> list = reservaService.findAllCancelada();
        return ResponseEntity.ok().body(list);
    }
}
