# Proyecto Individual - Catálogo de Librería

Aplicación de escritorio desarrollada en Java para la gestión de un catálogo de libros utilizando arquitectura por capas, Swing para la interfaz gráfica, JDBC para el acceso a datos y MySQL como sistema gestor de base de datos.

## Características

El sistema permite:

- Registrar libros.
- Buscar libros por ID.
- Editar información de libros existentes.
- Eliminar uno o varios libros.
- Listar todos los libros registrados.
- Validar los datos ingresados por el usuario.
- Persistir la información en una base de datos MySQL.

## Datos almacenados

Cada libro contiene la siguiente información:

- ID (autogenerado por la base de datos)
- Título
- Autor
- Categoría
- Precio
- Existencias
- Año de publicación

## Validaciones implementadas

- El título es obligatorio.
- El autor es obligatorio.
- No se permiten libros duplicados con el mismo título y autor.
- El precio debe ser mayor que cero.
- Las existencias no pueden ser negativas.
- El año de publicación no puede ser mayor al año actual.
- El ID de búsqueda debe ser un número entero.

## Tecnologías utilizadas

- Java 11
- Apache Maven
- Java Swing
- JDBC
- MySQL
- Eclipse IDE

## Estructura del proyecto

```
Proyecto-Individual
│
├── Proyecto-Individual-core
│   ├── modelo
│   ├── dao
│   ├── util
│   └── excepcion
│
├── Proyecto-Individual-ui
│   ├── ui
│   └── sql
│
└── pom.xml
```

### Módulo Core

Contiene la lógica de acceso a datos y las clases del dominio.

- `Libro`
- `LibroDAO`
- `Conexion`
- `DatosException`

### Módulo UI

Contiene la interfaz gráfica desarrollada con Swing.

- `VentanaPrincipal`
- `FormularioLibro`

## Base de datos

Crear la base de datos ejecutando el archivo:

```
Proyecto-Individual-ui/sql/schema.sql
```

La tabla principal utilizada es:

```sql
CREATE TABLE libros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(150) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    existencias INT NOT NULL,
    anio_publicacion INT NOT NULL
);
```

## Configuración de conexión

Editar la clase:

```
Proyecto-Individual-core/src/main/java/edu/umg/programacion2/util/Conexion.java
```

y configurar:

```java
private static final String URL =
        "jdbc:mysql://localhost:3306/catalogo_libros";

private static final String USUARIO = "root";

private static final String PASSWORD = "tu_password";
```

## Compilación

Desde la raíz del proyecto ejecutar:

```bash
mvn clean install
```

Si la compilación finaliza correctamente aparecerá:

```text
BUILD SUCCESS
```

## Ejecución

Ejecutar la clase:

```text
edu.umg.programacion2.ui.VentanaPrincipal
```

## Manejo de excepciones

La interfaz gráfica no depende directamente de JDBC.

Las excepciones SQL son encapsuladas mediante la clase:

```java
DatosException
```

permitiendo desacoplar la capa de presentación de la capa de acceso a datos.