<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.repositorio.bd.ConexionBD" %>

<%
    // Obtener el ID del proyecto a eliminar
    String idStr = request.getParameter("id");
    
    if (idStr != null && !idStr.isEmpty()) {
        try {
            int id = Integer.parseInt(idStr);
            ConexionBD.inicializarBD();
            
            // Intentar eliminar el proyecto
            boolean eliminado = ConexionBD.deleteProyecto(id);
            
            if (eliminado) {
                // Redirigir a proyectos.jsp con mensaje de éxito
                response.sendRedirect("proyectos.jsp?mensaje=eliminado&exito=true");
            } else {
                // Redirigir con mensaje de error
                response.sendRedirect("proyectos.jsp?mensaje=error&exito=false");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("proyectos.jsp?mensaje=error&exito=false");
        }
    } else {
        response.sendRedirect("proyectos.jsp");
    }
%>

<%--
    Archivo: eliminar-proyecto.jsp
    Propósito: Recibe POST del formulario de eliminación, convierte el ID y
    llama a `ConexionBD.deleteProyecto(id)`. Redirige a `proyectos.jsp` con
    parámetros `mensaje` para indicar resultado (eliminado/error).
--%>
