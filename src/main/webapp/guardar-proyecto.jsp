<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page import="com.repositorio.bd.ConexionBD" %>
<%@ page import="com.repositorio.modelo.Proyecto" %>

<%
    // Inicializar BD en la primera carga
    ConexionBD.inicializarBD();
    
    // Obtener parámetros del formulario
    String nombreProyecto = request.getParameter("nombreProyecto");
    String autores = request.getParameter("autores");
    String fechaStr = request.getParameter("fecha");
    String categoria = request.getParameter("categoria");
    String resumen = request.getParameter("resumen");
    String palabrasClave = request.getParameter("palabrasClave");
    String enlaces = request.getParameter("enlaces");
    String correoContacto = request.getParameter("correoContacto");
    
    // Variables de respuesta
    String mensaje = "";
    String tipoMensaje = "error";
    
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
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmación - Guardar Proyecto</title>
    <link rel="stylesheet" href="<c:url value='/style.css'/>">
    <style>
        .mensaje-contenedor {
            padding: 20px;
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
            margin-top: 20px;
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
        <div class="mensaje-contenedor <%= tipoMensaje.equals("exito") ? "mensaje-exito" : "mensaje-error" %>">
            <h2><%= mensaje %></h2>
            <p>
                <% if (tipoMensaje.equals("exito")) { %>
                    El proyecto ha sido registrado correctamente en el sistema.
                <% } else { %>
                    Por favor verifica los datos ingresados e intenta nuevamente.
                <% } %>
            </p>
            <a href="proyectos.jsp" class="enlace-volver">← Volver a Proyectos</a>
        </div>
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
