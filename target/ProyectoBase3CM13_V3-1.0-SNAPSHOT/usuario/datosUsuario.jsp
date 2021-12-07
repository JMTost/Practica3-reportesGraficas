<%-- 
    Document   : datosUsuario
    Created on : 21 nov 2021, 20:44:55
    Author     : JMTN
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Datos usuarios</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js" ></script>
    </head>
    <body>
       <div class="container">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div class="container-fluid">
                    <a class="navbar-brand" href="#">
                        <img src="./Images/bootstrap-logo.svg" alt="" width="30" height="24" class="d-inline-block align-text-top">
                        Proyecto Base V3
                    </a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNav">
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="#">Home</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="CategoriaServlet?accion=listaDeCategorias">Lista de categorias</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="ProductoServlet?accion=listaDeProductos">Lista de productos</a>
                            </li>
                                <a class="nav-link" href="UsuarioServlet?accion=listaDeUsuarios">Lista de usuarios</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
           <div class="card bg-light">
                <div class="card-header">
                    <h3 class="text-center">
                        Datos del usuario
                    </h3>
                </div>
                <div class="card-body">
                    <center>
                        <img src="./Images/bootstrap-logo.svg" alt="" width="200" height="200" class="d-inline-block align-text-top">
                    </center>
                    <ul class="list-group">
                        <li class="list-group-item">
                            <c:out value="${usuario.entidad.idAlumno}" />
                        </li>
                        <li class="list-group-item">
                            <c:out value="${usuario.entidad.nombre}"/>
                        </li>
                        <li class="list-group-item">
                            <c:out value="${usuario.entidad.paterno}"/>
                        </li>
                        <li class="list-group-item">
                            <c:out value="${usuario.entidad.materno}"/>
                        </li>
                        <li class="list-group-item">
                            <c:out value="${usuario.entidad.email}"/>
                        </li>
                        <li class="list-group-item">
                            <c:out value="${usuario.entidad.nombreUsuario}"/>
                        </li>
                        <li class="list-group-item">
                            <c:out value="${usuario.entidad.claveUsuario}"/>
                        </li>
                        <li class="list-group-item">
                            <c:out value="${usuario.entidad.tipoUsuario}"/>
                        </li>
                    </ul>
                </div>
            </div>
       </div>
    </body>
</html>
