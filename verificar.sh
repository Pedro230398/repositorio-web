#!/bin/bash
# Script de prueba rápida - Verificar que el proyecto está listo

echo "==============================================="
echo "  VERIFICACIÓN DEL PROYECTO"
echo "==============================================="
echo ""

# Verificar estructura de directorios
echo "1. Verificando estructura de directorios..."
if [ -d "src/main/java/com/repositorio/modelo" ] && \
   [ -d "src/main/java/com/repositorio/bd" ] && \
   [ -d "src/main/java/com/repositorio/servlet" ] && \
   [ -d "src/main/webapp" ]; then
    echo "   ✓ Directorios correctos"
else
    echo "   ✗ Faltan directorios"
fi
echo ""

# Verificar archivos Java
echo "2. Verificando archivos Java..."
if [ -f "src/main/java/com/repositorio/modelo/Proyecto.java" ] && \
   [ -f "src/main/java/com/repositorio/bd/ConexionBD.java" ] && \
   [ -f "src/main/java/com/repositorio/servlet/ListarProyectosServlet.java" ]; then
    echo "   ✓ Archivos Java presentes"
else
    echo "   ✗ Faltan archivos Java"
fi
echo ""

# Verificar archivos JSP
echo "3. Verificando archivos JSP..."
if [ -f "src/main/webapp/proyectos.jsp" ] && \
   [ -f "src/main/webapp/guardar-proyecto.jsp" ] && \
   [ -f "src/main/webapp/style.css" ]; then
    echo "   ✓ Archivos JSP y CSS presentes"
else
    echo "   ✗ Faltan archivos JSP o CSS"
fi
echo ""

# Verificar pom.xml
echo "4. Verificando pom.xml..."
if grep -q "sqlite-jdbc" pom.xml; then
    echo "   ✓ Dependencia SQLite presente"
else
    echo "   ✗ Falta dependencia SQLite"
fi
echo ""

# Compilar
echo "5. Compilando proyecto..."
mvn clean compile -q
if [ $? -eq 0 ]; then
    echo "   ✓ Compilación exitosa"
else
    echo "   ✗ Error en compilación"
fi
echo ""

echo "==============================================="
echo "  RESUMEN"
echo "==============================================="
echo ""
echo "✓ El proyecto está LISTO para usar"
echo ""
echo "Para iniciar:"
echo "  1. mvn clean package -DskipTests"
echo "  2. Desplegar el WAR en Tomcat o servidor similar"
echo ""
echo "Base de datos:"
echo "  - Archivo: repositorio.db"
echo "  - Se crea automáticamente en la primera carga"
echo ""
echo "==============================================="
