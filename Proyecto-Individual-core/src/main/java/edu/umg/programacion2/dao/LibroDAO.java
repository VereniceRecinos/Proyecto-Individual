package edu.umg.programacion2.dao;

import edu.umg.programacion2.modelo.Libro;
import edu.umg.programacion2.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibroDAO {
	
	public boolean existePorTituloYAutor(String titulo, String autor)
	        throws SQLException {

	    String sql = "SELECT COUNT(*) "
	            + "FROM libros "
	            + "WHERE titulo = ? AND autor = ?";

	    try (Connection conexion = Conexion.obtenerConexion();
	         PreparedStatement ps = conexion.prepareStatement(sql)) {

	        ps.setString(1, titulo);
	        ps.setString(2, autor);

	        try (ResultSet rs = ps.executeQuery()) {

	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	        }
	    }

	    return false;
	}

	public Libro crear(Libro libro) throws SQLException {

	    String sql = "INSERT INTO libros "
	            + "(titulo, autor, categoria, precio, existencias, anio_publicacion) "
	            + "VALUES (?, ?, ?, ?, ?, ?)";

	    try (Connection conexion = Conexion.obtenerConexion();
	         PreparedStatement ps = conexion.prepareStatement(
	                 sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

	        ps.setString(1, libro.getTitulo());
	        ps.setString(2, libro.getAutor());
	        ps.setString(3, libro.getCategoria());
	        ps.setDouble(4, libro.getPrecio());
	        ps.setInt(5, libro.getExistencias());
	        ps.setInt(6, libro.getAnioPublicacion());

	        ps.executeUpdate();

	        try (ResultSet rs = ps.getGeneratedKeys()) {
	            if (rs.next()) {
	                libro.setId(rs.getInt(1));
	            }
	        }

	        return libro;
	    }
	}
	
	public List<Libro> listarTodos() throws SQLException {

	    String sql = "SELECT id, titulo, autor, categoria, precio, existencias, anio_publicacion "
	            + "FROM libros";

	    List<Libro> libros = new ArrayList<>();

	    try (Connection conexion = Conexion.obtenerConexion();
	         PreparedStatement ps = conexion.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {

	            Libro libro = new Libro(
	                    rs.getInt("id"),
	                    rs.getString("titulo"),
	                    rs.getString("autor"),
	                    rs.getString("categoria"),
	                    rs.getDouble("precio"),
	                    rs.getInt("existencias"),
	                    rs.getInt("anio_publicacion")
	            );

	            libros.add(libro);
	        }
	    }

	    return libros;
	}
	
	public Optional<Libro> buscarPorId(int id) throws SQLException {

	    String sql = "SELECT id, titulo, autor, categoria, precio, existencias, anio_publicacion "
	            + "FROM libros "
	            + "WHERE id = ?";

	    try (Connection conexion = Conexion.obtenerConexion();
	         PreparedStatement ps = conexion.prepareStatement(sql)) {

	        ps.setInt(1, id);

	        try (ResultSet rs = ps.executeQuery()) {

	            if (rs.next()) {

	                Libro libro = new Libro(
	                        rs.getInt("id"),
	                        rs.getString("titulo"),
	                        rs.getString("autor"),
	                        rs.getString("categoria"),
	                        rs.getDouble("precio"),
	                        rs.getInt("existencias"),
	                        rs.getInt("anio_publicacion")
	                );

	                return Optional.of(libro);
	            }
	        }
	    }

	    return Optional.empty();
	}
	
	public boolean actualizar(Libro libro) throws SQLException {

	    String sql = "UPDATE libros SET "
	            + "titulo = ?, "
	            + "autor = ?, "
	            + "categoria = ?, "
	            + "precio = ?, "
	            + "existencias = ?, "
	            + "anio_publicacion = ? "
	            + "WHERE id = ?";

	    try (Connection conexion = Conexion.obtenerConexion();
	         PreparedStatement ps = conexion.prepareStatement(sql)) {

	        ps.setString(1, libro.getTitulo());
	        ps.setString(2, libro.getAutor());
	        ps.setString(3, libro.getCategoria());
	        ps.setDouble(4, libro.getPrecio());
	        ps.setInt(5, libro.getExistencias());
	        ps.setInt(6, libro.getAnioPublicacion());
	        ps.setInt(7, libro.getId());

	        int filasAfectadas = ps.executeUpdate();

	        return filasAfectadas > 0;
	    }
	}
	
	public boolean eliminar(int id) throws SQLException {

	    String sql = "DELETE FROM libros WHERE id = ?";

	    try (Connection conexion = Conexion.obtenerConexion();
	         PreparedStatement ps = conexion.prepareStatement(sql)) {

	        ps.setInt(1, id);

	        int filasAfectadas = ps.executeUpdate();

	        return filasAfectadas > 0;
	    }
	}
}
