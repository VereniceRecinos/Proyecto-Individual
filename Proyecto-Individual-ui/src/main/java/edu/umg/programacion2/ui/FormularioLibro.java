package edu.umg.programacion2.ui;

import edu.umg.programacion2.modelo.Libro;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class FormularioLibro extends JPanel {

    private JLabel etiquetaTitulo;

    private JTextField campoTitulo;
    private JTextField campoAutor;
    private JTextField campoCategoria;
    private JTextField campoPrecio;
    private JTextField campoExistencias;
    private JTextField campoAnioPublicacion;

    private JButton botonGuardar;
    private JButton botonCancelar;

    public FormularioLibro() {

        setBorder(BorderFactory.createTitledBorder("Registrar Nuevo Libro"));

        inicializarComponentes();
    }

    private void inicializarComponentes() {

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        etiquetaTitulo = new JLabel("Registrar Nuevo Libro:");

        campoTitulo = new JTextField(20);
        campoAutor = new JTextField(20);
        campoCategoria = new JTextField(20);
        campoPrecio = new JTextField(20);
        campoExistencias = new JTextField(20);
        campoAnioPublicacion = new JTextField(20);

        botonGuardar = new JButton("Guardar");
        botonCancelar = new JButton("Cancelar");

        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Título:"), gbc);

        gbc.gridx = 1;
        add(campoTitulo, gbc);

        // Precio
        gbc.gridx = 2;
        add(new JLabel("Precio (Q):"), gbc);

        gbc.gridx = 3;
        add(campoPrecio, gbc);

        // Autor
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Autor:"), gbc);

        gbc.gridx = 1;
        add(campoAutor, gbc);

        // Existencias
        gbc.gridx = 2;
        add(new JLabel("Existencias:"), gbc);

        gbc.gridx = 3;
        add(campoExistencias, gbc);

        // Categoría
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Categoría:"), gbc);

        gbc.gridx = 1;
        add(campoCategoria, gbc);

        // Año
        gbc.gridx = 2;
        add(new JLabel("Año de publicación:"), gbc);

        gbc.gridx = 3;
        add(campoAnioPublicacion, gbc);

        // Botón Guardar
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        add(botonGuardar, gbc);

        // Botón Cancelar
        gbc.gridy = 1;
        add(botonCancelar, gbc);
    }

    public JTextField getCampoTitulo() {
        return campoTitulo;
    }

    public JTextField getCampoAutor() {
        return campoAutor;
    }

    public JTextField getCampoCategoria() {
        return campoCategoria;
    }

    public JTextField getCampoPrecio() {
        return campoPrecio;
    }

    public JTextField getCampoExistencias() {
        return campoExistencias;
    }

    public JTextField getCampoAnioPublicacion() {
        return campoAnioPublicacion;
    }

    public JButton getBotonGuardar() {
        return botonGuardar;
    }

    public JButton getBotonCancelar() {
        return botonCancelar;
    }

    public void cargarLibro(Libro libro) {

        campoTitulo.setText(libro.getTitulo());
        campoAutor.setText(libro.getAutor());
        campoCategoria.setText(libro.getCategoria());
        campoPrecio.setText(String.valueOf(libro.getPrecio()));
        campoExistencias.setText(String.valueOf(libro.getExistencias()));
        campoAnioPublicacion.setText(String.valueOf(libro.getAnioPublicacion()));
    }

    public void limpiar() {

        campoTitulo.setText("");
        campoAutor.setText("");
        campoCategoria.setText("");
        campoPrecio.setText("");
        campoExistencias.setText("");
        campoAnioPublicacion.setText("");

        campoAutor.setEnabled(true);
        campoAnioPublicacion.setEnabled(true);

        setBorder(
                BorderFactory.createTitledBorder("Registrar Nuevo Libro")
        );

        botonGuardar.setText("Guardar");
    }

    public void activarModoEdicion() {

        setBorder(
                BorderFactory.createTitledBorder("Editar Libro")
        );

        botonGuardar.setText("Guardar Cambios");

        campoAutor.setEnabled(false);
        campoAnioPublicacion.setEnabled(false);
    }

    public String getTitulo() {
        return campoTitulo.getText().trim();
    }

    public String getAutor() {
        return campoAutor.getText().trim();
    }

    public String getCategoria() {
        return campoCategoria.getText().trim();
    }

    public String getPrecio() {
        return campoPrecio.getText().trim();
    }

    public String getExistencias() {
        return campoExistencias.getText().trim();
    }

    public String getAnioPublicacion() {
        return campoAnioPublicacion.getText().trim();
    }
}