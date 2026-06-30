package com.joseangel.reservas.reserva.repository;

import com.joseangel.reservas.reserva.domain.Reserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class ReservaRepositoryTest {

    @Autowired
    private ReservaRepository reservaRepository;

    @BeforeEach
    void cadaTestEmpiezaSinReservasPersistidas() {
        assertThat(reservaRepository.count()).isZero();
    }

    @Test
    void guardaUnaReservaValidaYRecibeId() {
        Reserva reserva = reservaRepository.saveAndFlush(nuevaReserva("Ana García", LocalDate.of(2026, 7, 1), 4));

        assertThat(reserva.getId()).isNotNull();
    }

    @Test
    void detectaDuplicadoExactoMedianteRepositorio() {
        reservaRepository.saveAndFlush(nuevaReserva("Ana García", LocalDate.of(2026, 7, 1), 4));

        boolean existeDuplicado = reservaRepository.existsByNombreClienteAndFechaAndNumeroPersonas(
                "Ana García",
                LocalDate.of(2026, 7, 1),
                4
        );

        assertThat(existeDuplicado).isTrue();
    }

    @Test
    void restriccionUnicaDeBaseDeDatosImpideGuardarDuplicadoExacto() {
        reservaRepository.saveAndFlush(nuevaReserva("Ana García", LocalDate.of(2026, 7, 1), 4));

        assertThatThrownBy(() -> reservaRepository.saveAndFlush(nuevaReserva("Ana García", LocalDate.of(2026, 7, 1), 4)))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void permiteReservaConNombreDiferente() {
        reservaRepository.saveAndFlush(nuevaReserva("Ana García", LocalDate.of(2026, 7, 1), 4));

        Reserva reserva = reservaRepository.saveAndFlush(nuevaReserva("Luis Pérez", LocalDate.of(2026, 7, 1), 4));

        assertThat(reserva.getId()).isNotNull();
        assertThat(reservaRepository.count()).isEqualTo(2);
    }

    @Test
    void permiteReservaConFechaDiferente() {
        reservaRepository.saveAndFlush(nuevaReserva("Ana García", LocalDate.of(2026, 7, 1), 4));

        Reserva reserva = reservaRepository.saveAndFlush(nuevaReserva("Ana García", LocalDate.of(2026, 7, 2), 4));

        assertThat(reserva.getId()).isNotNull();
        assertThat(reservaRepository.count()).isEqualTo(2);
    }

    @Test
    void permiteReservaConNumeroPersonasDiferente() {
        reservaRepository.saveAndFlush(nuevaReserva("Ana García", LocalDate.of(2026, 7, 1), 4));

        Reserva reserva = reservaRepository.saveAndFlush(nuevaReserva("Ana García", LocalDate.of(2026, 7, 1), 5));

        assertThat(reserva.getId()).isNotNull();
        assertThat(reservaRepository.count()).isEqualTo(2);
    }

    @Test
    void listadoQuedaOrdenadoPorIdAscendente() {
        reservaRepository.saveAllAndFlush(List.of(
                nuevaReserva("Ana García", LocalDate.of(2026, 7, 1), 4),
                nuevaReserva("Luis Pérez", LocalDate.of(2026, 7, 2), 2),
                nuevaReserva("Marta López", LocalDate.of(2026, 7, 3), 6)
        ));

        List<Reserva> reservas = reservaRepository.findAllByOrderByIdAsc();

        assertThat(reservas)
                .hasSize(3)
                .extracting(Reserva::getId)
                .isSorted();
    }

    private static Reserva nuevaReserva(String nombreCliente, LocalDate fecha, int numeroPersonas) {
        return new Reserva(nombreCliente, fecha, numeroPersonas);
    }
}
