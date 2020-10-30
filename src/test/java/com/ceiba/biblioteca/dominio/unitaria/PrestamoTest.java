package com.ceiba.biblioteca.dominio.unitaria;

import com.ceiba.biblioteca.dominio.Libro;
import com.ceiba.biblioteca.dominio.Prestamo;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioPrestamo;
import com.ceiba.biblioteca.dominio.servicio.prestamo.ServicioGenerarPrestamo;
import com.ceiba.biblioteca.testdatabuilder.LibroTestDataBuilder;
import com.ceiba.biblioteca.testdatabuilder.PrestamoTestDataBuilder;
import org.junit.Test;

import java.util.Date;
import static org.mockito.Mockito.*;

public class PrestamoTest {

    private static final String NOMBRE_CLIENTE = "Pedro Jesus";
    private static final Date FECHA_SOLICITUD = new Date();
    private static final Libro LIBRO = new LibroTestDataBuilder().build();
    private static final Date FECHA_ENTREGA_MAXIMA = new Date();

    @Test
    public void generarPrestamoTest(){

        PrestamoTestDataBuilder prestamoTestDataBuilder = new PrestamoTestDataBuilder().
                confechasol(FECHA_SOLICITUD).
                conlibro(LIBRO).
                confechamax(FECHA_ENTREGA_MAXIMA).
                connombrecli(NOMBRE_CLIENTE);

        Prestamo prestamo = prestamoTestDataBuilder.build();

        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);

        ServicioGenerarPrestamo servicioGenerarPrestamo = mock(ServicioGenerarPrestamo.class);
                doNothing().when(servicioGenerarPrestamo).ejecutar(prestamo);
        servicioGenerarPrestamo.ejecutar(prestamo);

        verify(servicioGenerarPrestamo, times(1)).ejecutar(prestamo);
    }
}
