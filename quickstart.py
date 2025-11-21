#!/usr/bin/env python3
"""
QUICK START - Verificador de Proyecto Base de Datos
Valida que todo esté correctamente configurado antes del despliegue
"""

import os
import sys
from pathlib import Path

def print_header():
    print("=" * 60)
    print("  VERIFICACIÓN RÁPIDA - Sistema de Proyectos")
    print("=" * 60)
    print()

def check_java_files():
    print("1. Verificando archivos Java...")
    files = [
        "src/main/java/com/repositorio/modelo/Proyecto.java",
        "src/main/java/com/repositorio/bd/ConexionBD.java",
        "src/main/java/com/repositorio/servlet/ListarProyectosServlet.java"
    ]
    
    all_exist = True
    for file in files:
        if Path(file).exists():
            print(f"   ✓ {file}")
        else:
            print(f"   ✗ FALTA: {file}")
            all_exist = False
    
    return all_exist

def check_jsp_files():
    print("\n2. Verificando archivos JSP...")
    files = [
        "src/main/webapp/proyectos.jsp",
        "src/main/webapp/guardar-proyecto.jsp",
        "src/main/webapp/style.css"
    ]
    
    all_exist = True
    for file in files:
        if Path(file).exists():
            print(f"   ✓ {file}")
        else:
            print(f"   ✗ FALTA: {file}")
            all_exist = False
    
    return all_exist

def check_config_files():
    print("\n3. Verificando archivos de configuración...")
    files = ["pom.xml"]
    
    all_exist = True
    for file in files:
        if Path(file).exists():
            # Verificar si contiene SQLite
            with open(file, 'r') as f:
                content = f.read()
                if 'sqlite-jdbc' in content:
                    print(f"   ✓ {file} (con SQLite)")
                else:
                    print(f"   ✗ {file} (falta SQLite dependency)")
                    all_exist = False
        else:
            print(f"   ✗ FALTA: {file}")
            all_exist = False
    
    return all_exist

def check_war_file():
    print("\n4. Verificando archivo WAR...")
    war_file = Path("target/mi-repositorio-web-1.0.war")
    
    if war_file.exists():
        size_mb = war_file.stat().st_size / (1024 * 1024)
        print(f"   ✓ {war_file} ({size_mb:.1f} MB)")
        return True
    else:
        print(f"   ✗ FALTA: {war_file}")
        print("      Ejecutar: mvn clean package -DskipTests")
        return False

def print_summary(all_pass):
    print("\n" + "=" * 60)
    if all_pass:
        print("  ✅ ESTADO: LISTO PARA DESPLEGAR")
        print("=" * 60)
        print("\nPróximos pasos:")
        print("  1. Descargar Tomcat 10+ (si no lo tienes)")
        print("  2. Copiar WAR a $CATALINA_HOME/webapps/")
        print("  3. Iniciar Tomcat")
        print("  4. Acceder a http://localhost:8080/mi-repositorio-web-1.0/proyectos.jsp")
        print("\nAlternativa (sin Tomcat):")
        print("  java -jar target/dependency/webapp-runner.jar target/mi-repositorio-web-1.0.war")
        print("  Luego: http://localhost:8080/proyectos.jsp")
    else:
        print("  ❌ ESTADO: FALTAN ELEMENTOS")
        print("=" * 60)
        print("\nFalta compilar el proyecto. Ejecutar:")
        print("  mvn clean package -DskipTests")
    print()

def main():
    print_header()
    
    java_ok = check_java_files()
    jsp_ok = check_jsp_files()
    config_ok = check_config_files()
    war_ok = check_war_file()
    
    all_pass = java_ok and jsp_ok and config_ok and war_ok
    
    print_summary(all_pass)
    
    return 0 if all_pass else 1

if __name__ == "__main__":
    sys.exit(main())
