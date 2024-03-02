<%-- 
    Document   : index
    Created on : 22 févr. 2024, 06:27:49
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    Object sessionId = session.getAttribute("idUser");
    if(sessionId != null){
        response.sendRedirect("Acceuil.jsp");
    }
    else {
        response.sendRedirect("login.jsp");
    }
%>