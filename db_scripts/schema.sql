USE kazuki_turismo_sch;

-- Tabla de Usuarios (Para el login y perfiles)
CREATE TABLE usuario (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    rol_usuario VARCHAR(50) NOT NULL
);

-- Tabla de Servicios (Hostales, Tours, etc.)
CREATE TABLE servicio (
    id_servicio INT PRIMARY KEY AUTO_INCREMENT,
    nombre_hostal VARCHAR(100) NOT NULL,
    tipo_hostal VARCHAR(50),
    huespedes_max INT,
    valor DECIMAL(10, 2)
);

-- Tabla de Reservas (La más importante para el CRUD)
CREATE TABLE reserva (
    id_reserva INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT,
    id_servicio INT,
    fecha_inicio DATE,
    fecha_final DATE,
    huespedes INT,
    pago_total DECIMAL(10, 2),
    estado_reserva VARCHAR(50) DEFAULT 'ACTIVO',
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_servicio) REFERENCES servicio(id_servicio)
);

INSERT INTO usuario (nombre, correo, contrasena, rol_usuario)
VALUES ('Julissa', 'julissa@mail.com', '1234', 'CLIENTE');

INSERT INTO servicio (nombre_hostal, tipo_hostal, huespedes_max, valor)
VALUES ('Hostal Andes', 'Hostal', 4, 150000);

INSERT INTO reserva (id_usuario, id_servicio, fecha_inicio, fecha_final, huespedes, pago_total)
VALUES (1, 1, '2026-04-10', '2026-04-15', 2, 300000);

SELECT *
	FROM reserva;
    
