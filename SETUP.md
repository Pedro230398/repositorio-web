# 🚀 GUÍA DE CONFIGURACIÓN - Base de Datos de Proyectos

## Requisitos Previos

- **Java JDK 17+** (verificar con `java -version`)
- **Maven 3.6+** (verificar con `mvn --version`)
- **Tomcat 10+** (opcional, para despliegue)

## ✅ Paso 1: Verificar Instalación

```bash
# Verificar Java
java -version

# Verificar Maven
mvn --version
```

## 🔧 Paso 2: Compilar el Proyecto

```bash
# Navegar al directorio
cd "c:\Users\Serge\Documents\documentos ubv\Osmery Navarro\Html\repositorio-web"

# Compilar
mvn clean compile
```

**Resultado esperado**: `BUILD SUCCESS`

## 📦 Paso 3: Empaquetar (Crear WAR)

```bash
mvn clean package -DskipTests
```

El archivo WAR se creará en: `target/mi-repositorio-web-1.0.war`

## 🎯 Paso 4: Desplegar

### Opción A: Con Tomcat (Recomendado)

1. Copiar el WAR a `$CATALINA_HOME/webapps/`
2. Iniciar Tomcat
3. Acceder a: `http://localhost:8080/mi-repositorio-web-1.0`

### Opción B: Con Maven (Desarrollo)

Si tienes configurado un plugin Tomcat:
```bash
mvn tomcat7:run
# O según tu versión de Tomcat
```

### Opción C: Con Java Directo

```bash
java -jar target/dependency/webapp-runner.jar target/mi-repositorio-web-1.0.war
```

## 🗄️ Paso 5: Base de Datos

### Inicialización Automática

La base de datos se crea **automáticamente** la primera vez que:
- Accedes a `proyectos.jsp`
- Guardas un proyecto
- Accedes al servlet

### Archivo de Base de Datos

**Ubicación**: `repositorio.db` (en la raíz del proyecto o donde se ejecute)

**Tamaño inicial**: ~50 KB (crece con datos)

### Verificar BD (Opcional)

Si deseas explorar la BD directamente:

1. Descargar [SQLite Studio](https://sqlitestudio.pl/) o similar
2. Abrir archivo `repositorio.db`
3. Ver tabla `proyectos`

## 🌐 Paso 6: Acceso a la Aplicación

Una vez desplegada, acceder a:

- **Inicio**: `/index.jsp`
- **Proyectos**: `/proyectos.jsp` ← **AQUÍ VA EL FORMULARIO**
- **Bibliografía**: `/bibliograficos.jsp`
- **Guardar Proyecto**: `/guardar-proyecto.jsp` (POST automático)

## 📝 Paso 7: Usar el Formulario

1. Ir a **Proyectos Comunitarios**
2. Completar formulario:
   - ✓ Nombre del Proyecto (obligatorio)
   - ✓ Autor(es) (obligatorio)
   - ✓ Año (obligatorio)
   - ✓ Categoría (obligatorio)
   - ✓ Resumen (obligatorio)
   - ○ Palabras Clave (opcional)
   - ○ Enlaces (opcional)
   - ✓ Correo de Contacto (obligatorio)
3. Clic en **"Guardar Proyecto"**
4. Confirmación → Los datos se guardan en BD

## ✨ Características Principales

### Campos Soportados

```
| Campo                 | Tipo       | Requerido | Descripción                    |
|----------------------|------------|-----------|--------------------------------|
| Nombre del Proyecto  | Texto      | Sí        | Título del proyecto            |
| Autores              | Texto      | Sí        | Uno o más autores              |
| Año                  | Número     | Sí        | Año de realización (2000-2100) |
| Categoría            | Selección  | Sí        | Proyecto/Tesis/Otro            |
| Resumen              | Texto Lg   | Sí        | Descripción completa           |
| Palabras Clave       | Texto      | No        | Separadas por comas            |
| Enlaces              | URL        | No        | Link a recursos                |
| Correo de Contacto   | Email      | Sí        | Para comunicaciones            |
```

### Validaciones

- ✓ Campos obligatorios verificados
- ✓ Formato de email validado
- ✓ URL validada
- ✓ Año entre 2000-2100
- ✓ Proteción contra SQL injection (Prepared Statements)

## 🐛 Solución de Problemas

### Error: "No such file or directory"
```bash
# Asegúrate de estar en el directorio correcto
cd "c:\Users\Serge\Documents\documentos ubv\Osmery Navarro\Html\repositorio-web"
```

### Error: "Class not found: org.sqlite.JDBC"
```bash
# Ejecutar Maven para descargar dependencias
mvn dependency:resolve
mvn clean compile
```

### Error: "Port 8080 already in use"
```bash
# Cambiar puerto en configuración de Tomcat o:
netstat -ano | findstr :8080  # Ver qué usa el puerto
```

### BD no se crea
1. Verificar permisos de escritura en el directorio
2. Verificar logs en `target/logs/` o consola
3. Ejecutar `mvn clean compile` nuevamente

## 📊 Estructura de Directorios Generada

```
repositorio-web/
├── src/
│   ├── main/
│   │   ├── java/com/repositorio/
│   │   │   ├── modelo/Proyecto.java
│   │   │   ├── bd/ConexionBD.java
│   │   │   └── servlet/ListarProyectosServlet.java
│   │   └── webapp/
│   │       ├── proyectos.jsp
│   │       ├── guardar-proyecto.jsp
│   │       ├── index.jsp
│   │       ├── bibliograficos.jsp
│   │       └── style.css
├── target/
│   ├── classes/
│   └── mi-repositorio-web-1.0.war
├── pom.xml
├── DATABASE_README.md
├── SETUP.md (este archivo)
└── repositorio.db (creado automáticamente)
```

## 🔐 Backup de Base de Datos

Para hacer backup:
```bash
# Windows
copy repositorio.db repositorio.db.backup

# Linux/Mac
cp repositorio.db repositorio.db.backup
```

Para restaurar:
```bash
# Windows
copy repositorio.db.backup repositorio.db

# Linux/Mac
cp repositorio.db.backup repositorio.db
```

## 📈 Monitoreo

### Ver cantidad de proyectos (en SQLite)
```sql
SELECT COUNT(*) as total FROM proyectos;
```

### Ver últimos proyectos registrados
```sql
SELECT nombre_proyecto, autores, fecha_registro 
FROM proyectos 
ORDER BY fecha_registro DESC 
LIMIT 10;
```

## 🚀 Próximos Pasos (Futuras Mejoras)

- [ ] Agregar edición de proyectos
- [ ] Agregar eliminación de proyectos
- [ ] Filtrar por categoría
- [ ] Buscar proyectos
- [ ] Exportar a PDF/CSV
- [ ] Autenticación de usuarios
- [ ] Dashboard de estadísticas

## 📞 Soporte

Para problemas o dudas:
1. Revisar este documento
2. Verificar logs del servidor
3. Consultar DATABASE_README.md

---

**Versión**: 1.0  
**Última actualización**: 20 de noviembre de 2025  
**Estado**: ✅ FUNCIONAL Y LISTO PARA USAR
