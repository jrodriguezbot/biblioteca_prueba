package com.ceiba.biblioteca.aplicacion.manejadores.prestamo;

import com.ceiba.biblioteca.aplicacion.comando.ComandoPrestamo;
import com.ceiba.biblioteca.aplicacion.fabrica.FabricaPrestamo;
import com.ceiba.biblioteca.aplicacion.manejadores.libro.ManejadorObtenerLibro;
import com.ceiba.biblioteca.dominio.Libro;
import com.ceiba.biblioteca.dominio.Prestamo;
import com.ceiba.biblioteca.dominio.excepcion.PrestamoException;
import com.ceiba.biblioteca.dominio.servicio.prestamo.ServicioGenerarPrestamo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Component
public class ManejadorGenerarPrestamo {

    public static final String LIBROS_PALINDROMOS_NO_SE_PRESTAN = "los libros palíndromos solo se\n" +
            "pueden utilizar en la biblioteca";

    public static final int DIAS_MAXIMOS_PRESTAMO_ISBN_MAYOR30 = 15;
    public static final int DIAS_MAXIMOS_PRESTAMO_ISBN_MENOR30 = 20;

    private final ServicioGenerarPrestamo servicioGenerarPrestamo;
    private final ManejadorObtenerLibro manejadorObtenerLibro;
    private final FabricaPrestamo fabricaPrestamo;

    public ManejadorGenerarPrestamo(ServicioGenerarPrestamo servicioGenerarPrestamo,
                                    ManejadorObtenerLibro manejadorObtenerLibro,
                                    FabricaPrestamo fabricaPrestamo) {
        this.servicioGenerarPrestamo = servicioGenerarPrestamo;
        this.manejadorObtenerLibro = manejadorObtenerLibro;
        this.fabricaPrestamo = fabricaPrestamo;
    }

    @Transactional
    public void ejecutar(String isbn, String nombreCliente) {
        ComandoPrestamo comandoPrestamo;
        //Consultar si el libro existe
        Libro libro = manejadorObtenerLibro.ejecutar(isbn);
        int plazoMaximo = 0;
        if (libro!=null){
            //Verificar si ISBN es palindromo
            if (esPalindromo((isbn))) {
                throw new PrestamoException(LIBROS_PALINDROMOS_NO_SE_PRESTAN);
            } else {
                //calcular suma números del ISBN
                plazoMaximo = calcularSumaISBN(isbn) > 30 ?
                        DIAS_MAXIMOS_PRESTAMO_ISBN_MAYOR30
                        : DIAS_MAXIMOS_PRESTAMO_ISBN_MENOR30;
                comandoPrestamo = new ComandoPrestamo(new Date(), libro,
                        calcularFechaEntrega(new Date(), plazoMaximo), nombreCliente);
                Prestamo prestamo = fabricaPrestamo.crearPrestamo(comandoPrestamo);
                servicioGenerarPrestamo.ejecutar(prestamo);
            }
        }
        //throw new UnsupportedOperationException("Método pendiente por implementar");
    }

    /**
     * Método que retorna true o false si la cadena de entrada es palindromo.
     *
     * @param isbn
     * @return true or false
     */
    private boolean esPalindromo(String isbn) {
        int inc = 0;
        int des = isbn.length() - 1;
        boolean bError = false;
        while ((inc < des) && (!bError)) {
            if (isbn.charAt(inc) == isbn.charAt(des)) {
                inc++;
                des--;
            } else bError = true;
        }
        return !bError;
    }

    /**
     * Método que calcula la fecha de devolución del prestamo
     *
     * @param fechaPrestamo
     * @return Fecha de decolución del libro en String
     * @throws ParseException
     */
    private Date calcularFechaEntrega(Date fechaPrestamo, int plazoMaximo) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(fechaPrestamo);
        for (int i = 1; i < plazoMaximo; i++) {
            c.add(Calendar.DATE, 1);
            if (obetenerDia(sdf.format(c.getTime())).equals("Sunday")) c.add(Calendar.DATE, 1);
        }
        return c.getTime();
    }

    /**
     * Método que obtiene el día de la semana a partir de una fecha dada.
     *
     * @param fecha
     * @return Día de la semana en String.
     */
    private String obetenerDia(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate ld = LocalDate.parse(fecha, formatter);
        DayOfWeek dia = ld.getDayOfWeek();
        return dia.getDisplayName(TextStyle.FULL, Locale.US);
    }

    /**
     * Método que retorna la suma de los numeros del ISBN
     *
     * @param isbn
     * @return suma de los números del ISBN
     */
    private int calcularSumaISBN(String isbn) {
        int result = 0;
        String numISBN = obtenerNumerosISBN(isbn);
        for (int i = 0; i < numISBN.length(); i++) result += Character.getNumericValue(numISBN.charAt(i));
        return result;
    }

    /**
     * Método que retorna los numeros del ISBN
     *
     * @param isbn
     * @return string con los números del ISBN
     */
    private String obtenerNumerosISBN(String isbn) {
        isbn = isbn.replaceAll("[^\\d]", " ").trim();
        isbn = isbn.replaceAll(" ", "");
        if (isbn.equals("")) return "-1";
        return isbn;
    }
}
