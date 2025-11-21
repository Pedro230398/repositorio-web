package com.repositorio.bd;

import com.repositorio.modelo.Proyecto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class ConexionBDTest {

    @BeforeEach
    public void setUp() throws SQLException {
        // Inicializar BD y limpiar la tabla antes de cada prueba
        ConexionBD.inicializarBD();
        Connection conn = ConexionBD.getConexion();
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM proyectos");
        }
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Limpiar tabla después de cada prueba para aislamiento
        Connection conn = ConexionBD.getConexion();
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM proyectos");
        }
    }

    @Test
    public void testPermitirMismoAnioDistintoNombreYEnlace() {
        Proyecto p1 = new Proyecto("Proyecto A", "Autor A", 2024, "Proyecto", "Resumen A", "claveA", "http://recursoA", "a@a.com");
        Proyecto p2 = new Proyecto("Proyecto B", "Autor B", 2024, "Proyecto", "Resumen B", "claveB", "http://recursoB", "b@b.com");

        // Ambos inserts deben ser exitosos porque sólo el año coincide
        assertTrue(ConexionBD.insertarProyecto(p1), "Inserción de p1 debería ser exitosa");
        assertTrue(ConexionBD.insertarProyecto(p2), "Inserción de p2 (mismo año, distinto nombre/enlace) debería ser exitosa");
    }

    @Test
    public void testRechazarDuplicadoExacto() {
        Proyecto p1 = new Proyecto("Proyecto X", "Autor X", 2023, "Investigación", "Resumen X", "claveX", "http://linkX", "x@x.com");
        Proyecto pDup = new Proyecto("Proyecto X", "Autor X", 2023, "Investigación", "Resumen X", "claveX", "http://linkX", "x@x.com");

        assertTrue(ConexionBD.insertarProyecto(p1), "Inserción de p1 debería ser exitosa");
        // Intento de insertar duplicado exacto debe fallar
        assertFalse(ConexionBD.insertarProyecto(pDup), "Inserción de duplicado exacto debería fallar");
    }
}
