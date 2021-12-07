<%-- 
    Document   : listaProductos
    Created on : 18 oct 2021, 15:09:35
    Author     : JMTN
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de productos</title>
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
                            <li class="nav-item">
                                <a class="nav-link" href="UsuarioServlet?accion=listaDeUsuarios">Lista de usuarios</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <div class="card border-success">
                <div class="card-header text-center">
                    Lista de productos
                </div>
                <div class="card-body container-fluid">
                    <h4>
                        <a href="ProductoServlet?accion=nuevo" class="btn btn-outline-dark">Crear Producto</a>
                        <a href="ProductoServlet?accion=graficar" class="btn btn-outline-info" target="_blank">Mostrar Gráfica</a>
                        <a href="ProductoServlet?accion=verReporte&individual=0" class="btn btn-outline-secondary" target="_blank">Reporte General</a>
                    </h4>
                    <c:if test="${mensaje != null}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <strong>${mensaje}</strong>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" arial-label="Close"/>
                        </div>
                    </c:if>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID producto</th>
                                <th>Nombre producto</th>
                                <th>Descripción producto</th>
                                <th>Precio</th>
                                <th>Existencia</th>
                                <th>Stock minimo</th>
                                <th>Clave categoría</th>
                                <th>Eliminar</th>
                                <th>Actualizar</th>
                                <th>Reporte</th>
                            </tr>
                        </thead>
                        <c:forEach var="dto" items="${listaDeProductos}">
                            <tbody>
                                <tr>
                                    <td><a href="ProductoServlet?accion=ver&id=<c:out value="${dto.entidad.idProducto}"/>" class="btn btn-outline-warning"><c:out value="${dto.entidad.idProducto}"/></a></td>
                                    <td><c:out value="${dto.entidad.nombreProducto}"/></td>
                                    <td><c:out value="${dto.entidad.descripcionProducto}"/></td>
                                    <td><c:out value="${dto.entidad.precio}"/></td>
                                    <td><c:out value="${dto.entidad.existencia}"/></td>
                                    <td><c:out value="${dto.entidad.stockMinimo}"/></td>
                                    <td><c:out value="${dto.entidad.claveCategoria}"/></td>
                                    <td><a href="ProductoServlet?accion=eliminar&id=<c:out value="${dto.entidad.idProducto}"/>" class="btn btn-info">Eliminar</a></td>
                                    <td><a href="ProductoServlet?accion=actualizar&id=<c:out value="${dto.entidad.idProducto}"/>" class="btn btn-primary">Actualizar</a></td>
                                    <td><a href="ProductoServlet?accion=verReporte&individual=1&id=<c:out value="${dto.entidad.idProducto}" />" class="btn btn-dark"  target="_blank">Reporte</a></td>
                                </tr>
                            </tbody>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>