-- Migración no destructiva: varios negocios por proveedor y multimedia Base64.
-- Ejecutar una sola vez en Supabase SQL Editor, antes de desplegar esta versión de la API.

ALTER TABLE bookingnow.negocios
    DROP CONSTRAINT IF EXISTS negocios_id_proveedor_key;

ALTER TABLE bookingnow.negocios
    RENAME COLUMN foto_principal TO foto_principal_base64;
ALTER TABLE bookingnow.negocios
    ALTER COLUMN foto_principal_base64 TYPE text;

ALTER TABLE bookingnow.negocio_multimedia
    RENAME COLUMN url TO contenido_base64;
ALTER TABLE bookingnow.negocio_multimedia
    ALTER COLUMN contenido_base64 TYPE text;

ALTER TABLE bookingnow.servicios
    RENAME COLUMN imagen_referencia TO imagen_referencia_base64;
ALTER TABLE bookingnow.servicios
    ALTER COLUMN imagen_referencia_base64 TYPE text;

-- Las URL ya guardadas se conservan como texto histórico. Las nuevas peticiones
-- de la API aceptan únicamente data URLs Base64 de imagen (y video en galería),
-- con máximo 5 MiB por elemento.
