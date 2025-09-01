-- ====================================================================
-- Script: 03-seed_reto3.sql
-- Descripción: Inserta datos iniciales (usuarios, comerciantes y
--              establecimientos) para pruebas y desarrollo
-- ====================================================================

-- =======================================================
-- 1. Usuarios
-- =======================================================
-- Usuario administrador
INSERT INTO usuario (nombre, correo_electronico, contrasena, rol)
VALUES ('Administrador Principal', 'admin@gremio.com', 'admin123', 'ADMINISTRADOR');

-- Usuario auxiliar
INSERT INTO usuario (nombre, correo_electronico, contrasena, rol)
VALUES ('Auxiliar Registro', 'auxiliar@gremio.com', 'aux123', 'AUXILIAR_REGISTRO');

-- =======================================================
-- 2. Comerciantes
-- =======================================================
-- Insertamos 5 comerciantes con datos variados
INSERT INTO comerciante (nombre_razon_social, municipio, telefono, correo_electronico, estado)
VALUES ('Comercial El Éxito', 'Bogotá', '3001112233', 'exito@correo.com', 'ACTIVO');

INSERT INTO comerciante (nombre_razon_social, municipio, telefono, correo_electronico, estado)
VALUES ('Panadería La Espiga', 'Medellín', '3012223344', 'espiga@correo.com', 'ACTIVO');

INSERT INTO comerciante (nombre_razon_social, municipio, telefono, correo_electronico, estado)
VALUES ('Tienda Don Pepe', 'Cali', NULL, 'donpepe@correo.com', 'INACTIVO');

INSERT INTO comerciante (nombre_razon_social, municipio, telefono, correo_electronico, estado)
VALUES ('Supermercado Buen Precio', 'Barranquilla', '3023334455', NULL, 'ACTIVO');

INSERT INTO comerciante (nombre_razon_social, municipio, telefono, correo_electronico, estado)
VALUES ('Librería El Saber', 'Cartagena', '3034445566', 'elsaber@correo.com', 'ACTIVO');

-- =======================================================
-- 3. Establecimientos
-- =======================================================
-- Nota: Para asignar comerciantes al azar, usamos los IDs generados automáticamente

-- Para Comercial El Éxito
INSERT INTO establecimiento (nombre_establecimiento, ingresos, numero_empleados, id_comerciante)
VALUES ('Sucursal Norte', 12000000.50, 25, 1);

INSERT INTO establecimiento (nombre_establecimiento, ingresos, numero_empleados, id_comerciante)
VALUES ('Sucursal Centro', 8000000.00, 15, 1);

-- Para Panadería La Espiga
INSERT INTO establecimiento (nombre_establecimiento, ingresos, numero_empleados, id_comerciante)
VALUES ('Punto Laureles', 2500000.00, 5, 2);

INSERT INTO establecimiento (nombre_establecimiento, ingresos, numero_empleados, id_comerciante)
VALUES ('Punto Poblado', 3200000.00, 6, 2);

-- Para Tienda Don Pepe
INSERT INTO establecimiento (nombre_establecimiento, ingresos, numero_empleados, id_comerciante)
VALUES ('Sucursal San Fernando', 1500000.00, 3, 3);

-- Para Supermercado Buen Precio
INSERT INTO establecimiento (nombre_establecimiento, ingresos, numero_empleados, id_comerciante)
VALUES ('Sucursal Prado', 5000000.00, 10, 4);

INSERT INTO establecimiento (nombre_establecimiento, ingresos, numero_empleados, id_comerciante)
VALUES ('Sucursal Norte', 6500000.00, 12, 4);

-- Para Librería El Saber
INSERT INTO establecimiento (nombre_establecimiento, ingresos, numero_empleados, id_comerciante)
VALUES ('Sucursal Bocagrande', 1000000.00, 2, 5);

INSERT INTO establecimiento (nombre_establecimiento, ingresos, numero_empleados, id_comerciante)
VALUES ('Sucursal Centro', 1800000.00, 4, 5);

INSERT INTO establecimiento (nombre_establecimiento, ingresos, numero_empleados, id_comerciante)
VALUES ('Sucursal Universidades', 2200000.00, 3, 5);

-- =======================================================
-- Confirmamos cambios
-- =======================================================
COMMIT;

-- =======================================================
-- Fin del script del RETO 03
-- =======================================================
