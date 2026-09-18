package edu.umg.programacion2.excepcion;

public class DatosException extends Exception {

    public DatosException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
