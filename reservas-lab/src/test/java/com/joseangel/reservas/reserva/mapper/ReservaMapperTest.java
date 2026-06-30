package com.joseangel.reservas.reserva.mapper;

import com.joseangel.reservas.reserva.domain.EstadoReserva;
import com.joseangel.reservas.reserva.domain.Reserva;
import com.joseangel.reservas.reserva.dto.CrearReservaRequest;
import com.joseangel.reservas.reserva.dto.ReservaResponse;
import org.junit.jupiter.api.Test;

import java.lang.reflect.RecordComponent;
import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ReservaMapperTest {

    @Test
    void convierteRequestValidoAReserva() {
        LocalDate fecha = LocalDate.now().plusDays(1);
        CrearReservaRequest request = new CrearReservaRequest("  Ana Garcia  ", fecha, 4);

        Reserva reserva = ReservaMapper.toReserva(request);

        assertThat(reserva.getNombreCliente()).isEqualTo("Ana Garcia");
        assertThat(reserva.getFecha()).isEqualTo(fecha);
        assertThat(reserva.getNumeroPersonas()).isEqualTo(4);
        assertThat(reserva.getEstado()).isEqualTo(EstadoReserva.PENDIENTE);
    }

    @Test
    void convierteReservaAReservaResponse() {
        LocalDate fecha = LocalDate.now().plusDays(2);
        Reserva reserva = new Reserva("Cliente Prueba", fecha, 2);

        ReservaResponse response = ReservaMapper.toResponse(reserva);

        assertThat(response.id()).isNull();
        assertThat(response.nombreCliente()).isEqualTo("Cliente Prueba");
        assertThat(response.fecha()).isEqualTo(fecha);
        assertThat(response.numeroPersonas()).isEqualTo(2);
        assertThat(response.estado()).isEqualTo(EstadoReserva.PENDIENTE);
    }

    @Test
    void reservaResponseNoExponeVersion() {
        assertThat(Arrays.stream(ReservaResponse.class.getRecordComponents())
                .map(RecordComponent::getName))
                .doesNotContain("version");
    }
}
