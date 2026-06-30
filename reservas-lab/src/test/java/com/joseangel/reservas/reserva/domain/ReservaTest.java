package com.joseangel.reservas.reserva.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservaTest {

    @Test
    void creacionNormalizaNombreConTrimYEstadoPendiente() {
        Reserva reserva = new Reserva("  Ana García  ", LocalDate.of(2026, 7, 1), 4);

        assertThat(reserva.getNombreCliente()).isEqualTo("Ana García");
        assertThat(reserva.getEstado()).isEqualTo(EstadoReserva.PENDIENTE);
    }

    @Test
    void confirmarPendienteCambiaAConfirmada() {
        Reserva reserva = nuevaReserva();

        reserva.confirmar();

        assertThat(reserva.getEstado()).isEqualTo(EstadoReserva.CONFIRMADA);
    }

    @Test
    void confirmarConfirmadaEsIdempotente() {
        Reserva reserva = nuevaReserva();
        reserva.confirmar();

        reserva.confirmar();

        assertThat(reserva.getEstado()).isEqualTo(EstadoReserva.CONFIRMADA);
    }

    @Test
    void confirmarCanceladaLanzaConflicto() {
        Reserva reserva = nuevaReserva();
        reserva.cancelar();

        assertThatThrownBy(reserva::confirmar)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("No se puede confirmar una reserva cancelada");
    }

    @Test
    void cancelarPendienteCambiaACancelada() {
        Reserva reserva = nuevaReserva();

        reserva.cancelar();

        assertThat(reserva.getEstado()).isEqualTo(EstadoReserva.CANCELADA);
    }

    @Test
    void cancelarConfirmadaCambiaACancelada() {
        Reserva reserva = nuevaReserva();
        reserva.confirmar();

        reserva.cancelar();

        assertThat(reserva.getEstado()).isEqualTo(EstadoReserva.CANCELADA);
    }

    @Test
    void cancelarCanceladaEsIdempotente() {
        Reserva reserva = nuevaReserva();
        reserva.cancelar();

        reserva.cancelar();

        assertThat(reserva.getEstado()).isEqualTo(EstadoReserva.CANCELADA);
    }

    @Test
    void numeroPersonasSeMantieneComoInt() throws NoSuchFieldException {
        Reserva reserva = new Reserva("Ana García", LocalDate.of(2026, 7, 1), 4);

        assertThat(reserva.getNumeroPersonas()).isEqualTo(4);
        assertThat(Reserva.class.getDeclaredField("numeroPersonas").getType()).isEqualTo(int.class);
    }

    private static Reserva nuevaReserva() {
        return new Reserva("Ana García", LocalDate.of(2026, 7, 1), 4);
    }
}
