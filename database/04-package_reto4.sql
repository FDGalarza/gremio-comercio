-- ====================================================================
-- Script: 04-package_reto4.sql
-- Descripción: Paquete PL/SQL para retornar comerciantes activos
--              con resumen de sus establecimientos
-- ====================================================================

-- =======================================================
-- 1. Especificación del paquete
-- =======================================================
CREATE OR REPLACE PACKAGE pkg_comerciantes AS
  -- Cursor referenciado para retornar los comerciantes activos
  TYPE cur_comerciantes IS REF CURSOR;

  -- Función que retorna el cursor con el resumen
  FUNCTION obtener_comerciantes_activos
    RETURN cur_comerciantes;
END pkg_comerciantes;
/

-- =======================================================
-- 2. Implementación del paquete
-- =======================================================
CREATE OR REPLACE PACKAGE BODY pkg_comerciantes AS

  FUNCTION obtener_comerciantes_activos
    RETURN cur_comerciantes
  IS
    v_cursor cur_comerciantes;
  BEGIN
    -- Abrimos el cursor con el resumen de comerciantes activos
    OPEN v_cursor FOR
      SELECT c.nombre_razon_social,
             c.municipio,
             c.telefono,
             c.correo_electronico,
             c.fecha_registro,
             c.estado,
             COUNT(e.id_establecimiento) AS cantidad_establecimientos,
             NVL(SUM(e.ingresos), 0)     AS total_ingresos,
             NVL(SUM(e.numero_empleados), 0) AS cantidad_empleados
      FROM comerciante c
      LEFT JOIN establecimiento e
        ON c.id_comerciante = e.id_comerciante
      WHERE c.estado = 'ACTIVO'
      GROUP BY c.id_comerciante,
               c.nombre_razon_social,
               c.municipio,
               c.telefono,
               c.correo_electronico,
               c.fecha_registro,
               c.estado
      ORDER BY COUNT(e.id_establecimiento) DESC;
    RETURN v_cursor;
  END obtener_comerciantes_activos;

END pkg_comerciantes;
/

-- =======================================================
-- Fin del script del RETO 04
-- =======================================================
