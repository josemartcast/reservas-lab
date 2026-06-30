package com.joseangel.reservas.reserva.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CrearReservaRequestValidationTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidatorFactory() {
        validatorFactory.close();
    }

    @Test
    void requestValido() {
        CrearReservaRequest request = requestValidoBuilder().build();

        Set<ConstraintViolation<CrearReservaRequest>> violations = validate(request);

        assertThat(violations).isEmpty();
    }

    @Test
    void nombreNulo() {
        CrearReservaRequest request = requestValidoBuilder().nombreCliente(null).build();

        Set<ConstraintViolation<CrearReservaRequest>> violations = validate(request);

        assertThat(violations).anySatisfy(violation ->
                assertThat(violation.getPropertyPath()).hasToString("nombreCliente")
        );
    }

    @Test
    void nombreVacio() {
        CrearReservaRequest request = requestValidoBuilder().nombreCliente("").build();

        Set<ConstraintViolation<CrearReservaRequest>> violations = validate(request);

        assertThat(violations).anySatisfy(violation ->
                assertThat(violation.getPropertyPath()).hasToString("nombreCliente")
        );
    }

    @Test
    void nombreSoloEspacios() {
        CrearReservaRequest request = requestValidoBuilder().nombreCliente("   ").build();

        Set<ConstraintViolation<CrearReservaRequest>> violations = validate(request);

        assertThat(violations).anySatisfy(violation ->
                assertThat(violation.getPropertyPath()).hasToString("nombreCliente")
        );
    }

    @Test
    void nombreSuperiorA100Caracteres() {
        CrearReservaRequest request = requestValidoBuilder().nombreCliente("a".repeat(101)).build();

        Set<ConstraintViolation<CrearReservaRequest>> violations = validate(request);

        assertThat(violations).anySatisfy(violation ->
                assertThat(violation.getPropertyPath()).hasToString("nombreCliente")
        );
    }

    @Test
    void fechaNula() {
        CrearReservaRequest request = requestValidoBuilder().fecha(null).build();

        Set<ConstraintViolation<CrearReservaRequest>> violations = validate(request);

        assertThat(violations).anySatisfy(violation ->
                assertThat(violation.getPropertyPath()).hasToString("fecha")
        );
    }

    @Test
    void fechaPasada() {
        CrearReservaRequest request = requestValidoBuilder().fecha(LocalDate.now().minusDays(1)).build();

        Set<ConstraintViolation<CrearReservaRequest>> violations = validate(request);

        assertThat(violations).anySatisfy(violation ->
                assertThat(violation.getPropertyPath()).hasToString("fecha")
        );
    }

    @Test
    void fechaActualValida() {
        CrearReservaRequest request = requestValidoBuilder().fecha(LocalDate.now()).build();

        Set<ConstraintViolation<CrearReservaRequest>> violations = validate(request);

        assertThat(violations).isEmpty();
    }

    @Test
    void numeroPersonasNulo() {
        CrearReservaRequest request = requestValidoBuilder().numeroPersonas(null).build();

        Set<ConstraintViolation<CrearReservaRequest>> violations = validate(request);

        assertThat(violations).anySatisfy(violation ->
                assertThat(violation.getPropertyPath()).hasToString("numeroPersonas")
        );
    }

    @Test
    void numeroPersonasIgualA0() {
        CrearReservaRequest request = requestValidoBuilder().numeroPersonas(0).build();

        Set<ConstraintViolation<CrearReservaRequest>> violations = validate(request);

        assertThat(violations).anySatisfy(violation ->
                assertThat(violation.getPropertyPath()).hasToString("numeroPersonas")
        );
    }

    @Test
    void numeroPersonasNegativo() {
        CrearReservaRequest request = requestValidoBuilder().numeroPersonas(-1).build();

        Set<ConstraintViolation<CrearReservaRequest>> violations = validate(request);

        assertThat(violations).anySatisfy(violation ->
                assertThat(violation.getPropertyPath()).hasToString("numeroPersonas")
        );
    }

    private static Set<ConstraintViolation<CrearReservaRequest>> validate(CrearReservaRequest request) {
        return validator.validate(request);
    }

    private static CrearReservaRequestBuilder requestValidoBuilder() {
        return new CrearReservaRequestBuilder()
                .nombreCliente("Cliente Prueba")
                .fecha(LocalDate.now().plusDays(1))
                .numeroPersonas(2);
    }

    private static final class CrearReservaRequestBuilder {
        private String nombreCliente;
        private LocalDate fecha;
        private Integer numeroPersonas;

        private CrearReservaRequestBuilder nombreCliente(String nombreCliente) {
            this.nombreCliente = nombreCliente;
            return this;
        }

        private CrearReservaRequestBuilder fecha(LocalDate fecha) {
            this.fecha = fecha;
            return this;
        }

        private CrearReservaRequestBuilder numeroPersonas(Integer numeroPersonas) {
            this.numeroPersonas = numeroPersonas;
            return this;
        }

        private CrearReservaRequest build() {
            return new CrearReservaRequest(nombreCliente, fecha, numeroPersonas);
        }
    }
}
