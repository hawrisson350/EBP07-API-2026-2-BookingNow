> Revisión histórica previa a la implementación. Estado actualizado: [Sprint 1](../implementation/SPRINT-1.md).

# Revisión inicial del Sprint 1

Fuente: `workspace/Recuento HU.docx`, recibido el 2026-09-17.
Se incorporaron cinco HU y 40 criterios/escenarios indexados. Las fichas conservan
el texto original; este documento recoge observaciones, no nuevos requisitos.
No se modificó código funcional ni se ejecutaron pruebas en esta incorporación.

## Diferencias detectadas

### HU-010101

El registro actual valida formato de correo y longitud de contraseña, pero no unicidad de correo ni presencia obligatoria de letra, número y carácter especial. El nombre de usuario sí sigue siendo requerido por esta HU. Confirmar si el correo debe ser único entre clientes y proveedores y qué caracteres se admiten en nombreUsuario; el código aplica restricciones adicionales.

### HU-010102

Pendiente validar razón social de mínimo 3 caracteres, NIT con 9 dígitos-guion-1 dígito y unicidad de correo, razón social y NIT. La contraseña también exige letra, número y carácter especial. La HU describe el formato del dígito de verificación; no exige explícitamente calcularlo. Confirmar normalización de razón social y alcance de unicidad del correo.

### HU-010201

El login actual usa nombreUsuario; debe pasar a correo. El error genérico debe decir exactamente "Correo o contraseña incorrectos". Faltan estado activo/bloqueado, validación específica de campos y una decisión sobre identificación automática del rol en un login común. El ocultamiento de contraseña es del frontend. El máximo de 3 segundos requiere definir medición y tratamiento del arranque en frío de Render Free; no está verificado.

### HU-020101

Negocio no está implementado. La HU impide registrar otro negocio cuando ya existe uno. Añade modalidad virtual (dirección no obligatoria) y galería multimedia, aunque inicialmente se había supuesto Multimedia fuera del sprint. Falta precisar campos obligatorios, formatos, catálogo de categorías y si fotos/galería se entregan como archivos o URLs, con sus límites. El ocultamiento de opciones corresponde al frontend; el backend también debe impedir duplicados y asociar el negocio al proveedor autenticado.

### HU-030101

Servicio no está implementado. Se exige duración > 0, precio >= 0, asociación a un negocio propio y aparición en su listado. "Uno de mis negocios" contrasta con la restricción de una sola empresa en HU-020101; confirmar cardinalidad. Definir obligatoriedad de imagen, almacenamiento, moneda/precisión y si la duración admite fracciones. La disponibilidad para reservas no describe un caso de uso de creación de reservas.

## Decisiones pendientes

1. ¿El correo es único en toda la plataforma, o una persona puede tener ambos roles con el mismo correo?
2. ¿Se mantiene un negocio por proveedor como establece HU-020101, pese al plural de HU-030101?
3. ¿Cuáles campos de negocio y servicio son obligatorios y cómo se reciben/almacenan imágenes y galería?
4. ¿Cómo se define y administra el estado inactivo/bloqueado de una cuenta?
5. ¿El máximo de tres segundos se mide también durante un arranque en frío de Render Free?

## Orden técnico sugerido

Registros de cliente y proveedor; login por correo y roles; registro de negocio;
registro y listado de servicios. Las migraciones deben coordinarse con el
repositorio de BD. Los criterios visuales requieren también trabajo de frontend.
