<%-- 
    Document   : productosForm
    Created on : 22 oct 2021, 21:21:32
    Author     : JMTN
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Productos Form</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js" ></script>
    </head>
    <body>
        <%
            request.setCharacterEncoding("UTF-8");
        %>
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
                        Datos del producto
                    </h1>               
                </div>
                <div class="card-body">
                    <c:if test="${ modificar ==1}">
                        <form method="post" action="ProductoServlet?accion=guardar&modificar=1">
                            <div class="mb-3">
                                <label class="form-label">ID del producto</label>
                                <input type="text" name="txtIdProducto" id="txtIdProducto"   placeholder="ID del producto" required="required" value="<c:out value="${producto.entidad.idProducto}"/>" class="form-control" readonly="readonly"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Nombre del producto</label>
                                <input type="text" name="txtNombreProducto" id="txtNombreProducto"   placeholder="Nombre del producto" required="required" maxlength="50" value="<c:out value="${producto.entidad.nombreProducto}"/>" class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Descripción del producto</label>
                                <input type="text" name="txtDescripcionProducto" id="txtDescripcionProducto"   placeholder="Descripcion del producto" required="required" maxlength="100" value="<c:out value="${producto.entidad.descripcionProducto}" />" class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Precio del producto</label>
                                <input type="text" name="txtPrecioProducto" id="txtPrecioProducto"   placeholder="Precio del producto" required="required" maxlength="50" value="<c:out value="${producto.entidad.precio}" />" class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Existencia del producto</label>
                                <input type="text" name="txtExistenciaProducto" id="txtExistenciaProducto"   placeholder="Exitencia del producto" required="required" maxlength="50" value="<c:out value="${producto.entidad.existencia}" />" class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Stock del producto</label>
                                <input type="text" name="txtStockProducto" id="txtStockProducto"   placeholder="Stock del producto" required="required" maxlength="50" value="<c:out value="${producto.entidad.stockMinimo}" />" class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Clave de categoría</label>
                                <input type="text" name="txtClaveCategoria" id="txtClaveCategoria"   placeholder="Clave de categoría del producto" required="required" maxlength="50" value="<c:out value="${producto.entidad.claveCategoria}" />" class="form-control"/>
                            </div>
                            <button type="submit" class="btn btn-outline-primary">Guardar producto</button>
                        </form>
                    </c:if>
                    <c:if test="${ modificar == 0}">
                        
                        <form method="post" action="ProductoServlet?accion=guardar&modificar=0">
                            <div class="mb-3">
                                <label class="form-label">Nombre del producto</label>
                                <input type="text" name="txtNombreProducto" id="txtNombreProducto"   placeholder="Nombre del producto" required="required" maxlength="50" class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Descripción del producto</label>
                                <input type="text" name="txtDescripcionProducto" id="txtDescripcionProducto"   placeholder="Descripcion del producto" required="required" maxlength="100" class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Precio del producto</label>
                                <input type="text" name="txtPrecioProducto" id="txtPrecioProducto"   placeholder="Precio del producto" required="required" maxlength="50"  class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Existencia del producto</label>
                                <input type="text" name="txtExistenciaProducto" id="txtExistenciaProducto"   placeholder="Exitencia del producto" required="required" maxlength="50"  class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Srock del producto</label>
                                <input type="text" name="txtStockProducto" id="txtStockProducto"   placeholder="Stock del producto" required="required" maxlength="50"  class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Clave de categoría</label>
                                <input type="text" name="txtClaveCategoria" id="txtClaveCategoria"   placeholder="Clave de categoría del producto" required="required" maxlength="50"  class="form-control"/>
                            </div>
                            <button type="submit" class="btn btn-outline-primary">Guardar producto</button>
                        </form>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
