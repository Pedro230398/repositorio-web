package com.repositorio.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Proyecto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String nombreProyecto;
    private String autores;
    private int anio;
    private String categoria;
    private String resumen;
    private String palabrasClave;
    private String enlaces;
    private String correoContacto;
    private LocalDateTime fechaRegistro;
    
    // Constructor vacío
    public Proyecto() {
    }
    
    // Constructor con parámetros
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
        this.fechaRegistro = LocalDateTime.now();
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombreProyecto() {
        return nombreProyecto;
    }
    
    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }
    
    public String getAutores() {
        return autores;
    }
    
    public void setAutores(String autores) {
        this.autores = autores;
    }
    
    public int getAnio() {
        return anio;
    }
    
    public void setAnio(int anio) {
        this.anio = anio;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public String getResumen() {
        return resumen;
    }
    
    public void setResumen(String resumen) {
        this.resumen = resumen;
    }
    
    public String getPalabrasClave() {
        return palabrasClave;
    }
    
    public void setPalabrasClave(String palabrasClave) {
        this.palabrasClave = palabrasClave;
    }
    
    public String getEnlaces() {
        return enlaces;
    }
    
    public void setEnlaces(String enlaces) {
        this.enlaces = enlaces;
    }
    
    public String getCorreoContacto() {
        return correoContacto;
    }
    
    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }
    
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
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
