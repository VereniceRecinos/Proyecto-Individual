CREATE DATABASE IF NOT EXISTS catalogo_libros;

USE catalogo_libros;

-- Tabla principal del catálogo de libros.
-- Cada registro representa un libro disponible en la librería.
CREATE TABLE IF NOT EXISTS libros (

	-- Identificador único generado automáticamente.
    id INT AUTO_INCREMENT PRIMARY KEY,
    
    -- Título y autor son datos obligatorios para identificar el libro.
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(150) NOT NULL,
    
    -- Categoría de texto libre para clasificar el libro.
    categoria VARCHAR(100) NOT NULL,
    
    -- Precio con dos decimales para representar valores monetarios.
    precio DECIMAL(10,2) NOT NULL,
    
    -- Cantidad de ejemplares disponibles.
    existencias INT NOT NULL,
    
    -- Año de publicación del libro.
    anio_publicacion INT NOT NULL,
    
	-- Indica si el libro es considerado best seller.
	best_seller BOOLEAN NOT NULL DEFAULT FALSE
);