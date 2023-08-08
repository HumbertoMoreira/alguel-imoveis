package com.example.alguelimoveis.repository;

import com.example.alguelimoveis.domain.Reserva;
import com.example.alguelimoveis.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    @Query("FROM Reserva as obj WHERE obj.status = 'CONFIRMADA' ")
    List<Reserva> findAllConfirmada();


    @Query("FROM Reserva as obj WHERE obj.status  = 'PENDENTE' ")
    List<Reserva> findAllPendente();

    @Query("FROM Reserva as obj WHERE obj.status  = 'CANCELADA' ")
    List<Reserva> findAllCancelada();

}
