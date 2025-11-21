<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<%--
    Archivo: index.jsp
    Propósito: Página de inicio del repositorio — enlaces a los módulos principales
    (Proyectos Comunitarios y Material Bibliográfico). Contiene navegación y
    contenido introductorio. No realiza operaciones de BD.
--%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Repositorio Digital - Inicio</title>
    <link rel="stylesheet" href="<c:url value='/style.css'/>">
</head>
<body>
    <header class="header">
        <h1>📚 Repositorio Digital Académico</h1>
        <nav>
            <a href="index.jsp" class="active">Inicio</a>
            <a href="proyectos.jsp">Proyectos Comunitarios</a>
            <a href="bibliograficos.jsp">Material Bibliográfico</a>
        </nav>
    </header>

    <main class="container">
        <section class="hero">
            <h2>Bienvenido/a al Repositorio de Difusión Académica</h2>
            <p>Aquí encontrarás la producción intelectual de nuestra comunidad: desde proyectos con impacto social hasta recursos bibliográficos esenciales para tu formación.</p>
            <a href="proyectos.jsp" class="button">Explorar Proyectos</a>
        </section>

        <section class="modules-overview">
            <div class="card">
                <h3>Módulo 1: Proyectos Comunitarios</h3>
                <p>Accede a trabajos de investigación y tesis centradas en el **desarrollo local y el impacto social**.</p>
                <a href="proyectos.jsp" class="card-link">Ver Proyectos</a>
            </div>
            <div class="card">
                <h3>Módulo 2: Material Bibliográfico</h3>
                <p>Encuentra **documentos, artículos y libros digitales** esenciales para el soporte académico.</p>
                <a href="bibliograficos.jsp" class="card-link">Ver Bibliografía</a>
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