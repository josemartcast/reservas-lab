package com.joseangel.reservas.reserva.mapper;

import com.joseangel.reservas.reserva.domain.Reserva;
import com.joseangel.reservas.reserva.dto.CrearReservaRequest;
import com.joseangel.reservas.reserva.dto.ReservaResponse;

public final class ReservaMapper {

    private ReservaMapper() {
    }

    public static Reserva toReserva(CrearReservaRequest request) {
        return new Reserva(
                request.nombreCliente(),
                request.fecha(),
                request.numeroPersonas()
        );
    }

    public static ReservaResponse toResponse(Reserva reserva) {
        return new ReservaResponse(
                reserva.getId(),
                reserva.getNombreCliente(),
                reserva.getFecha(),
                reserva.getNumeroPersonas(),
                reserva.getEstado()
        );
    }
}
