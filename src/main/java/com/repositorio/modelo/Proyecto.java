package com.repositorio.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * CLASE: Proyecto
 * FUNCIÓN: Representar un proyecto comunitario con todos sus datos
 * DESCRIPCIÓN: Esta es la clase modelo que encapsula toda la información de un proyecto:
 *              - Información básica (nombre, autores, año)
 *              - Categorización (tipo de proyecto)
 *              - Documentación (resumen, palabras clave)
 *              - Enlaces y contacto (URLs y email)
 *              - Auditoría (fecha de registro automática)
 * IMPLEMENTS: Serializable - Permite serializar objetos para almacenamiento o transmisión
 */
public class Proyecto implements Serializable {
    // serialVersionUID: Identificador único para control de versiones al serializar
    private static final long serialVersionUID = 1L;
    
    // CAMPOS DE DATOS DEL PROYECTO
    // ID: Identificador único asignado por la BD (autoincremental)
    private int id;
    // Nombre del proyecto (campo obligatorio, debe ser único)
    private String nombreProyecto;
    // Nombres de los autores/creadores (campo obligatorio)
    private String autores;
    // Año de realización del proyecto (campo obligatorio, debe ser único)
    private int anio;
    // Categoría del proyecto (Proyecto, Trabajo Especial Grado, Investigación, etc.)
    private String categoria;
    // Descripción detallada del proyecto (campo obligatorio)
    private String resumen;
    // Palabras clave para búsqueda y clasificación (opcional)
    private String palabrasClave;
    // URLs de recursos relacionados (campo opcional, debe ser único si no está vacío)
    private String enlaces;
    // Email de contacto del autor (campo obligatorio)
    private String correoContacto;
    // Fecha y hora de registro en la BD (se asigna automáticamente)
    private LocalDateTime fechaRegistro;
    
    /**
     * CONSTRUCTOR: Constructor vacío (sin parámetros)
     * FUNCIÓN: Crear instancia de Proyecto sin inicializar datos
     * UTILIDAD: Usado por frameworks como Hibernate, JSP, y conversión de JSON a objeto
     */
    public Proyecto() {
    }
    
    /**
     * CONSTRUCTOR: Constructor con parámetros
     * FUNCIÓN: Crear instancia de Proyecto con datos iniciales
     * PARÁMETROS: Todos los campos principales (excepto id y fecha que se asignan en BD)
     * ASIGNACIÓN AUTOMÁTICA: fechaRegistro se asigna con la hora actual (LocalDateTime.now())
     */
    public Proyecto(String nombreProyecto, String autores, int anio, String categoria, 
                    String resumen, String palabrasClave, String enlaces, String correoContacto) {
        this.nombreProyecto = nombreProyecto;
        this.autores = autores;
        this.anio = anio;
        this.categoria = categoria;
        this.resumen = resumen;
        this.palabrasClave = palabrasClave;
        this.enlaces = enlaces;
        this.correoContacto = correoContacto;
        // Asignar fecha actual automáticamente
        this.fechaRegistro = LocalDateTime.now();
    }
    
    // ========== GETTER Y SETTER: id ==========
    // FUNCIÓN: Obtener el identificador único del proyecto
    public int getId() {
        return id;
    }
    
    // FUNCIÓN: Asignar el identificador único (normalmente lo hace la BD)
    public void setId(int id) {
        this.id = id;
    }
    
    // ========== GETTER Y SETTER: nombreProyecto ==========
    // FUNCIÓN: Obtener el nombre del proyecto
    public String getNombreProyecto() {
        return nombreProyecto;
    }
    
    // FUNCIÓN: Asignar el nombre del proyecto (debe ser único)
    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }
    
    // ========== GETTER Y SETTER: autores ==========
    // FUNCIÓN: Obtener los nombres de los autores
    public String getAutores() {
        return autores;
    }
    
    // FUNCIÓN: Asignar los nombres de los autores
    public void setAutores(String autores) {
        this.autores = autores;
    }
    
    // ========== GETTER Y SETTER: anio ==========
    // FUNCIÓN: Obtener el año del proyecto
    public int getAnio() {
        return anio;
    }
    
    // FUNCIÓN: Asignar el año del proyecto (debe ser único)
    public void setAnio(int anio) {
        this.anio = anio;
    }
    
    // ========== GETTER Y SETTER: categoria ==========
    // FUNCIÓN: Obtener la categoría del proyecto
    public String getCategoria() {
        return categoria;
    }
    
    // FUNCIÓN: Asignar la categoría (Proyecto, Trabajo Especial Grado, Investigación, etc.)
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    // ========== GETTER Y SETTER: resumen ==========
    // FUNCIÓN: Obtener la descripción detallada del proyecto
    public String getResumen() {
        return resumen;
    }
    
    // FUNCIÓN: Asignar la descripción detallada del proyecto
    public void setResumen(String resumen) {
        this.resumen = resumen;
    }
    
    // ========== GETTER Y SETTER: palabrasClave ==========
    // FUNCIÓN: Obtener las palabras clave para búsqueda
    public String getPalabrasClave() {
        return palabrasClave;
    }
    
    // FUNCIÓN: Asignar palabras clave para indexación y búsqueda
    public void setPalabrasClave(String palabrasClave) {
        this.palabrasClave = palabrasClave;
    }
    
    // ========== GETTER Y SETTER: enlaces ==========
    // FUNCIÓN: Obtener las URLs de recursos relacionados
    public String getEnlaces() {
        return enlaces;
    }
    
    // FUNCIÓN: Asignar URLs de recursos (debe ser único si no está vacío)
    public void setEnlaces(String enlaces) {
        this.enlaces = enlaces;
    }
    
    // ========== GETTER Y SETTER: correoContacto ==========
    // FUNCIÓN: Obtener el email de contacto del autor
    public String getCorreoContacto() {
        return correoContacto;
    }
    
    // FUNCIÓN: Asignar el email de contacto
    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }
    
    // ========== GETTER Y SETTER: fechaRegistro ==========
    // FUNCIÓN: Obtener la fecha y hora de registro en la BD
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    
    // FUNCIÓN: Asignar la fecha de registro (normalmente asignada automáticamente por la BD)
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    /**
     * MÉTODO: toString()
     * FUNCIÓN: Convertir el objeto Proyecto a una representación en texto
     * UTILIDAD: Útil para debugging, logging y visualización rápida del contenido
     * RETORNA: String con formato legible mostrando todos los campos
     */
    @Override
    public String toString() {
        return "Proyecto{" +
                "id=" + id +
                ", nombreProyecto='" + nombreProyecto + '\'' +
                ", autores='" + autores + '\'' +
                ", anio=" + anio +
                ", categoria='" + categoria + '\'' +
                ", resumen='" + resumen + '\'' +
                ", palabrasClave='" + palabrasClave + '\'' +
                ", enlaces='" + enlaces + '\'' +
                ", correoContacto='" + correoContacto + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}
