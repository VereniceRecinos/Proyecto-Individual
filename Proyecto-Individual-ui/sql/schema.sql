CREATE DATABASE IF NOT EXISTS catalogo_libros;

USE catalogo_libros;

CREATE TABLE IF NOT EXISTS libros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(150) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    existencias INT NOT NULL,
    anio_publicacion INT NOT NULL
);