package com.ceiba.biblioteca.aplicacion.fabrica;

import com.ceiba.biblioteca.aplicacion.comando.ComandoPrestamo;
import com.ceiba.biblioteca.dominio.Prestamo;
import org.springframework.stereotype.Component;

@Component
public class FabricaPrestamo {

    public Prestamo crearPrestamo(ComandoPrestamo comandoPrestamo){
        return new Prestamo(comandoPrestamo.getFechaSolicitud(),
                comandoPrestamo.getLibro(), comandoPrestamo.getFechaEntregaMaxima(),
                comandoPrestamo.getNombreUsuario());
    }
}
