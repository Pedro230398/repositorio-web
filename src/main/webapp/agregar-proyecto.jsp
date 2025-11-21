<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page import="com.repositorio.bd.ConexionBD" %>
<%@ page import="com.repositorio.modelo.Proyecto" %>

<%
    // Inicializar BD
    ConexionBD.inicializarBD();
    
    // Variables de respuesta para guardar
    String mensaje = "";
    String tipoMensaje = "error";
    
    // Verificar si es una solicitud POST (guardar proyecto)
    if ("POST".equals(request.getMethod())) {
        // Obtener parámetros del formulario
        String nombreProyecto = request.getParameter("nombreProyecto");
        String autores = request.getParameter("autores");
        String fechaStr = request.getParameter("fecha");
        String categoria = request.getParameter("categoria");
        String resumen = request.getParameter("resumen");
        String palabrasClave = request.getParameter("palabrasClave");
        String enlaces = request.getParameter("enlaces");
        String correoContacto = request.getParameter("correoContacto");
        
        // Validar que los campos obligatorios no estén vacíos
        if (nombreProyecto != null && !nombreProyecto.trim().isEmpty() &&
            autores != null && !autores.trim().isEmpty() &&
            fechaStr != null && !fechaStr.trim().isEmpty() &&
            categoria != null && !categoria.trim().isEmpty() &&
            resumen != null && !resumen.trim().isEmpty() &&
            correoContacto != null && !correoContacto.trim().isEmpty()) {
            
            try {
                // Convertir fecha a entero
                int anio = Integer.parseInt(fechaStr);
                
                // Crear objeto Proyecto
                Proyecto nuevoProyecto = new Proyecto(
                    nombreProyecto.trim(),
                    autores.trim(),
                    anio,
                    categoria,
                    resumen.trim(),
                    palabrasClave != null ? palabrasClave.trim() : "",
                    enlaces != null ? enlaces.trim() : "",
                    correoContacto.trim()
                );
                
                // Validar unicidad por TRIPLETA: nombre + año + enlace
                if (ConexionBD.existeProyectoDuplicado(nombreProyecto.trim(), anio, nuevoProyecto.getEnlaces())) {
                    mensaje = "✗ Ya Existe un Proyecto Similar";
                    tipoMensaje = "error";
                } else if (ConexionBD.insertarProyecto(nuevoProyecto)) {
                    // Guardar en la base de datos
                    mensaje = "✓ Proyecto guardado exitosamente en la base de datos.";
                    tipoMensaje = "exito";
                } else {
                    mensaje = "✗ Error al guardar el proyecto en la base de datos.";
                    tipoMensaje = "error";
                }
                
            } catch (NumberFormatException e) {
                mensaje = "✗ Error: El año debe ser un número válido.";
                tipoMensaje = "error";
            } catch (Exception e) {
                mensaje = "✗ Error al procesar el proyecto: " + e.getMessage();
                tipoMensaje = "error";
            }
        } else {
            mensaje = "✗ Por favor completa todos los campos obligatorios.";
            tipoMensaje = "error";
        }
    }
%>

<%--
    Archivo: agregar-proyecto.jsp
    Propósito: Formulario para crear un nuevo proyecto. Recibe POST con los
    parámetros del formulario, valida campos obligatorios y llama a la capa
    de persistencia (`ConexionBD`) para insertar el proyecto tras validar
    la unicidad por nombre+anio+enlace.
--%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agregar o Eliminar Proyectos</title>
    <link rel="stylesheet" href="<c:url value='/style.css'/>">
    <style>
        .mensaje-contenedor {
            padding: 15px;
            border-radius: 8px;
            margin: 20px 0;
            text-align: center;
            font-size: 16px;
        }
        .mensaje-exito {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .mensaje-error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .enlace-volver {
            display: inline-block;
            margin-top: 15px;
            padding: 10px 20px;
            background-color: #004d99;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .enlace-volver:hover {
            background-color: #0073e6;
        }
    </style>
</head>
<body>
    <header class="header">
        <h1>📋 Proyectos Académicos Comunitarios</h1>
        <nav>
            <a href="index.jsp">Inicio</a>
            <a href="proyectos.jsp" class="active">Proyectos Comunitarios</a>
            <a href="bibliograficos.jsp">Material Bibliográfico</a>
        </nav>
    </header>

    <main class="container">
        <!-- Mensaje de confirmación o error -->
        <% if (!tipoMensaje.isEmpty()) { %>
            <div class="mensaje-contenedor <%= tipoMensaje.equals("exito") ? "mensaje-exito" : "mensaje-error" %>">
                <h3><%= mensaje %></h3>
                <% if (tipoMensaje.equals("exito")) { %>
                    <p>El proyecto ha sido registrado correctamente en el sistema.</p>
                    <a href="proyectos.jsp" class="enlace-volver">← Ver Proyectos</a>
                <% } %>
            </div>
        <% } %>

        <!-- Sección para agregar nuevo proyecto -->
        <section class="add-project-section">
            <h2>📝 Agregar Nuevo Proyecto</h2>
            <form class="project-form" method="POST" action="agregar-proyecto.jsp">
                <!-- Fila 1: Nombre y Autor(es) -->
                <div class="form-row">
                    <div class="form-group form-col">
                        <label for="nombreProyecto">Nombre del Proyecto:</label>
                        <input type="text" id="nombreProyecto" name="nombreProyecto" placeholder="Ej: Huertos Urbanos Sostenibles" required>
                    </div>
                    <div class="form-group form-col">
                        <label for="autores">Autor(es):</label>
                        <input type="text" id="autores" name="autores" placeholder="Ej: María Pérez, Juan Soto" required>
                    </div>
                </div>

                <!-- Fila 2: Año y Categoría -->
                <div class="form-row">
                    <div class="form-group form-col">
                        <label for="fecha">Año/Fecha:</label>
                        <input type="number" id="fecha" name="fecha" placeholder="Ej: 2025" min="2000" max="2100" required>
                    </div>
                    <div class="form-group form-col">
                        <label for="categoria">Categoría:</label>
                        <select id="categoria" name="categoria" required>
                            <option value="">-- Selecciona una categoría --</option>
                            <option value="Proyecto">Proyecto</option>
                            <option value="Trabajo Especial de Grado">Trabajo Especial de Grado</option>
                            <option value="Investigación">Investigación</option>
                            <option value="Iniciativa Comunitaria">Iniciativa Comunitaria</option>
                        </select>
                    </div>
                </div>

                <!-- Fila 3: Palabras Clave y Enlace -->
                <div class="form-row">
                    <div class="form-group form-col">
                        <label for="palabrasClave">Palabras Clave:</label>
                        <input type="text" id="palabrasClave" name="palabrasClave" placeholder="Ej: sustentabilidad, tecnología (separadas por comas)">
                    </div>
                    <div class="form-group form-col">
                        <label for="enlaces">Enlaces o Recursos:</label>
                        <input type="url" id="enlaces" name="enlaces" placeholder="Ej: https://ejemplo.com">
                    </div>
                </div>

                <!-- Fila 4: Correo de Contacto -->
                <div class="form-row">
                    <div class="form-group form-col">
                        <label for="correoContacto">Correo de Contacto:</label>
                        <input type="email" id="correoContacto" name="correoContacto" placeholder="tu@correo.com" required>
                    </div>
                </div>

                <!-- Fila 5: Resumen (full width) -->
                <div class="form-group form-full">
                    <label for="resumen">Resumen/Descripción:</label>
                    <textarea id="resumen" name="resumen" rows="4" placeholder="Describe brevemente el contenido y propósito del proyecto..." required></textarea>
                </div>

                <!-- Botones -->
                <div class="form-buttons">
                    <button type="submit" class="btn-submit">Guardar Proyecto</button>
                    <button type="reset" class="btn-reset">Limpiar Formulario</button>
                    <a href="proyectos.jsp" class="enlace-volver">← Volver a Proyectos</a>
                </div>
            </form>
        </section>
    </main>

    <footer class="footer">
        <p>
            © 2025 Repositorio Académico Estudiantil. Prototipo Funcional.
            | Fecha de Acceso: 
            <% 
                java.time.ZoneId zonaVenezuela = java.time.ZoneId.of("America/Caracas");
                java.time.ZonedDateTime fechaVenezuela = java.time.ZonedDateTime.now(zonaVenezuela);
                java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");
                out.print(fechaVenezuela.format(formato)); 
            %>
        </p>
    </footer>
</body>
</html>
