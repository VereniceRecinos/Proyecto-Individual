package edu.umg.programacion2.ui;

import edu.umg.programacion2.dao.LibroDAO;
import edu.umg.programacion2.excepcion.DatosException;
import edu.umg.programacion2.modelo.Libro;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.Year;
import java.util.List;
import java.util.Optional;

public class VentanaPrincipal extends JFrame {

    private FormularioLibro formulario;

    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;

    private JTextField campoBuscarId;

    private JButton botonEditar;
    private JButton botonEliminar;
    private JButton botonSalir;

    private LibroDAO libroDAO;

    private Libro libroEnEdicion;

    public VentanaPrincipal() {

        libroDAO = new LibroDAO();

        setTitle("Catálogo de Librería - Guatemala");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
        cargarLibros();
    }

    private void inicializarComponentes() {

        setLayout(new BorderLayout(10, 10));

        JLabel tituloPrincipal = new JLabel(
                "Catálogo de Librería",
                JLabel.CENTER
        );

        tituloPrincipal.setFont(
                tituloPrincipal.getFont().deriveFont(28f)
        );

        add(tituloPrincipal, BorderLayout.NORTH);

        formulario = new FormularioLibro();

        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));

        panelCentro.add(formulario, BorderLayout.NORTH);

        JPanel panelLibros = crearPanelLibros();

        panelCentro.add(panelLibros, BorderLayout.CENTER);

        add(panelCentro, BorderLayout.CENTER);

        configurarEventos();
    }

    private JPanel crearPanelLibros() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(0, 10, 10, 10)
        );

        JLabel etiquetaLibros = new JLabel("Libros Registrados:");

        etiquetaLibros.setFont(
                etiquetaLibros.getFont().deriveFont(20f)
        );

        panel.add(etiquetaLibros, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
                new Object[]{
                    "ID",
                    "Título",
                    "Autor",
                    "Categoría",
                    "Precio (Q)",
                    "Existencias",
                    "Año de publicación"
                },
                0
        ) {

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaLibros = new JTable(modeloTabla);

        tablaLibros.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        );

        tablaLibros.setRowHeight(28);

        JScrollPane scrollTabla = new JScrollPane(tablaLibros);

        panel.add(scrollTabla, BorderLayout.CENTER);

        JPanel panelDerecho = crearPanelAcciones();

        panel.add(panelDerecho, BorderLayout.EAST);

        return panel;
    }

    private JPanel crearPanelAcciones() {

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(6, 1, 10, 15));

        JLabel etiquetaBuscar = new JLabel("Buscar ID:");

        campoBuscarId = new JTextField();

        JButton botonBuscar = new JButton("Buscar");

        botonEditar = new JButton("Editar");

        botonEliminar = new JButton("Eliminar");

        botonSalir = new JButton("Salir");

        JPanel panelBuscar = new JPanel(new BorderLayout(5, 5));

        JPanel panelTextoBuscar = new JPanel(new BorderLayout());

        panelTextoBuscar.add(etiquetaBuscar, BorderLayout.NORTH);
        panelTextoBuscar.add(campoBuscarId, BorderLayout.CENTER);

        panelBuscar.add(panelTextoBuscar, BorderLayout.CENTER);
        panelBuscar.add(botonBuscar, BorderLayout.SOUTH);

        panel.add(panelBuscar);
        panel.add(botonEditar);
        panel.add(botonEliminar);
        panel.add(new JLabel(""));
        panel.add(botonSalir);

        botonBuscar.addActionListener(e -> buscarLibro());

        return panel;
    }

    private void configurarEventos() {

        formulario.getBotonGuardar().addActionListener(
                e -> guardarLibro()
        );

        formulario.getBotonCancelar().addActionListener(
                e -> cancelar()
        );

        botonEditar.addActionListener(
                e -> editarLibro()
        );

        botonEliminar.addActionListener(
                e -> eliminarLibros()
        );

        botonSalir.addActionListener(
                e -> salir()
        );
    }

    private void cargarLibros() {

        try {

            List<Libro> libros = libroDAO.listarTodos();

            modeloTabla.setRowCount(0);

            for (Libro libro : libros) {

                modeloTabla.addRow(
                        new Object[]{
                            libro.getId(),
                            libro.getTitulo(),
                            libro.getAutor(),
                            libro.getCategoria(),
                            String.format("%.2f", libro.getPrecio()),
                            libro.getExistencias(),
                            libro.getAnioPublicacion()
                        }
                );
            }

        } catch (DatosException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudieron cargar los libros.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void guardarLibro() {

        if (libroEnEdicion == null) {

            crearLibro();

        } else {

            guardarCambios();
        }
    }

    private void crearLibro() {

        try {

            String titulo = formulario.getTitulo();
            String autor = formulario.getAutor();
            String categoria = formulario.getCategoria();

            double precio = obtenerPrecio();
            int existencias = obtenerExistencias();
            int anio = obtenerAnio();
            boolean bestSeller = formulario.isBestSeller();

            if (titulo.isEmpty()) {

                mostrarMensaje(
                        "El título es obligatorio."
                );

                return;
            }

            if (autor.isEmpty()) {

                mostrarMensaje(
                        "El autor es obligatorio."
                );

                return;
            }

            if (libroDAO.existePorTituloYAutor(titulo, autor)) {

                mostrarMensaje(
                        "El libro \"" + titulo + "\" de " + autor
                        + " ya se encuentra registrado en el catálogo."
                );

                return;
            }

            if (precio <= 0) {

                mostrarMensaje(
                        "El precio debe ser mayor que cero."
                );

                return;
            }

            if (existencias < 0) {

                mostrarMensaje(
                        "Las existencias no pueden ser negativas."
                );

                return;
            }

            int anioActual = Year.now().getValue();

            if (anio > anioActual) {

                mostrarMensaje(
                        "El año de publicación no puede ser mayor que "
                        + anioActual + "."
                );

                return;
            }

            Libro libro = new Libro(
                    titulo,
                    autor,
                    categoria,
                    precio,
                    existencias,
                    anio,
                    bestSeller
            );

            libroDAO.crear(libro);

            cargarLibros();

            formulario.limpiar();

            mostrarMensaje(
                    "El libro se registró correctamente.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    "Precio, existencias y año de publicación deben "
                    + "contener valores numéricos."
            );

        } catch (DatosException e) {

            mostrarMensaje(
                    "No se pudo registrar el libro en la base de datos."
            );
        }
    }

    private void editarLibro() {

        int cantidadSeleccionada = tablaLibros.getSelectedRowCount();

        if (cantidadSeleccionada == 0) {

            mostrarMensaje(
                    "Por favor, selecciona el libro que deseas editar."
            );

            return;
        }

        if (cantidadSeleccionada > 1) {

            mostrarMensaje(
                    "Solo se puede editar un libro a la vez. "
                    + "Por favor, selecciona únicamente el libro "
                    + "que deseas modificar."
            );

            return;
        }

        int filaSeleccionada = tablaLibros.getSelectedRow();

        int id = obtenerIdDeFila(filaSeleccionada);

        try {

            Optional<Libro> resultado = libroDAO.buscarPorId(id);

            if (resultado.isPresent()) {

                libroEnEdicion = resultado.get();

                formulario.cargarLibro(libroEnEdicion);

                formulario.activarModoEdicion();
            }

        } catch (DatosException e) {

            mostrarMensaje(
                    "No se pudo obtener el libro seleccionado."
            );
        }
    }

    private void guardarCambios() {

        try {

            String titulo = formulario.getTitulo();
            String categoria = formulario.getCategoria();

            double precio = obtenerPrecio();
            int existencias = obtenerExistencias();

            if (titulo.isEmpty()) {

                mostrarMensaje(
                        "El título es obligatorio."
                );

                return;
            }

            if (precio <= 0) {

                mostrarMensaje(
                        "El precio debe ser mayor que cero."
                );

                return;
            }

            if (existencias < 0) {

                mostrarMensaje(
                        "Las existencias no pueden ser negativas."
                );

                return;
            }

            libroEnEdicion.setTitulo(titulo);
            libroEnEdicion.setCategoria(categoria);
            libroEnEdicion.setPrecio(precio);
            libroEnEdicion.setExistencias(existencias);

            libroDAO.actualizar(libroEnEdicion);

            cargarLibros();

            libroEnEdicion = null;

            formulario.limpiar();

            mostrarMensaje(
                    "Los cambios se guardaron correctamente.",
                    "Edición exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    "Precio y existencias deben contener valores numéricos."
            );

        } catch (DatosException e) {

            mostrarMensaje(
                    "No se pudieron guardar los cambios."
            );
        }
    }

    private void buscarLibro() {

        String textoId = campoBuscarId.getText().trim();

        if (textoId.isEmpty()) {

            mostrarMensaje(
                    "Ingresa el ID del libro que deseas buscar."
            );

            return;
        }

        try {

            int id = Integer.parseInt(textoId);

            Optional<Libro> resultado = libroDAO.buscarPorId(id);

            if (resultado.isPresent()) {

                seleccionarFilaPorId(id);

            } else {

                mostrarMensaje(
                        "No se encontró ningún libro registrado "
                        + "con el ID ingresado."
                );
            }

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    "El ID debe ser un número entero."
            );

        } catch (DatosException e) {

            mostrarMensaje(
                    "No se pudo realizar la búsqueda."
            );
        }
    }

    private void seleccionarFilaPorId(int id) {

        for (int fila = 0; fila < modeloTabla.getRowCount(); fila++) {

            int idFila = Integer.parseInt(
                    modeloTabla.getValueAt(fila, 0).toString()
            );

            if (idFila == id) {

                tablaLibros.clearSelection();

                tablaLibros.addRowSelectionInterval(fila, fila);

                tablaLibros.scrollRectToVisible(
                        tablaLibros.getCellRect(fila, 0, true)
                );

                return;
            }
        }
    }

    private void eliminarLibros() {

        int[] filasSeleccionadas = tablaLibros.getSelectedRows();

        if (filasSeleccionadas.length == 0) {

            mostrarMensaje(
                    "Por favor, selecciona el libro que desea borrar."
            );

            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea eliminar los registros seleccionados?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (respuesta != JOptionPane.YES_OPTION) {

            return;
        }

        try {

            for (int i = filasSeleccionadas.length - 1; i >= 0; i--) {

                int fila = filasSeleccionadas[i];

                int id = obtenerIdDeFila(fila);

                libroDAO.eliminar(id);
            }

            cargarLibros();

            mostrarMensaje(
                    "Los registros seleccionados fueron eliminados.",
                    "Eliminación exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (DatosException e) {

            mostrarMensaje(
                    "No se pudieron eliminar los registros seleccionados."
            );
        }
    }

    private int obtenerIdDeFila(int fila) {

        return Integer.parseInt(
                modeloTabla.getValueAt(fila, 0).toString()
        );
    }

    private double obtenerPrecio()
            throws NumberFormatException {

        return Double.parseDouble(
                formulario.getPrecio()
        );
    }

    private int obtenerExistencias()
            throws NumberFormatException {

        return Integer.parseInt(
                formulario.getExistencias()
        );
    }

    private int obtenerAnio()
            throws NumberFormatException {

        return Integer.parseInt(
                formulario.getAnioPublicacion()
        );
    }

    private void cancelar() {

        libroEnEdicion = null;

        formulario.limpiar();

        tablaLibros.clearSelection();
    }

    private void salir() {

        dispose();
    }

    private void mostrarMensaje(String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Aviso",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void mostrarMensaje(
            String mensaje,
            String titulo,
            int tipo) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                titulo,
                tipo
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            VentanaPrincipal ventana = new VentanaPrincipal();

            ventana.setVisible(true);
        });
    }
}