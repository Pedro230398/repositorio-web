<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%><%
    java.time.ZoneId zonaVenezuela = java.time.ZoneId.of("America/Caracas");
    java.time.ZonedDateTime fechaVenezuela = java.time.ZonedDateTime.now(zonaVenezuela);
    java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");
    out.print(fechaVenezuela.format(formato));
%>