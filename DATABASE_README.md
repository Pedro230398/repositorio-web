# Repositorio Académico Estudiantil - Base de Datos de Proyectos

## 📋 Descripción

Sistema web completo con formulario funcional conectado a una **base de datos SQLite** para gestionar proyectos académicos comunitarios.

## ✨ Características

### Base de Datos Funcional
- **SQLite**: Base de datos embebida, sin configuración externa requerida
- Archivo: `repositorio.db` (creado automáticamente)
- Tabla `proyectos` con los siguientes campos:
  - ID (autoincremental)
  - Nombre del Proyecto
  - Autores
  - Año
  - Categoría
  - Resumen/Descripción
  - Palabras Clave
  - Enlaces/Recursos
  - Correo de Contacto
  - Fecha de Registro (timestamp)

### Funcionalidades
1. **Agregar Proyectos**: Formulario completo con validación
2. **Listar Proyectos**: Visualización dinámica desde BD
3. **Categorización**: Badge con tipo de proyecto
4. **Contacto**: Información de autor para consultas
5. **Gestión Completa**: CRUD con operaciones de base de datos

## 🏗️ Estructura del Proyecto

```
src/main/
├── java/com/repositorio/
│   ├── modelo/
│   │   └── Proyecto.java           # Clase modelo de Proyecto
│   ├── bd/
│   │   └── ConexionBD.java         # Gestión de conexión SQLite
│   └── servlet/
│       └── ListarProyectosServlet.java  # Servlet para listar
├── webapp/
│   ├── proyectos.jsp               # Página con formulario y listado
│   ├── guardar-proyecto.jsp        # Procesa y guarda en BD
│   ├── index.jsp                   # Inicio
│   ├── bibliograficos.jsp          # Material bibliográfico
│   └── style.css                   # Estilos
```

## 🚀 Compilación y Ejecución

### Compilar
```bash
mvn clean compile
```

### Empaquetar
```bash
mvn clean package
```

### Ejecutar con Tomcat (si está configurado)
```bash
mvn clean tomcat7:run
```

## 📦 Dependencias

- **SQLite JDBC**: `org.xerial:sqlite-jdbc:3.44.0.0`
- **Jakarta Servlet API**: 6.0.0
- **Jakarta JSP API**: 3.1.0
- **JSTL**: 3.0.1

## 🗄️ Base de Datos

### Ubicación
El archivo `repositorio.db` se crea automáticamente en el directorio raíz del proyecto.

### Inicialización
La base de datos se inicializa automáticamente en la primera carga:
1. Al cargar `proyectos.jsp`
2. Al procesar `guardar-proyecto.jsp`
3. Al acceder al servlet `ListarProyectosServlet`

### Tabla Proyectos
```sql
CREATE TABLE proyectos (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  nombre_proyecto TEXT NOT NULL,
  autores TEXT NOT NULL,
  anio INTEGER NOT NULL,
  categoria TEXT NOT NULL,
  resumen TEXT NOT NULL,
  palabras_clave TEXT,
  enlaces TEXT,
  correo_contacto TEXT NOT NULL,
  fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)
```

## 📝 Cómo Usar

### 1. Agregar un Proyecto
- Ir a la página **Proyectos Comunitarios**
- Completar el formulario con:
  - Nombre del proyecto
  - Autor(es)
  - Año
  - Categoría (Proyecto, Tesis, Investigación, etc.)
  - Resumen
  - Palabras clave
  - Enlaces (opcional)
  - Correo de contacto
- Hacer clic en **"Guardar Proyecto"**
- Recibirá confirmación y se guardará en la BD

### 2. Ver Proyectos Registrados
- La lista se muestra automáticamente en la misma página
- Ordenada por fecha más reciente
- Incluye toda la información del proyecto
- Muestra contador total de proyectos

### 3. Enlace a Recursos
- Si el proyecto tiene un enlace, aparecerá como **"🔗 Ver Recurso"**
- Se abre en una nueva pestaña

## 🎨 Interfaz

### Formulario
- Campos validados (requeridos/opcionales)
- Estilos responsivos
- Efectos visuales en inputs
- Botones de guardar y limpiar

### Listado
- Badges de categoría en naranja
- Información completa de cada proyecto
- Fecha de registro
- Enlaces a recursos

## 🔧 Operaciones de BD Disponibles

### Métodos en `ConexionBD.java`
- `inicializarBD()` - Inicializar BD y tabla
- `insertarProyecto(Proyecto)` - Agregar proyecto
- `obtenerTodosLosProyectos()` - Listar todos
- `obtenerProyectoPorId(int)` - Buscar por ID
- `actualizarProyecto(Proyecto)` - Editar proyecto
- `eliminarProyecto(int)` - Eliminar proyecto
- `obtenerProyectosPorCategoria(String)` - Filtrar por categoría

## 📱 Responsive Design
- Formulario adaptable a móviles
- Botones responsive
- Inputs accesibles

## 🛡️ Seguridad
- Validación de entrada en servidor
- Prepared statements (prevención de SQL injection)
- Campos requeridos validados
- Manejo de errores

## 📊 Zona Horaria
- Configurado para **América/Caracas** (Venezuela)
- Se muestra en footer de cada página

---

**Versión**: 1.0  
**Última actualización**: 20 de noviembre de 2025
