package com.joseangel.reservas.reserva.dto;

import com.joseangel.reservas.reserva.domain.EstadoReserva;

import java.time.LocalDate;

public record ReservaResponse(
        Long id,
        String nombreCliente,
        LocalDate fecha,
        int numeroPersonas,
        EstadoReserva estado
) {
}
