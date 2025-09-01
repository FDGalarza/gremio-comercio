-- ====================================================================
-- Script: 02-triggers_reto2.sql
-- Descripción: Secuencias y triggers para IDs automáticos y auditoría
-- ====================================================================

-- =======================================================
-- 1. Secuencias para IDs
-- =======================================================
-- Secuencia para usuarios
CREATE SEQUENCE seq_usuario
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

-- Secuencia para comerciantes
CREATE SEQUENCE seq_comerciante
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

-- Secuencia para establecimientos
CREATE SEQUENCE seq_establecimiento
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

-- =======================================================
-- 2. Trigger para asignar ID de usuario automáticamente
-- =======================================================
CREATE OR REPLACE TRIGGER trg_usuario_id
BEFORE INSERT ON usuario
FOR EACH ROW
BEGIN
  IF :NEW.id_usuario IS NULL THEN
    SELECT seq_usuario.NEXTVAL INTO :NEW.id_usuario FROM dual;
  END IF;
END;
/

-- =======================================================
-- 3. Trigger para asignar ID de comerciante automáticamente
--    y llenar campos de auditoría
-- =======================================================
CREATE OR REPLACE TRIGGER trg_comerciante_id_auditoria
BEFORE INSERT OR UPDATE ON comerciante
FOR EACH ROW
BEGIN
  -- Si es inserción y no viene ID, lo asignamos
  IF INSERTING AND :NEW.id_comerciante IS NULL THEN
    SELECT seq_comerciante.NEXTVAL INTO :NEW.id_comerciante FROM dual;
  END IF;

  -- Campos de auditoría
  :NEW.fecha_actualizacion := SYSDATE;
  :NEW.usuario_actualizacion := NVL(SYS_CONTEXT('USERENV','SESSION_USER'),'sistema');
END;
/

-- =======================================================
-- 4. Trigger para asignar ID de establecimiento automáticamente
--    y llenar campos de auditoría
-- =======================================================
CREATE OR REPLACE TRIGGER trg_establecimiento_id_auditoria
BEFORE INSERT OR UPDATE ON establecimiento
FOR EACH ROW
BEGIN
  -- Si es inserción y no viene ID, lo asignamos
  IF INSERTING AND :NEW.id_establecimiento IS NULL THEN
    SELECT seq_establecimiento.NEXTVAL INTO :NEW.id_establecimiento FROM dual;
  END IF;

  -- Campos de auditoría
  :NEW.fecha_actualizacion := SYSDATE;
  :NEW.usuario_actualizacion := NVL(SYS_CONTEXT('USERENV','SESSION_USER'),'sistema');
END;
/

-- ================
