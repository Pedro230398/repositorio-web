<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page import="com.repositorio.bd.ConexionBD" %>
<%@ page import="com.repositorio.modelo.Proyecto" %>
<%@ page import="java.util.List" %>

<%
    // Inicializar BD
    ConexionBD.inicializarBD();
    
    // Obtener todos los proyectos
    List<Proyecto> proyectos = ConexionBD.obtenerTodosLosProyectos();
    request.setAttribute("proyectos", proyectos);
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Módulo Proyectos Comunitarios</title>
    <link rel="stylesheet" href="<c:url value='/style.css'/>">
    <style>
        .btn-add-project {
            display: inline-block;
            background-color: #ff6600;
            color: white;
            padding: 12px 25px;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            transition: background-color 0.3s;
            font-size: 14px;
        }
        .btn-add-project:hover {
            background-color: #cc5200;
        }
    </style>
</head>
<body>
    <header class="header">
        <h1> Proyectos Académicos Comunitarios</h1>
        <nav>
            <a href="index.jsp">Inicio</a>
            <a href="proyectos.jsp" class="active">Proyectos Comunitarios</a>
            <a href="bibliograficos.jsp">Material Bibliográfico</a>
        </nav>
    </header>

    <main class="container">
        <!-- Botón para agregar nuevo proyecto -->
        <div style="margin-bottom: 30px; text-align: left;">
            <a href="agregar-proyecto.jsp" class="btn-add-project"> Agregar Nuevo Proyecto</a>
        </div>

        <!-- Sección de proyectos listados -->
        <section class="module-list">
            <h2> Proyectos Registrados (${ proyectos.size()} total)</h2>
            <c:choose>
                <c:when test="${proyectos.size() > 0}">
                    <c:forEach var="proyecto" items="${proyectos}">
                        <div class="item">
                            <h3>
                                <span class="categoria-badge">${proyecto.categoria}</span>
                                ${proyecto.nombreProyecto}
                            </h3>
                            <p>
                                <strong>Autor(es):</strong> ${proyecto.autores} | 
                                <strong>Año:</strong> ${proyecto.anio}
                            </p>
                            <p><strong>Resumen:</strong> ${proyecto.resumen}</p>
                            <c:if test="${not empty proyecto.palabrasClave}">
                                <p><strong>Palabras Clave:</strong> ${proyecto.palabrasClave}</p>
                            </c:if>
                            <c:if test="${not empty proyecto.enlaces}">
                                <p><a href="${proyecto.enlaces}" target="_blank" class="card-link"> Ver Recurso</a></p>
                            </c:if>
                            <p><strong>Contacto:</strong> <a href="mailto:${proyecto.correoContacto}">${proyecto.correoContacto}</a></p>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="item">
                        <p style="text-align: center; color: #999;">No hay proyectos registrados aún. ¡Sé el primero en agregar uno!</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </section>
    </main>
    
    <footer class="footer">
        <p>
             2025 Repositorio Académico Estudiantil. Prototipo Funcional.
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
