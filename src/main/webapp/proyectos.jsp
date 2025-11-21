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
        <!-- Sección para agregar nuevo proyecto -->
        <section class="add-project-section">
            <h2>📝 Agregar Nuevo Proyecto</h2>
            <form class="project-form" method="POST" action="guardar-proyecto.jsp">
                <div class="form-group">
                    <label for="nombreProyecto">Nombre del Proyecto:</label>
                    <input type="text" id="nombreProyecto" name="nombreProyecto" placeholder="Ej: Huertos Urbanos Sostenibles" required>
                </div>

                <div class="form-group">
                    <label for="autores">Autor(es):</label>
                    <input type="text" id="autores" name="autores" placeholder="Ej: María Pérez, Juan Soto" required>
                </div>

                <div class="form-group">
                    <label for="fecha">Año/Fecha:</label>
                    <input type="number" id="fecha" name="fecha" placeholder="Ej: 2025" min="2000" max="2100" required>
                </div>

                <div class="form-group">
                    <label for="categoria">Categoría:</label>
                    <select id="categoria" name="categoria" required>
                        <option value="">-- Selecciona una categoría --</option>
                        <option value="Proyecto">Proyecto</option>
                        <option value="Trabajo Especial de Grado">Trabajo Especial de Grado</option>
                        <option value="Investigación">Investigación</option>
                        <option value="Iniciativa Comunitaria">Iniciativa Comunitaria</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="resumen">Resumen/Descripción:</label>
                    <textarea id="resumen" name="resumen" rows="4" placeholder="Describe brevemente el contenido y propósito del proyecto..." required></textarea>
                </div>

                <div class="form-group">
                    <label for="palabrasClave">Palabras Clave:</label>
                    <input type="text" id="palabrasClave" name="palabrasClave" placeholder="Ej: sustentabilidad, tecnología, educación (separadas por comas)">
                </div>

                <div class="form-group">
                    <label for="enlaces">Enlaces o Recursos:</label>
                    <input type="url" id="enlaces" name="enlaces" placeholder="Ej: https://ejemplo.com">
                </div>

                <div class="form-group">
                    <label for="correoContacto">Correo de Contacto:</label>
                    <input type="email" id="correoContacto" name="correoContacto" placeholder="tu@correo.com" required>
                </div>

                <button type="submit" class="btn-submit">Guardar Proyecto</button>
                <button type="reset" class="btn-reset">Limpiar Formulario</button>
            </form>
        </section>

        <!-- Sección de proyectos listados -->
        <section class="module-list">
            <h2>Proyectos Registrados (${proyectos.size()} total)</h2>
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
                            <p><strong>Contacto:</strong> ${proyecto.correoContacto}</p>
                            <c:if test="${not empty proyecto.enlaces}">
                                <p><a href="${proyecto.enlaces}" target="_blank">🔗 Ver Recurso</a></p>
                            </c:if>
                            <p style="font-size: 12px; color: #999;">
                                Registrado: <fmt:formatDate value="${proyecto.fechaRegistro}" pattern="dd/MM/yyyy HH:mm:ss" />
                            </p>
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