<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
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
        <section class="module-list">
            <h2>Proyectos Recientes</h2>
            <div class="item">
                <h3>Proyecto: Desarrollo de Huertos Urbanos Sostenibles</h3>
                <p><strong>Autor(es):</strong> María Pérez, Juan Soto | <strong>Fecha:</strong> 2025</p>
                <p><strong>Resumen:</strong> Estudio de impacto en la seguridad alimentaria de comunidades locales.</p>
                <a href="#">Descargar PDF (Simulado)</a>
            </div>
            <div class="item">
                <h3>Tesis: La Tecnología Educativa en Zonas Rurales</h3>
                <p><strong>Autor(es):</strong> Ana Gómez | <strong>Fecha:</strong> 2024</p>
                <p><strong>Resumen:</strong> Análisis de la brecha digital y propuestas de integración tecnológica.</p>
                <a href="#">Descargar PDF (Simulado)</a>
            </div>
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