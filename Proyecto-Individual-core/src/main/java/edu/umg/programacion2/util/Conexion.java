package edu.umg.programacion2.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	 	private static final String URL = "jdbc:mysql://localhost:3306/catalogo_libros";
	    private static final String USUARIO = "root";
	    private static final String PASSWORD = "tu_password";

	    public static Connection obtenerConexion() throws SQLException {
	        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
	    }
}
