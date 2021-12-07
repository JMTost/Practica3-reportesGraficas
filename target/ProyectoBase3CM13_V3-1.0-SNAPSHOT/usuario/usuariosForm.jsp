<%-- 
    Document   : usuariosForm
    Created on : 21 nov 2021, 20:45:14
    Author     : JMTN
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Formulario usuarios</title>
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
                        Datos del usuario
                    </h1>               
                </div>
                <div class="card-body">
                    <c:if test="${ modificar ==1}">
                        <h1>modificando</h1>
                        <form method="post" action="UsuarioServlet?accion=guardar&modificar=1">
                            <div class="mb-3">
                                <label class="form-label">ID alumno</label>
                                <input type="text" name="txtIdUsuario" id="txtIdUsuario"   placeholder="ID del usuario" required="required" maxlength="50" value="<c:out value="${usuario.entidad.idAlumno}" />" class="form-control" readonly="readonly"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Nombre del usuario</label>
                                <input type="text" name="txtNombre" id="txtNombre"   placeholder="Nombre del usuario" required="required" maxlength="50" value="<c:out value="${usuario.entidad.nombre}"/>" class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Apellido paterno</label>
                                <input type="text" name="txtApPaterno" id="txtApPaterno"   placeholder="Apellido paterno" required="required" maxlength="50" value="<c:out value="${usuario.entidad.paterno}" />" class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Apellido materno</label>
                                <input type="text" name="txtApMaterno" id="txtApMaterno"   placeholder="Apellido materno" required="required" maxlength="50" value="<c:out value="${usuario.entidad.materno}" />" class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Email del usuario</label>
                                <input type="text" name="txtEmailUser" id="txtEmailUser"   placeholder="Email del usuario" required="required" maxlength="50" value="<c:out value="${usuario.entidad.email}" />" class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Nombre de usuario</label>
                                <input type="text" name="txtUsername" id="txtUsername"   placeholder="Nombre de usuario" required="required" maxlength="50" value="<c:out value="${usuario.entidad.nombreUsuario}" />" class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Clave de usuario</label>
                                <input type="text" name="txtClaveUser" id="txtClaveUser"   placeholder="Clave de usuario" required="required" maxlength="50" value="<c:out value="${usuario.entidad.claveUsuario}" />" class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Tipo de usuario</label>
                                <input type="text" name="txtTypeUser" id="txtTypeUser"   placeholder="Tipo de usuario" required="required" maxlength="50" value="<c:out value="${usuario.entidad.tipoUsuario}" />" class="form-control"/>
                            </div>
                            <button type="submit" class="btn btn-outline-primary">Guardar categoría</button>
                        </form>
                    </c:if>
                    <c:if test="${ modificar == 0}">

                        <form method="post" action="UsuarioServlet?accion=guardar&modificar=0">
                            <div class="mb-3">
                                <label class="form-label">Nombre del usuario</label>
                                <input type="text" name="txtNombre" id="txtNombre"   placeholder="Nombre del usuario" required="required" maxlength="50"  class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Apellido paterno</label>
                                <input type="text" name="txtApPaterno" id="txtApPaterno"   placeholder="Apellido paterno" required="required" maxlength="50"  class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Apellido materno</label>
                                <input type="text" name="txtApMaterno" id="txtApMaterno"   placeholder="Apellido materno" required="required" maxlength="50"  class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Email del usuario</label>
                                <input type="text" name="txtEmailUser" id="txtEmailUser"   placeholder="Email del usuario" required="required" maxlength="50"  class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Nombre de usuario</label>
                                <input type="text" name="txtUsername" id="txtUsername"   placeholder="Nombre de usuario" required="required" maxlength="50"  class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Clave de usuario</label>
                                <input type="text" name="txtClaveUser" id="txtClaveUser"   placeholder="Clave de usuario" required="required" maxlength="50"  class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Tipo de usuario</label>
                                <input type="text" name="txtTypeUser" id="txtTypeUser"   placeholder="Tipo de usuario" required="required" maxlength="50" class="form-control"/>
                            </div>
                            <button type="submit" class="btn btn-outline-primary">Guardar categoría</button>
                        </form>
                    </c:if>
                </div>
            </div>

        </div>
    </body>
</html>
