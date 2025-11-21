<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Módulo Material Bibliográfico</title>
    <link rel="stylesheet" href="<c:url value='/style.css'/>">
    <script>
        function actualizarHoraVenezuela() {
            fetch('obtener-hora.jsp')
                .then(response => response.text())
                .then(hora => {
                    document.getElementById('horaVenezuela').textContent = hora;
                })
                .catch(error => console.error('Error al obtener la hora:', error));
        }
        
        // Actualizar la hora al cargar la página
        window.addEventListener('load', function() {
            actualizarHoraVenezuela();
            // Actualizar cada segundo
            setInterval(actualizarHoraVenezuela, 1000);
        });
    </script>
</head>
<body>
    <header class="header">
        <h1>📖 Material Bibliográfico y Documental</h1>
        <nav>
            <a href="index.jsp">Inicio</a>
            <a href="proyectos.jsp">Proyectos Comunitarios</a>
            <a href="bibliograficos.jsp" class="active">Material Bibliográfico</a>
        </nav>
    </header>

    <main class="container">
        <section class="module-list">
            <h2>Documentos Clave</h2>
            <div class="item">
                <h3>Libro: Metodología de Investigación Social Aplicada</h3>
                <p><strong>Autor:</strong> Dr. Carlos Ruiz | <strong>Año:</strong> 2023</p>
                <p><strong>Clasificación:</strong> Metodología</p>
                <a href="#">Ver Resumen / Enlace Externo</a>
            </div>
            <div class="item">
                <h3>Artículo: Ética y Comunidad Digital</h3>
                <p><strong>Autor:</strong> Revista de Estudios Sociales | <strong>Año:</strong> 2024</p>
                <p><strong>Clasificación:</strong> Humanidades</p>
                <a href="#">Acceder al Artículo Completo</a>
            </div>
            </section>
    </main>
    <footer class="footer">
        <p>
            © 2025 Repositorio Académico Estudiantil. Prototipo Funcional.
            | Fecha de Acceso: <span id="horaVenezuela">Cargando...</span>
        </p>
    </footer>
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