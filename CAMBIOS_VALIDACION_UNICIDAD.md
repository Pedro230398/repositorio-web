# 🔄 CAMBIOS REALIZADOS - Validación de Unicidad

## Fecha: 21 de noviembre de 2025

---

## ✅ Cambios Implementados

### 1. **Validación de Unicidad en Base de Datos**

#### Campos con restricción de NO repetición:

**a) Nombre del Proyecto**
- ✅ No pueden existir dos proyectos con el mismo nombre
- ✅ Mensaje de error: "El nombre del proyecto ya existe en la base de datos"

**b) Año del Proyecto**
- ✅ No puede haber dos proyectos del mismo año
- ✅ Mensaje de error: "Ya existe un proyecto registrado para el año [AÑO]"

**c) Enlace de Recurso**
- ✅ No pueden repetirse enlaces (si se proporciona)
- ✅ Los enlaces vacíos/opcionales pueden no incluirse sin problema
- ✅ Mensaje de error: "El enlace de recurso ya está registrado en otro proyecto"

---

## 🔧 Cambios Técnicos

### Archivo: `ConexionBD.java`

**Nuevos métodos de validación:**

```java
// Verificar si un nombre de proyecto ya existe
public static boolean existeNombreProyecto(String nombre)

// Verificar si un año ya existe
public static boolean existeAnio(int anio)

// Verificar si un enlace ya existe
public static boolean existeEnlace(String enlace)
```

**Método actualizado:**

```java
// insertarProyecto() ahora valida ANTES de guardar
```

### Archivo: `guardar-proyecto.jsp`

**Validaciones agregadas:**
- Verifica nombre único
- Verifica año único
- Verifica enlace único
- Mensajes de error específicos para cada caso

### Archivo: `proyectos.jsp`

**Cambios de categoría:**
- ❌ Eliminada opción: "Tesis"
- ✅ Nueva opción: "Trabajo Especial Grado"

---

## 📋 Opciones de Categoría (Actualizadas)

```
- Proyecto
- Trabajo Especial Grado (antes "Tesis")
- Investigación
- Iniciativa Comunitaria
- Otro
```

---

## 🧪 Cómo Funciona

### Flujo de Guardado:

```
1. Usuario llena formulario
   ↓
2. Envía a guardar-proyecto.jsp
   ↓
3. Valida campos obligatorios
   ↓
4. Valida nombre ÚNICO → ConexionBD.existeNombreProyecto()
   ↓
5. Valida año ÚNICO → ConexionBD.existeAnio()
   ↓
6. Valida enlace ÚNICO → ConexionBD.existeEnlace()
   ↓
7. Si TODO es válido → Guarda en BD
   ↓
8. Si hay error → Muestra mensaje específico
```

---

## ✨ Ejemplos de Validación

### Ejemplo 1: Nombre duplicado
```
Usuario intenta guardar:
- Nombre: "Proyecto A"
- Año: 2025
- Enlace: (vacío)

Si ya existe un proyecto llamado "Proyecto A" → ❌ ERROR
Mensaje: "El nombre del proyecto 'Proyecto A' ya existe"
```

### Ejemplo 2: Año duplicado
```
Usuario intenta guardar:
- Nombre: "Proyecto B"
- Año: 2024
- Enlace: (vacío)

Si ya existe un proyecto del año 2024 → ❌ ERROR
Mensaje: "Ya existe un proyecto registrado para el año 2024"
```

### Ejemplo 3: Enlace duplicado
```
Usuario intenta guardar:
- Nombre: "Proyecto C"
- Año: 2025
- Enlace: "https://ejemplo.com"

Si el enlace ya existe en otro proyecto → ❌ ERROR
Mensaje: "El enlace de recurso ya está registrado en otro proyecto"
```

### Ejemplo 4: Todo válido ✅
```
Usuario intenta guardar:
- Nombre: "Nuevo Proyecto" (único)
- Año: 2023 (único)
- Enlace: "https://nuevo.com" (único)

TODO OK → ✅ SE GUARDA EN BD
Mensaje: "Proyecto guardado exitosamente"
```

---

## 🔍 Casos Especiales

### Enlace vacío
- ✅ Permite múltiples proyectos SIN enlace
- ✅ Solo valida si el enlace está NO vacío
- ✅ La validación es inteligente

### Edición futura
- Si se implementa edición, se debe permitir que un proyecto
  mantenga sus valores actuales sin errores de duplicación

---

## 📊 Compilación

```
✅ mvn clean compile    → BUILD SUCCESS
✅ mvn clean package    → BUILD SUCCESS
✅ WAR actualizado      → 16.8 MB
```

---

## 📝 Resumen de Cambios

| Aspecto | Antes | Ahora |
|---------|-------|-------|
| Nombre Proyecto | Puede repetirse | ❌ NO PUEDE REPETIRSE |
| Año | Puede repetirse | ❌ NO PUEDE REPETIRSE |
| Enlace | Puede repetirse | ❌ NO PUEDE REPETIRSE |
| Categoría "Tesis" | Disponible | ✅ Cambió a "Trabajo Especial Grado" |
| Validación | Solo campos requeridos | Unicidad + campos requeridos |

---

## 🚀 Despliegue

El WAR actualizado está en: `target/mi-repositorio-web-1.0.war`

Pasos para desplegar:
1. Copiar WAR a `$CATALINA_HOME/webapps/`
2. Reiniciar Tomcat
3. Los cambios serán efectivos inmediatamente

---

## ✅ Verificación

Para verificar que funciona:

1. Agregar un proyecto con nombre "Test1", año 2025
2. Intentar agregar otro con nombre "Test1" → ❌ Debe fallar
3. Intentar agregar otro con año 2025 → ❌ Debe fallar
4. Intentar agregar otro con mismo enlace → ❌ Debe fallar
5. Verificar que la categoría "Trabajo Especial Grado" aparece

---

**Versión**: 1.1  
**Cambios**: Validación de unicidad + cambio de categoría  
**Status**: ✅ COMPILADO Y LISTO
