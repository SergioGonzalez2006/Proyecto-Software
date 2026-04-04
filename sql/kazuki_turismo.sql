CREATE DATABASE kazuki_turismo;
USE kazuki_turismo;
/*TABLA USUARIO*/
CREATE TABLE usuario(
id_usuario INT PRIMARY KEY NOT NULL,
nombre VARCHAR (50) NOT NULL,
correo VARCHAR (50) NOT NULL,
contrasena VARCHAR (50) NOT NULL,
rol_usuario VARCHAR (50) NOT NULL);



