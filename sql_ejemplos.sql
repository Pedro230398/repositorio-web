-- Script SQL para visualizar los datos en SQLite (opcional)
-- Puedes usar esto en una herramienta SQLite si quieres consultar la BD directamente

-- Ver todos los proyectos
SELECT * FROM proyectos ORDER BY fecha_registro DESC;

-- Ver cantidad de proyectos por categoría
SELECT categoria, COUNT(*) as total FROM proyectos GROUP BY categoria;

-- Ver proyectos de un año específico
SELECT * FROM proyectos WHERE anio = 2025 ORDER BY fecha_registro DESC;

-- Ver proyectos más recientes (últimos 7 días)
SELECT * FROM proyectos 
WHERE fecha_registro >= datetime('now', '-7 days')
ORDER BY fecha_registro DESC;

-- Buscar por palabras clave
SELECT * FROM proyectos 
WHERE palabras_clave LIKE '%sustentabilidad%'
ORDER BY fecha_registro DESC;

-- Ver contactos de proyectos (para notificaciones)
SELECT DISTINCT correo_contacto FROM proyectos;

-- Eliminar un proyecto (cambiar ID según sea necesario)
-- DELETE FROM proyectos WHERE id = 1;

-- Actualizar contacto de un proyecto
-- UPDATE proyectos SET correo_contacto = 'nuevo@email.com' WHERE id = 1;
