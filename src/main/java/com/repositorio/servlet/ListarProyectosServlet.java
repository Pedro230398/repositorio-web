package com.repositorio.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.repositorio.bd.ConexionBD;
import com.repositorio.modelo.Proyecto;
import java.util.List;

@WebServlet("/listar-proyectos")
public class ListarProyectosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init() throws ServletException {
        super.init();
        // Nota: Se inicializa la BD cuando se inicia el servlet (prepara tabla/conexión)
        // Comentario no funcional: esta llamada garantiza que la tabla exista antes de manejar peticiones
        ConexionBD.inicializarBD();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Nota: manejar GET -> obtener lista de proyectos y reenviar a JSP
        // Comentario: la lógica aquí es solo de presentación, no modifica la BD
        List<Proyecto> proyectos = ConexionBD.obtenerTodosLosProyectos();
        
        // Pasar la lista a la solicitud
        request.setAttribute("proyectos", proyectos);
        
        // Redirigir a la JSP
        request.getRequestDispatcher("/proyectos.jsp").forward(request, response);
    }
}
