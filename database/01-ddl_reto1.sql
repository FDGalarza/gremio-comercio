-- ====================================================================
-- Script: 01-ddl_reto1.sql
-- Descripción: Creación de tablas base para usuarios, comerciantes 
--              y establecimientos, con diseño normalizado y 
--              campos claros. 
-- Nota: En este punto solo creamos estructura, las secuencias y triggers
--       se agregan en el reto 02.
-- ====================================================================

-- =======================================================
-- 1. Tabla de usuarios del sistema
--    Aquí guardamos info básica de cada usuario y su rol
-- =======================================================
CREATE TABLE usuario (
    id_usuario        NUMBER(10)       PRIMARY KEY,
    nombre            VARCHAR2(100)    NOT NULL,
    correo_electronico VARCHAR2(150)   NOT NULL UNIQUE,
    contrasena        VARCHAR2(200)    NOT NULL,
    rol               VARCHAR2(20)     CHECK (rol IN ('ADMINISTRADOR','AUXILIAR_REGISTRO'))
);

-- =======================================================
-- 2. Tabla de comerciantes
--    Representa al comerciante o razón social
-- =======================================================
CREATE TABLE comerciante (
    id_comerciante      NUMBER(10)      PRIMARY KEY,
    nombre_razon_social VARCHAR2(150)   NOT NULL,
    municipio           VARCHAR2(100)   NOT NULL,
    telefono            VARCHAR2(20),
    correo_electronico  VARCHAR2(150),
    fecha_registro      DATE            DEFAULT SYSDATE NOT NULL,
    estado              VARCHAR2(10)    CHECK (estado IN ('ACTIVO','INACTIVO')) NOT NULL,
    fecha_actualizacion DATE,
    usuario_actualizacion VARCHAR2(100)
);

-- Índice recomendado para búsquedas frecuentes por estado
CREATE INDEX comerciante_estado_idx ON comerciante (estado);

-- =======================================================
-- 3. Tabla de establecimientos
--    Relacionados con un comerciante dueño
-- =======================================================
CREATE TABLE establecimiento (
    id_establecimiento     NUMBER(10)     PRIMARY KEY,
    nombre_establecimiento VARCHAR2(150) NOT NULL,
    ingresos               NUMBER(15,2)   DEFAULT 0 NOT NULL,
    numero_empleados       NUMBER(5)      DEFAULT 0 NOT NULL,
    id_comerciante         NUMBER(10)     NOT NULL,
    fecha_actualizacion DATE,
    usuario_actualizacion VARCHAR2(100),
    CONSTRAINT fk_establecimiento_comerciante FOREIGN KEY (id_comerciante)
        REFERENCES comerciante (id_comerciante)
);

-- Índice recomendado para buscar establecimientos por comerciante
CREATE INDEX establecimiento_comerciante_idx ON establecimiento (id_comerciante);

-- =======================================================
-- Fin del script del RETO 01
-- =======================================================
