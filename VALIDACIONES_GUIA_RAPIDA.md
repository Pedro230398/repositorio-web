# ✅ VALIDACIONES DE UNICIDAD - Guía Rápida

## Campos que NO pueden repetirse

### 1. **Nombre del Proyecto** 🔴
- Cada proyecto debe tener un nombre ÚNICO
- No puede haber dos proyectos con el mismo nombre
- Ejemplo:
  - ✅ "Proyecto A" (1er vez) → GUARDADO
  - ❌ "Proyecto A" (2da vez) → ERROR: "El nombre del proyecto 'Proyecto A' ya existe"

### 2. **Año del Proyecto** 📅
- Cada año debe ser ÚNICO
- No puede haber dos proyectos del mismo año
- Ejemplo:
  - ✅ Año 2025 (1er proyecto) → GUARDADO
  - ❌ Año 2025 (2do proyecto) → ERROR: "Ya existe un proyecto registrado para el año 2025"

### 3. **Enlace de Recurso** 🔗
- Si proporciona un enlace, DEBE ser ÚNICO
- Dos proyectos NO pueden compartir el mismo enlace
- Ejemplo:
  - ✅ "https://ejemplo.com" (1er proyecto) → GUARDADO
  - ❌ "https://ejemplo.com" (2do proyecto) → ERROR: "El enlace ya está registrado"

---

## Campos que SÍ pueden repetirse

### ✅ Permitido:
- **Autores** - Múltiples proyectos del mismo autor
- **Categoría** - Múltiples proyectos de la misma categoría
- **Resumen** - Resúmenes idénticos
- **Palabras Clave** - Iguales palabras clave
- **Correo de Contacto** - Mismo email en varios proyectos

---

## Caso especial: Enlace vacío

- ✅ Permite múltiples proyectos SIN enlace
- ✅ Solo valida si el enlace está completo
- ✅ Enlace vacío ≠ Enlace duplicado

Ejemplo:
- Proyecto 1: Enlace vacío ✅ OK
- Proyecto 2: Enlace vacío ✅ OK
- Proyecto 3: Enlace vacío ✅ OK
- (No hay conflicto)

---

## Flujo de Validación

```
1. Usuario completa formulario
        ↓
2. Envía para guardar
        ↓
3. ¿Nombre ÚNICO?
   NO → ❌ Mostrar error
   SÍ → Continuar
        ↓
4. ¿Año ÚNICO?
   NO → ❌ Mostrar error
   SÍ → Continuar
        ↓
5. ¿Enlace ÚNICO? (si no está vacío)
   NO → ❌ Mostrar error
   SÍ → Continuar
        ↓
6. ✅ GUARDAR EN BASE DE DATOS
```

---

## Mensajes de Error

| Validación | Mensaje |
|-----------|---------|
| Nombre duplicado | "El nombre del proyecto '[NOMBRE]' ya existe en la base de datos" |
| Año duplicado | "Ya existe un proyecto registrado para el año [AÑO]" |
| Enlace duplicado | "El enlace de recurso ya está registrado en otro proyecto" |

---

## Ejemplo Completo

### Guardado 1 (exitoso):
```
Nombre: Proyecto A ✅ (no existe)
Año: 2025 ✅ (no existe)
Enlace: https://ejemplo1.com ✅ (no existe)
→ GUARDADO EXITOSAMENTE
```

### Guardado 2 (error - nombre):
```
Nombre: Proyecto A ❌ (YA EXISTE)
Año: 2024 ✅ (es diferente)
Enlace: https://ejemplo2.com ✅ (es diferente)
→ ERROR: Nombre duplicado
```

### Guardado 3 (error - año):
```
Nombre: Proyecto B ✅ (es diferente)
Año: 2025 ❌ (YA EXISTE)
Enlace: https://ejemplo3.com ✅ (es diferente)
→ ERROR: Año duplicado
```

### Guardado 4 (error - enlace):
```
Nombre: Proyecto C ✅ (es diferente)
Año: 2023 ✅ (es diferente)
Enlace: https://ejemplo1.com ❌ (YA EXISTE)
→ ERROR: Enlace duplicado
```

### Guardado 5 (exitoso - sin enlace):
```
Nombre: Proyecto D ✅ (no existe)
Año: 2022 ✅ (no existe)
Enlace: (VACÍO) ✅ (no valida enlaces vacíos)
→ GUARDADO EXITOSAMENTE
```

---

## Notas Importantes

⚠️ **Orden de validación:**
1. Primero verifica nombre
2. Luego verifica año
3. Después verifica enlace

⚠️ **Si hay múltiples errores:**
- Se muestra el PRIMER error encontrado
- El usuario debe corregir y volver a intentar

⚠️ **Cambio de categoría:**
- "Tesis" fue reemplazada por "Trabajo Especial Grado"
- Proyectos antiguos con "Tesis" se mantienen
- Nuevos proyectos usan "Trabajo Especial Grado"

---

## Verificación

Para verificar que funciona correctamente:

```bash
1. Agregar: Proyecto 1, Año 2025, Enlace URL1
   → ✅ Debe guardar

2. Intentar agregar: Proyecto 1, Año 2026, Enlace URL2
   → ❌ Debe rechazar (nombre igual)

3. Intentar agregar: Proyecto 2, Año 2025, Enlace URL3
   → ❌ Debe rechazar (año igual)

4. Intentar agregar: Proyecto 3, Año 2026, Enlace URL1
   → ❌ Debe rechazar (enlace igual)

5. Agregar: Proyecto 3, Año 2026, Enlace URL3
   → ✅ Debe guardar (todo diferente)
```

---

**Versión**: 1.1  
**Fecha**: 21 de noviembre de 2025  
**Estado**: ✅ Validaciones activas
