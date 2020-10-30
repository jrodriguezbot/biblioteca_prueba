
package com.ceiba.biblioteca.testdatabuilder;

import com.ceiba.biblioteca.aplicacion.comando.ComandoLibro;
import com.ceiba.biblioteca.aplicacion.comando.ComandoPrestamo;
import com.ceiba.biblioteca.dominio.Libro;
import com.ceiba.biblioteca.dominio.Prestamo;

import java.util.Date;

public class PrestamoTestDataBuilder {

    private static final String NOMBRE_CLIENTE = "Pedro Jesus";
    private static final Date FECHA_SOLICITUD = new Date();
    private static final Libro LIBRO = new LibroTestDataBuilder().build();
    private static final Date FECHA_ENTREGA_MAXIMA = new Date();

    private String nombre_cliente;
    private Date fecha_solicitud;
    private Libro libro;
    private Date fecha_entrega_maxima;

    public PrestamoTestDataBuilder(){
        this.nombre_cliente = NOMBRE_CLIENTE;
        this.fecha_solicitud = FECHA_SOLICITUD;
        this.libro = LIBRO;
        this.fecha_entrega_maxima = FECHA_ENTREGA_MAXIMA;
    }

    public PrestamoTestDataBuilder connombrecli(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
        return this;
    }

    public PrestamoTestDataBuilder confechasol(Date fecha_solicitud) {
        this.fecha_solicitud = fecha_solicitud;
        return this;
    }

    public PrestamoTestDataBuilder conlibro(Libro libro) {
        this.libro = libro;
        return this;
    }

    public PrestamoTestDataBuilder confechamax(Date fecha_entrega_maxima) {
        this.fecha_entrega_maxima = fecha_entrega_maxima;
        return this;
    }

    public Prestamo build() {
        return new Prestamo(new Date(), new LibroTestDataBuilder().build(), new Date(), NOMBRE_CLIENTE);
    }

    public ComandoPrestamo buildComando() {
        return new ComandoPrestamo(this.fecha_solicitud, this.libro,
                this.fecha_entrega_maxima, this.nombre_cliente);
    }
}

