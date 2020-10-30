package com.ceiba.biblioteca.infraestructura.persistencia.builder;

import com.ceiba.biblioteca.dominio.Libro;
import com.ceiba.biblioteca.dominio.Prestamo;
import com.ceiba.biblioteca.infraestructura.persistencia.entidad.LibroEntity;
import com.ceiba.biblioteca.infraestructura.persistencia.entidad.PrestamoEntity;

public final class PrestamoBuilder {

    //private final LibroBuilder libroBuilder;

    private PrestamoBuilder() {
        //this.libroBuilder = libroBuilder;
    }

    public static Prestamo convertirADominio(PrestamoEntity prestamoEntity) {
        Prestamo prestamo = null;
        Libro libro = null;
        if(prestamoEntity!=null){
            libro = LibroBuilder.convertirADominio(prestamoEntity.getLibro());
            prestamo = new Prestamo(prestamoEntity.getFechaSolicitud(), libro,
                    prestamoEntity.getFechaEntregaMaxima(), prestamoEntity.getNombreUsuario());
        }
        return prestamo;
    }

    public static PrestamoEntity convertirAEntity(Prestamo prestamo){
        PrestamoEntity prestamoEntity = new PrestamoEntity();
        LibroEntity libroEntity = LibroBuilder.convertirAEntity(prestamo.getLibro());
        prestamoEntity.setFechaSolicitud(prestamo.getFechaSolicitud());
        prestamoEntity.setLibro(libroEntity);
        prestamoEntity.setFechaEntregaMaxima(prestamo.getFechaEntregaMaxima());
        prestamoEntity.setNombreUsuario(prestamo.getNombreUsuario());
        return prestamoEntity;
    }
}
