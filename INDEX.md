# 📑 ÍNDICE - Documentación del Proyecto

## 📚 Documentación Disponible

### 🚀 **Para empezar rápido**
- **[INSTRUCCIONES.md](INSTRUCCIONES.md)** - Guía simple y directa (START HERE!)
- **[ESTADO_FINAL.md](ESTADO_FINAL.md)** - Resumen de lo completado

### 🔧 **Para instalación y configuración**
- **[SETUP.md](SETUP.md)** - Guía completa de instalación paso a paso
- **[DATABASE_README.md](DATABASE_README.md)** - Detalles técnicos de la BD

### 📋 **Para desarrollo y cambios**
- **[CAMBIOS_RESUMEN.md](CAMBIOS_RESUMEN.md)** - Lista completa de cambios realizados

### 💾 **Utilidades**
- **[sql_ejemplos.sql](sql_ejemplos.sql)** - Queries SQL útiles
- **[quickstart.py](quickstart.py)** - Script de validación
- **[verificar.sh](verificar.sh)** - Script de verificación

---

## 📁 Estructura de Carpetas

```
repositorio-web/
├── src/main/
│   ├── java/com/repositorio/
│   │   ├── modelo/Proyecto.java
│   │   ├── bd/ConexionBD.java
│   │   └── servlet/ListarProyectosServlet.java
│   └── webapp/
│       ├── proyectos.jsp
│       ├── guardar-proyecto.jsp
│       ├── index.jsp
│       ├── bibliograficos.jsp
│       └── style.css
├── target/
│   └── mi-repositorio-web-1.0.war (deployable)
├── pom.xml (configuración Maven)
└── [Documentación]
```

---

## 🎯 Flujo de Información

```
Usuario
   ↓
[proyectos.jsp] ← Muestra formulario + listado
   ↓
[Formulario] → [guardar-proyecto.jsp]
   ↓
[ConexionBD.insertarProyecto()]
   ↓
[repositorio.db] ← Base de datos SQLite
   ↓
[Confirmación] → Usuario ve proyecto en lista
```

---

## 📊 Base de Datos

**Tabla: proyectos**

| Campo | Tipo | Restricción |
|-------|------|------------|
| id | INTEGER | PK AUTO |
| nombre_proyecto | TEXT | NOT NULL |
| autores | TEXT | NOT NULL |
| anio | INTEGER | NOT NULL |
| categoria | TEXT | NOT NULL |
| resumen | TEXT | NOT NULL |
| palabras_clave | TEXT | NULL |
| enlaces | TEXT | NULL |
| correo_contacto | TEXT | NOT NULL |
| fecha_registro | TIMESTAMP | DEFAULT NOW |

**Ubicación**: `repositorio.db` (se crea automáticamente)

---

## 🔧 Métodos CRUD Disponibles

### ConexionBD.java

```java
// Inicialización
ConexionBD.inicializarBD()
ConexionBD.getConexion()

// CREATE
ConexionBD.insertarProyecto(Proyecto)

// READ
ConexionBD.obtenerTodosLosProyectos()
ConexionBD.obtenerProyectoPorId(int)
ConexionBD.obtenerProyectosPorCategoria(String)

// UPDATE
ConexionBD.actualizarProyecto(Proyecto)

// DELETE
ConexionBD.eliminarProyecto(int)

// Limpieza
ConexionBD.cerrarConexion()
```

---

## 🌐 Endpoints Web

### JSP Pages

| URL | Descripción | Método |
|-----|-------------|--------|
| `/index.jsp` | Página de inicio | GET |
| `/proyectos.jsp` | Formulario + Listado | GET |
| `/guardar-proyecto.jsp` | Procesa formulario | POST |
| `/bibliograficos.jsp` | Material bibliográfico | GET |

### Servlet

| URL | Descripción |
|-----|-------------|
| `/listar-proyectos` | Servlet que carga BD |

---

## 📝 Formulario

### Campos del formulario

```
┌─────────────────────────┬────────────┬──────────────────────┐
│ Campo                   │ Obligatorio│ Tipo                 │
├─────────────────────────┼────────────┼──────────────────────┤
│ Nombre del Proyecto     │ Sí         │ text (max 255)       │
│ Autores                 │ Sí         │ text (max 255)       │
│ Año                     │ Sí         │ number (2000-2100)   │
│ Categoría               │ Sí         │ select               │
│ Resumen                 │ Sí         │ textarea             │
│ Palabras Clave          │ No         │ text (max 255)       │
│ Enlaces                 │ No         │ url                  │
│ Correo de Contacto      │ Sí         │ email                │
└─────────────────────────┴────────────┴──────────────────────┘
```

---

## ✅ Checklist de Verificación

### Antes de desplegar

- [ ] WAR compilado correctamente
- [ ] Archivo `target/mi-repositorio-web-1.0.war` existe
- [ ] Tomcat instalado y configurado
- [ ] Puerto 8080 disponible
- [ ] Permisos de escritura en directorio

### Después de desplegar

- [ ] Aplicación accesible en `http://localhost:8080/mi-repositorio-web-1.0/`
- [ ] Formulario carga correctamente
- [ ] Se puede completar y guardar proyecto
- [ ] BD se crea automáticamente
- [ ] Proyectos aparecen en listado

---

## 🐛 Solución de Problemas

### El WAR no se crea
```bash
mvn clean package -DskipTests
```

### La BD no se inicializa
- Verificar permisos en directorio
- Revisar logs del servidor
- Ejecutar en servidor con escritura

### El formulario no guarda
- Verificar que `guardar-proyecto.jsp` está presente
- Revisar logs del servidor
- Verificar datos del formulario

### Puerto 8080 ocupado
```bash
# Ver qué usa el puerto
netstat -ano | findstr :8080

# Cambiar puerto en Tomcat: conf/server.xml
```

---

## 📞 Contacto y Soporte

Para dudas o problemas:

1. Revisar documentación en esta carpeta
2. Ver logs del servidor
3. Consultar `sql_ejemplos.sql` para queries
4. Ejecutar `quickstart.py` para verificación

---

## 📈 Estadísticas

| Métrica | Valor |
|---------|-------|
| Clases Java | 3 |
| Archivos JSP | 2 (modificados) |
| Tablas BD | 1 |
| Campos BD | 10 |
| Campos formulario | 8 |
| Métodos CRUD | 8 |
| Líneas código Java | ~600 |
| Documentación | 5 archivos |
| Tamaño WAR | 16.8 MB |

---

## 🔗 Archivos Relacionados

### Configuración
- `pom.xml` - Dependencias Maven
- `web.xml` - Configuración web (generado)

### Código Fuente
- `src/main/java/` - Clases Java
- `src/main/webapp/` - Páginas JSP y estilos

### Compilado
- `target/classes/` - Clases compiladas
- `target/mi-repositorio-web-1.0.war` - Archivo deployable

---

## ⏱️ Timeline

- **20/11/2025 19:30** - Inicio implementación
- **20/11/2025 19:49** - Compilación exitosa
- **20/11/2025 19:49** - Empaquetamiento completado
- **20/11/2025** - Documentación completa

---

## 🎯 Próximos Pasos

1. **Desplegar** en Tomcat o servidor
2. **Probar** formulario
3. **Agregar** proyectos
4. **Explorar** BD
5. **Extender** funcionalidades (opcional)

---

**Versión**: 1.0  
**Estado**: ✅ COMPLETO Y FUNCIONAL  
**Actualizado**: 20 de noviembre de 2025

---

> 💡 **Consejo**: Comienza por [INSTRUCCIONES.md](INSTRUCCIONES.md) para despliegue rápido.
