package com.ceiba.biblioteca.dominio.servicio.prestamo;

import com.ceiba.biblioteca.dominio.Prestamo;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioPrestamo;
import org.springframework.stereotype.Component;

@Component
public class ServicioGenerarPrestamo {
    private final RepositorioPrestamo repositorioPrestamo;

    public ServicioGenerarPrestamo(RepositorioPrestamo repositorioPrestamo) {
        this.repositorioPrestamo = repositorioPrestamo;
    }

    public void ejecutar(Prestamo prestamo) {
        this.repositorioPrestamo.agregar(prestamo);
    }
}
