package com.repositorio.bd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.repositorio.modelo.Proyecto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * CLASE: ConexionBD
 * FUNCIÓN: Gestionar la conexión a la base de datos SQLite y realizar
 * operaciones CRUD
 * DESCRIPCIÓN: Esta clase maneja toda la comunicación con la base de datos,
 * incluyendo:
 * - Inicialización de BD
 * - Conexión a SQLite
 * - Creación de tabla
 * - Operaciones de lectura, escritura, actualización y eliminación
 * - Validaciones de unicidad
 */
public class ConexionBD {
    // VARIABLE ESTÁTICA: URL de conexión a la BD SQLite
    // La BD se almacena en un archivo local llamado "repositorio.db"
    private static final String URL_BD = "jdbc:sqlite:repositorio.db";

    // VARIABLE ESTÁTICA: Conexión a la BD que se mantiene durante toda la sesión
    private static Connection conexion;

    /**
     * MÉTODO: inicializarBD()
     * FUNCIÓN: Inicializar la base de datos en la primera ejecución
     * PROCESO:
     * 1. Carga el driver JDBC de SQLite
     * 2. Establece la conexión a la BD
     * 3. Crea la tabla si no existe
     */
    public static void inicializarBD() {
        try {
            // Cargar el driver de SQLite
            // Este paso es necesario para que Java reconozca SQLite
            Class.forName("org.sqlite.JDBC");

            // Crear conexión con la BD
            // Si el archivo repositorio.db no existe, SQLite lo crea automáticamente
            conexion = DriverManager.getConnection(URL_BD);

            // Crear tabla de proyectos (si no existe)
            crearTablaProyectos();

            System.out.println("Base de datos inicializada correctamente");
        } catch (ClassNotFoundException e) {
            // Error si no encuentra el driver de SQLite
            System.err.println("Error al cargar el driver de SQLite: " + e.getMessage());
        } catch (SQLException e) {
            // Error de conexión a la BD
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    /**
     * MÉTODO: getConexion()
     * FUNCIÓN: Obtener la conexión actual a la BD
     * VERIFICACIONES:
     * - Si la conexión es nula, crea una nueva
     * - Si la conexión está cerrada, crea una nueva
     * RETORNA: Connection - La conexión activa a la BD
     */
    public static Connection getConexion() {
        try {
            // Verificar si la conexión existe y está abierta
            if (conexion == null || conexion.isClosed()) {
                // Recargar driver
                Class.forName("org.sqlite.JDBC");
                // Crear nueva conexión
                conexion = DriverManager.getConnection(URL_BD);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al obtener conexión: " + e.getMessage());
        }
        return conexion;
    }

    /**
     * MÉTODO PRIVADO: crearTablaProyectos()
     * FUNCIÓN: Crear la tabla "proyectos" en la BD (si no existe)
     * CAMPOS DE LA TABLA:
     * - id: Identificador único (autoincremental)
     * - nombre_proyecto: Nombre del proyecto (obligatorio)
     * - autores: Nombres de los autores (obligatorio)
     * - anio: Año del proyecto (obligatorio)
     * - categoria: Tipo de proyecto (obligatorio)
     * - resumen: Descripción del proyecto (obligatorio)
     * - palabras_clave: Palabras para búsqueda (opcional)
     * - enlaces: URLs de recursos (opcional)
     * - correo_contacto: Email del autor (obligatorio)
     * - fecha_registro: Timestamp de cuando se registró (automático)
     */
    private static void crearTablaProyectos() {
        // SQL para crear tabla
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
            // Ejecutar comando SQL
            stmt.execute(sql);
            System.out.println("Tabla 'proyectos' creada o ya existe");
        } catch (SQLException e) {
            System.err.println("Error al crear tabla: " + e.getMessage());
        }
    }

    /**
     * MÉTODO: existeNombreProyecto(String nombre)
     * FUNCIÓN: Validar que el nombre del proyecto sea ÚNICO (no exista otro igual)
     * PARÁMETRO: nombre - Nombre del proyecto a verificar
     * RETORNA: boolean - true si existe, false si no existe
     * VALIDACIÓN: Evita que se repitan nombres de proyectos
     */
    public static boolean existeNombreProyecto(String nombre) {
        // Query SQL que cuenta cuántos proyectos tienen ese nombre
        String sql = "SELECT COUNT(*) FROM proyectos WHERE nombre_proyecto = ?";

        try (PreparedStatement pstmt = getConexion().prepareStatement(sql)) {
            // ? es un parámetro seguro contra SQL injection
            pstmt.setString(1, nombre);
            // Ejecutar query
            ResultSet rs = pstmt.executeQuery();

            // Si encuentra registro, retorna true (existe)
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar nombre: " + e.getMessage());
        }

        return false; // No existe
    }

    /**
     * MÉTODO: existeAnio(int anio)
     * FUNCIÓN: Validar que el AÑO sea ÚNICO (no exista otro proyecto del mismo año)
     * PARÁMETRO: anio - Año a verificar
     * RETORNA: boolean - true si existe, false si no existe
     * VALIDACIÓN: Evita que se repitan años
     */
    public static boolean existeAnio(int anio) {
        // Query que cuenta proyectos del mismo año
        String sql = "SELECT COUNT(*) FROM proyectos WHERE anio = ?";

        try (PreparedStatement pstmt = getConexion().prepareStatement(sql)) {
            pstmt.setInt(1, anio);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar año: " + e.getMessage());
        }

        return false;
    }

    /**
     * MÉTODO: existeEnlace(String enlace)
     * FUNCIÓN: Validar que el ENLACE sea ÚNICO (no exista otro igual)
     * PARÁMETRO: enlace - URL a verificar
     * RETORNA: boolean - true si existe, false si no existe
     * ESPECIAL: Los enlaces vacíos SIEMPRE retornan false (permiten duplicados)
     * VALIDACIÓN: Evita que se repitan enlaces de recursos
     */
    public static boolean existeEnlace(String enlace) {
        // Si el enlace está vacío, permitir múltiples proyectos sin enlace
        if (enlace == null || enlace.isEmpty()) {
            return false; // No validar enlaces vacíos
        }

        // Query que cuenta proyectos con ese enlace
        String sql = "SELECT COUNT(*) FROM proyectos WHERE enlaces = ?";

        try (PreparedStatement pstmt = getConexion().prepareStatement(sql)) {
            pstmt.setString(1, enlace);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar enlace: " + e.getMessage());
        }

        return false;
    }

    /**
     * MÉTODO: existeProyectoDuplicado(String nombre, int anio, String enlace)
     * FUNCIÓN: Verificar si ya existe un proyecto con la misma combinación
     * (nombre, año, enlace). Sólo retorna true si los tres coinciden.
     * PARÁMETROS:
     * - nombre: nombre del proyecto (se compara tal cual)
     * - anio: año del proyecto
     * - enlace: enlace del recurso (si está vacío, se compara como cadena vacía)
     * RETORNA: boolean - true si existe un proyecto duplicado exacto
     */
    public static boolean existeProyectoDuplicado(String nombre, int anio, String enlace) {
        // Normalizar valores para la comparación
        String enlaceNorm = (enlace == null) ? "" : enlace.trim();
        String nombreNorm = (nombre == null) ? "" : nombre.trim();

        String sql = "SELECT COUNT(*) FROM proyectos WHERE nombre_proyecto = ? AND anio = ? AND (enlaces = ? OR (enlaces IS NULL AND ? = ''))";

        try (PreparedStatement pstmt = getConexion().prepareStatement(sql)) {
            pstmt.setString(1, nombreNorm);
            pstmt.setInt(2, anio);
            pstmt.setString(3, enlaceNorm);
            pstmt.setString(4, enlaceNorm);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar proyecto duplicado: " + e.getMessage());
        }
        return false;
    }

    /**
     * MÉTODO: insertarProyecto(Proyecto proyecto)
     * FUNCIÓN: Agregar un nuevo proyecto a la BD (CREATE)
     * PARÁMETRO: proyecto - Objeto Proyecto con los datos a guardar
     * RETORNA: boolean - true si se guardó exitosamente, false si falló
     * VALIDACIONES PREVIAS:
     * - Verifica que el nombre sea único
     * - Verifica que el año sea único
     * - Verifica que el enlace sea único (si no está vacío)
     */
    public static boolean insertarProyecto(Proyecto proyecto) {
        // VALIDACIÓN: Verificar si ya existe un proyecto con la misma tripleta
        // (nombre, anio, enlace). Solo se considera duplicado si los tres coinciden.
        if (existeProyectoDuplicado(proyecto.getNombreProyecto(), proyecto.getAnio(), proyecto.getEnlaces())) {
            System.err.println("Error: Ya Existe un Proyecto Similar");
            return false;
        }

        // SQL para insertar nuevo proyecto
        String sql = "INSERT INTO proyectos (nombre_proyecto, autores, anio, categoria, " +
                "resumen, palabras_clave, enlaces, correo_contacto) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = getConexion().prepareStatement(sql)) {
            // Asignar valores a los parámetros (?)
            pstmt.setString(1, proyecto.getNombreProyecto());
            pstmt.setString(2, proyecto.getAutores());
            pstmt.setInt(3, proyecto.getAnio());
            pstmt.setString(4, proyecto.getCategoria());
            pstmt.setString(5, proyecto.getResumen());
            pstmt.setString(6, proyecto.getPalabrasClave());
            pstmt.setString(7, proyecto.getEnlaces());
            pstmt.setString(8, proyecto.getCorreoContacto());

            // Ejecutar la inserción
            int filasAfectadas = pstmt.executeUpdate();
            // Si afecta una fila, el insert fue exitoso
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar proyecto: " + e.getMessage());
            return false;
        }
    }

    /**
     * MÉTODO: obtenerTodosLosProyectos()
     * FUNCIÓN: Obtener TODOS los proyectos de la BD (READ ALL)
     * RETORNA: List<Proyecto> - Lista de todos los proyectos ordenados por fecha
     * (más reciente primero)
     * USO: Se usa para mostrar el listado en la página JSP
     */
    public static List<Proyecto> obtenerTodosLosProyectos() {
        // Lista para almacenar los proyectos
        List<Proyecto> proyectos = new ArrayList<>();
        // SQL con ORDER BY para ordenar por fecha descendente (más recientes primero)
        String sql = "SELECT * FROM proyectos ORDER BY fecha_registro DESC";

        try (Statement stmt = getConexion().createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // Recorrer cada fila del resultado
            while (rs.next()) {
                // Crear nuevo objeto Proyecto
                Proyecto proyecto = new Proyecto();

                // Asignar valores de la BD al objeto
                proyecto.setId(rs.getInt("id"));
                proyecto.setNombreProyecto(rs.getString("nombre_proyecto"));
                proyecto.setAutores(rs.getString("autores"));
                proyecto.setAnio(rs.getInt("anio"));
                proyecto.setCategoria(rs.getString("categoria"));
                proyecto.setResumen(rs.getString("resumen"));
                proyecto.setPalabrasClave(rs.getString("palabras_clave"));
                proyecto.setEnlaces(rs.getString("enlaces"));
                proyecto.setCorreoContacto(rs.getString("correo_contacto"));

                // Convertir fecha de texto a objeto LocalDateTime
                String fechaStr = rs.getString("fecha_registro");
                if (fechaStr != null) {
                    LocalDateTime fecha = LocalDateTime.parse(fechaStr,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    proyecto.setFechaRegistro(fecha);
                }

                // Agregar el proyecto a la lista
                proyectos.add(proyecto);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener proyectos: " + e.getMessage());
        }

        return proyectos; // Retornar lista (vacía si no hay proyectos)
    }

    /**
     * MÉTODO: obtenerProyectoPorId(int id)
     * FUNCIÓN: Obtener UN proyecto específico por su ID (READ BY ID)
     * PARÁMETRO: id - Identificador único del proyecto
     * RETORNA: Proyecto - El proyecto encontrado o null si no existe
     * USO: Se usa para obtener detalles de un proyecto específico
     */
    public static Proyecto obtenerProyectoPorId(int id) {
        // SQL para buscar un proyecto por ID
        String sql = "SELECT * FROM proyectos WHERE id = ?";

        try (PreparedStatement pstmt = getConexion().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            // Si encuentra el proyecto
            if (rs.next()) {
                Proyecto proyecto = new Proyecto();

                // Llenar datos del proyecto
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

        return null; // No encontrado
    }

    /**
     * MÉTODO: actualizarProyecto(Proyecto proyecto)
     * FUNCIÓN: Modificar un proyecto existente (UPDATE)
     * PARÁMETRO: proyecto - Objeto con ID y datos actualizados
     * RETORNA: boolean - true si se actualizó, false si falló
     * NOTA: Debe existir un proyecto con ese ID
     */
    public static boolean actualizarProyecto(Proyecto proyecto) {
        // SQL para actualizar todos los campos excepto el ID y la fecha
        String sql = "UPDATE proyectos SET nombre_proyecto = ?, autores = ?, anio = ?, " +
                "categoria = ?, resumen = ?, palabras_clave = ?, enlaces = ?, correo_contacto = ? " +
                "WHERE id = ?";

        try (PreparedStatement pstmt = getConexion().prepareStatement(sql)) {
            // Asignar valores
            pstmt.setString(1, proyecto.getNombreProyecto());
            pstmt.setString(2, proyecto.getAutores());
            pstmt.setInt(3, proyecto.getAnio());
            pstmt.setString(4, proyecto.getCategoria());
            pstmt.setString(5, proyecto.getResumen());
            pstmt.setString(6, proyecto.getPalabrasClave());
            pstmt.setString(7, proyecto.getEnlaces());
            pstmt.setString(8, proyecto.getCorreoContacto());
            pstmt.setInt(9, proyecto.getId()); // Para encontrar qué proyecto actualizar

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0; // Si afecta al menos una fila, fue exitoso
        } catch (SQLException e) {
            System.err.println("Error al actualizar proyecto: " + e.getMessage());
            return false;
        }
    }

    /**
     * MÉTODO: eliminarProyecto(int id)
     * FUNCIÓN: Eliminar un proyecto de la BD (DELETE)
     * PARÁMETRO: id - ID del proyecto a eliminar
     * RETORNA: boolean - true si se eliminó, false si falló
     * ADVERTENCIA: Esta operación es permanente e irreversible
     */
    public static boolean eliminarProyecto(int id) {
        // SQL para eliminar proyecto por ID
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

    /**
     * MÉTODO: obtenerProyectosPorCategoria(String categoria)
     * FUNCIÓN: Obtener todos los proyectos de una CATEGORÍA específica (READ BY
     * CATEGORY)
     * PARÁMETRO: categoria - Nombre de la categoría (ej: "Proyecto", "Trabajo
     * Especial de Grado")
     * RETORNA: List<Proyecto> - Lista de proyectos de esa categoría ordenados por
     * fecha
     * USO: Se usa para filtrar proyectos por tipo
     */
    public static List<Proyecto> obtenerProyectosPorCategoria(String categoria) {
        List<Proyecto> proyectos = new ArrayList<>();
        // SQL con WHERE para filtrar por categoría
        String sql = "SELECT * FROM proyectos WHERE categoria = ? ORDER BY fecha_registro DESC";

        try (PreparedStatement pstmt = getConexion().prepareStatement(sql)) {
            pstmt.setString(1, categoria);
            ResultSet rs = pstmt.executeQuery();

            // Recorrer resultados
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

    /**
     * MÉTODO: deleteProyecto(int id)
     * FUNCIÓN: Eliminar un proyecto de la BD por su ID
     * PARÁMETRO: id - Identificador único del proyecto a eliminar
     * RETORNA: boolean - true si se eliminó correctamente, false si hubo error
     * USO: Cuando el usuario desea borrar un proyecto que agregó por error
     */
    public static boolean deleteProyecto(int id) {
        try {
            String sql = "DELETE FROM proyectos WHERE id = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);

            int filasAfectadas = stmt.executeUpdate();
            stmt.close();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar proyecto: " + e.getMessage());
            return false;
        }
    }

    /**
     * MÉTODO: cerrarConexion()
     * FUNCIÓN: Cerrar la conexión a la BD (LIMPIEZA)
     * USO: Se llama cuando se termina de usar la aplicación
     * IMPORTANTE: Siempre cerrar conexiones para liberar recursos
     */
    public static void cerrarConexion() {
        try {
            // Verificar que la conexión existe y está abierta
            if (conexion != null && !conexion.isClosed()) {
                conexion.close(); // Cerrar conexión
                System.out.println("Conexión cerrada");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
