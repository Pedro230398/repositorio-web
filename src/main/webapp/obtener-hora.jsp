<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Archivo: obtener-hora.jsp
    // Propósito: Responder con la hora formateada en zona 'America/Caracas'.
    // Se usa vía fetch desde `bibliograficos.jsp` para mostrar una hora en vivo.
    java.time.ZoneId zonaVenezuela = java.time.ZoneId.of("America/Caracas");
    java.time.ZonedDateTime fechaVenezuela = java.time.ZonedDateTime.now(zonaVenezuela);
    java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");
    out.print(fechaVenezuela.format(formato));
%>