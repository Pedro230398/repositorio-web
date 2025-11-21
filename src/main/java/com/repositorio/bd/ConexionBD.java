package com.repositorio.bd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.repositorio.modelo.Proyecto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConexionBD {
    private static final String URL_BD = "jdbc:sqlite:repositorio.db";
    private static Connection conexion;
    
    // Inicializar la base de datos
    public static void inicializarBD() {
        try {
            // Cargar el driver de SQLite
            Class.forName("org.sqlite.JDBC");
            
            // Crear conexión
            conexion = DriverManager.getConnection(URL_BD);
            
            // Crear tabla si no existe
            crearTablaProyectos();
            
            System.out.println("Base de datos inicializada correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver de SQLite: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }
    
    // Obtener conexión
    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName("org.sqlite.JDBC");
                conexion = DriverManager.getConnection(URL_BD);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al obtener conexión: " + e.getMessage());
        }
        return conexion;
    }
    
    // Crear tabla de proyectos
    private static void crearTablaProyectos() {
        String sql = "CREATE TABLE IF NOT EXISTS proyectos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre_proyecto TEXT NOT NULL," +
                "autores TEXT NOT NULL," +
                "anio INTEGER NOT NULL," +
                "categoria TEXT NOT NULL," +
                "resumen TEXT NOT NULL," +
                "palabras_clave TEXT," +
                "enlaces TEXT," +
                "correo_contacto TEXT NOT NULL," +
                "fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
        
        try (Statement stmt = getConexion().createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'proyectos' creada o ya existe");
        } catch (SQLException e) {
            System.err.println("Error al crear tabla: " + e.getMessage());
        }
    }
    
    // Insertar un nuevo proyecto
    public static boolean insertarProyecto(Proyecto proyecto) {
        String sql = "INSERT INTO proyectos (nombre_proyecto, autores, anio, categoria, " +
                "resumen, palabras_clave, enlaces, correo_contacto) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = getConexion().prepareStatement(sql)) {
            pstmt.setString(1, proyecto.getNombreProyecto());
            pstmt.setString(2, proyecto.getAutores());
            pstmt.setInt(3, proyecto.getAnio());
            pstmt.setString(4, proyecto.getCategoria());
            pstmt.setString(5, proyecto.getResumen());
            pstmt.setString(6, proyecto.getPalabrasClave());
            pstmt.setString(7, proyecto.getEnlaces());
            pstmt.setString(8, proyecto.getCorreoContacto());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar proyecto: " + e.getMessage());
            return false;
        }
    }
    
    // Obtener todos los proyectos
    public static List<Proyecto> obtenerTodosLosProyectos() {
        List<Proyecto> proyectos = new ArrayList<>();
        String sql = "SELECT * FROM proyectos ORDER BY fecha_registro DESC";
        
        try (Statement stmt = getConexion().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Proyecto proyecto = new Proyecto();
                proyecto.setId(rs.getInt("id"));
                proyecto.setNombreProyecto(rs.getString("nombre_proyecto"));
                proyecto.setAutores(rs.getString("autores"));
                proyecto.setAnio(rs.getInt("anio"));
                proyecto.setCategoria(rs.getString("categoria"));
                proyecto.setResumen(rs.getString("resumen"));
                proyecto.setPalabrasClave(rs.getString("palabras_clave"));
                proyecto.setEnlaces(rs.getString("enlaces"));
                proyecto.setCorreoContacto(rs.getString("correo_contacto"));
                
                String fechaStr = rs.getString("fecha_registro");
                if (fechaStr != null) {
                    LocalDateTime fecha = LocalDateTime.parse(fechaStr, 
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    proyecto.setFechaRegistro(fecha);
                }
                
                proyectos.add(proyecto);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener proyectos: " + e.getMessage());
        }
        
        return proyectos;
    }
    
    // Obtener proyecto por ID
    public static Proyecto obtenerProyectoPorId(int id) {
        String sql = "SELECT * FROM proyectos WHERE id = ?";
        
        try (PreparedStatement pstmt = getConexion().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Proyecto proyecto = new Proyecto();
                proyecto.setId(rs.getInt("id"));
                proyecto.setNombreProyecto(rs.getString("nombre_proyecto"));
                proyecto.setAutores(rs.getString("autores"));
                proyecto.setAnio(rs.getInt("anio"));
                proyecto.setCategoria(rs.getString("categoria"));
                proyecto.setResumen(rs.getString("resumen"));
                proyecto.setPalabrasClave(rs.getString("palabras_clave"));
                proyecto.setEnlaces(rs.getString("enlaces"));
                proyecto.setCorreoContacto(rs.getString("correo_contacto"));
                
                String fechaStr = rs.getString("fecha_registro");
                if (fechaStr != null) {
                    LocalDateTime fecha = LocalDateTime.parse(fechaStr, 
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    proyecto.setFechaRegistro(fecha);
                }
                
                return proyecto;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener proyecto: " + e.getMessage());
        }
        
        return null;
    }
    
    // Actualizar un proyecto
    public static boolean actualizarProyecto(Proyecto proyecto) {
        String sql = "UPDATE proyectos SET nombre_proyecto = ?, autores = ?, anio = ?, " +
                "categoria = ?, resumen = ?, palabras_clave = ?, enlaces = ?, correo_contacto = ? " +
                "WHERE id = ?";
        
        try (PreparedStatement pstmt = getConexion().prepareStatement(sql)) {
            pstmt.setString(1, proyecto.getNombreProyecto());
            pstmt.setString(2, proyecto.getAutores());
            pstmt.setInt(3, proyecto.getAnio());
            pstmt.setString(4, proyecto.getCategoria());
            pstmt.setString(5, proyecto.getResumen());
            pstmt.setString(6, proyecto.getPalabrasClave());
            pstmt.setString(7, proyecto.getEnlaces());
            pstmt.setString(8, proyecto.getCorreoContacto());
            pstmt.setInt(9, proyecto.getId());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar proyecto: " + e.getMessage());
            return false;
        }
    }
    
    // Eliminar un proyecto
    public static boolean eliminarProyecto(int id) {
        String sql = "DELETE FROM proyectos WHERE id = ?";
        
        try (PreparedStatement pstmt = getConexion().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar proyecto: " + e.getMessage());
            return false;
        }
    }
    
    // Buscar proyectos por categoría
    public static List<Proyecto> obtenerProyectosPorCategoria(String categoria) {
        List<Proyecto> proyectos = new ArrayList<>();
        String sql = "SELECT * FROM proyectos WHERE categoria = ? ORDER BY fecha_registro DESC";
        
        try (PreparedStatement pstmt = getConexion().prepareStatement(sql)) {
            pstmt.setString(1, categoria);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Proyecto proyecto = new Proyecto();
                proyecto.setId(rs.getInt("id"));
                proyecto.setNombreProyecto(rs.getString("nombre_proyecto"));
                proyecto.setAutores(rs.getString("autores"));
                proyecto.setAnio(rs.getInt("anio"));
                proyecto.setCategoria(rs.getString("categoria"));
                proyecto.setResumen(rs.getString("resumen"));
                proyecto.setPalabrasClave(rs.getString("palabras_clave"));
                proyecto.setEnlaces(rs.getString("enlaces"));
                proyecto.setCorreoContacto(rs.getString("correo_contacto"));
                
                String fechaStr = rs.getString("fecha_registro");
                if (fechaStr != null) {
                    LocalDateTime fecha = LocalDateTime.parse(fechaStr, 
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    proyecto.setFechaRegistro(fecha);
                }
                
                proyectos.add(proyecto);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener proyectos por categoría: " + e.getMessage());
        }
        
        return proyectos;
    }
    
    // Cerrar conexión
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
