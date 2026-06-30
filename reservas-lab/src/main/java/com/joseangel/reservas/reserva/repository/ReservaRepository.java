package com.joseangel.reservas.reserva.repository;

import com.joseangel.reservas.reserva.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    boolean existsByNombreClienteAndFechaAndNumeroPersonas(String nombreCliente, LocalDate fecha, int numeroPersonas);

    List<Reserva> findAllByOrderByIdAsc();
}
