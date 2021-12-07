<%-- 
    Document   : categoriasForm
    Created on : 14 oct 2021, 8:09:09
    Author     : JMTN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Categoría form</title>
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
            <div class="card border-primary">
                <div class="card-header">
                    <h1 class="text-center">
                        Datos de la categoría
                    </h1>               
                </div>
                <div class="card-body">
                    <c:if test="${ modificar ==1}">
                        <form method="post" action="CategoriaServlet?accion=guardar&modificar=1">
                            <div class="mb-3">
                                <label class="form-label">ID categoría</label>
                                <input type="text" name="txtIDcategoria" id="txtIDcategoria"   placeholder="ID categoria" required="required" maxlength="50" value="<c:out value="${categoria.entidad.idCategoria}"/>" class="form-control" readonly="readonly"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Nombre categoría</label>
                                <input type="text" name="txtNombreCategoria" id="txtNombreCategoria"   placeholder="Nombre categoría" required="required" maxlength="50" value="<c:out value="${categoria.entidad.nombreCategoria}"/>" class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Descripción categoría</label>
                                <input type="text" name="txtDescripcionCategoria" id="txtDescripcionCategoria"   placeholder="Descripcion categoría" required="required" maxlength="50" value="<c:out value="${categoria.entidad.descripcionCategoria}" />" class="form-control"/>
                            </div>
                            <button type="submit" class="btn btn-outline-primary">Guardar categoría</button>
                        </form>
                    </c:if>
                    <c:if test="${ modificar == 0}">
                        
                        <form method="post" action="CategoriaServlet?accion=guardar&modificar=0">
                            <div class="mb-3">
                                <label class="form-label">Nombre categoría</label>
                                <input type="text" name="txtNombreCategoria" id="txtNombreCategoria"   placeholder="Nombre categoría" required="required" maxlength="50" class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Descripción categoría</label>
                                <input type="text" name="txtDescripcionCategoria" id="txtDescripcionCategoria"   placeholder="Descripcion categoría" required="required" maxlength="50" class="form-control"/>
                            </div>
                            <button type="submit" class="btn btn-outline-primary">Guardar categoría</button>
                        </form>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>