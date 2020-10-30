package com.ceiba.biblioteca.dominio.servicio.bibliotecario;

import com.ceiba.biblioteca.dominio.Libro;
import com.ceiba.biblioteca.dominio.Prestamo;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioLibro;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioPrestamo;
import com.ceiba.biblioteca.infraestructura.persistencia.builder.LibroBuilder;

import java.util.Date;

public class ServicioBibliotecario {

    public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";

    private final RepositorioLibro repositorioLibro;
    private final RepositorioPrestamo repositorioPrestamo;

    public ServicioBibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
        this.repositorioLibro = repositorioLibro;
        this.repositorioPrestamo = repositorioPrestamo;
    }

    public void prestar(String isbn, String nombreUsuario) {
        //Consultar libro por isbn. Si el libro no esta prestado se hace el prestamo
        //si esta prestado no se realiza el prestamo
        Libro libro =  this.repositorioLibro.obtenerPorIsbn(isbn);
        if (esPrestado(libro.getIsbn())){
            //mensaje el libro no se encuentra disponible
        } else {
            this.repositorioPrestamo.agregar(new Prestamo(new Date(), libro, new Date(), nombreUsuario));
        }
    }

    public boolean esPrestado(String isbn) {
        Libro libro = repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn);
        return libro!=null ? true : false;
        //return false;
    }
}
