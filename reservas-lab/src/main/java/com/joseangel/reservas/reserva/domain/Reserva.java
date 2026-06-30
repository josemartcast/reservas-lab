package com.joseangel.reservas.reserva.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(
        name = "reservas",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_reservas_nombre_fecha_personas",
                columnNames = {"nombre_cliente", "fecha", "numero_personas"}
        )
)
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_cliente", nullable = false)
    private String nombreCliente;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "numero_personas", nullable = false)
    private int numeroPersonas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoReserva estado;

    @Version
    private Long version;

    protected Reserva() {
        // Constructor requerido por JPA.
    }

    public Reserva(String nombreCliente, LocalDate fecha, int numeroPersonas) {
        this.nombreCliente = normalizarNombreCliente(nombreCliente);
        this.fecha = Objects.requireNonNull(fecha, "La fecha es obligatoria");
        if (numeroPersonas <= 0) {
            throw new IllegalArgumentException("El número de personas debe ser mayor que cero");
        }
        this.numeroPersonas = numeroPersonas;
        this.estado = EstadoReserva.PENDIENTE;
    }

    public void confirmar() {
        if (estado == EstadoReserva.CANCELADA) {
            throw new IllegalStateException("No se puede confirmar una reserva cancelada");
        }
        estado = EstadoReserva.CONFIRMADA;
    }

    public void cancelar() {
        estado = EstadoReserva.CANCELADA;
    }

    public Long getId() {
        return id;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public Long getVersion() {
        return version;
    }

    private static String normalizarNombreCliente(String nombreCliente) {
        String nombreNormalizado = Objects.requireNonNull(nombreCliente, "El nombre del cliente es obligatorio").trim();
        if (nombreNormalizado.isBlank()) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio");
        }
        return nombreNormalizado;
    }
}
