<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<%@page import="java.util.ArrayList"%>
<%@page import="bean.BeanUsuario"%>
<%@page import="bean.BeanRol"%>
<%@page import="mysql.Sql_Usuario"%>
<%@page import="mysql.Sql_Rol"%>

<%
int id =
Integer.parseInt(
request.getParameter("id"));

Sql_Usuario sql =
new Sql_Usuario();

BeanUsuario u =
sql.buscarUsuario(id);

Sql_Rol sqlRol = new Sql_Rol();
ArrayList<BeanRol> listaRoles = sqlRol.listarRoles();

request.setAttribute("paginaActiva", "usuarios");
request.setAttribute("tituloPagina", "Editar Trabajador");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Editar Trabajador</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link href="css/estilo.css" rel="stylesheet">
</head>

<body>

<div class="container-fluid">
    <div class="row">

        <%@ include file="header.jsp" %>

            <div class="card card-resumen">
                <div class="card-body">

                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h4 class="mb-0">Editar Trabajador</h4>
                    </div>

                    <hr>

                    <form action="ServletUsuarioActualizar" method="post">

                        <input type="hidden" name="txtId" value="<%=u.getIdUsuario()%>">

                        <div class="row">

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Nombre</label>
                                <input type="text" name="txtNombre"
                                       value="<%=u.getNombre()%>"
                                       class="form-control"
                                       required>
                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label">DNI</label>
                                <input type="text" name="txtDni"
                                       value="<%=u.getDni()%>"
                                       class="form-control"
                                       maxlength="8"
                                       required>
                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Usuario</label>
                                <input type="text" name="txtUsuario"
                                       value="<%=u.getUsuario()%>"
                                       class="form-control"
                                       required>
                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Nueva Contraseña <small class="text-muted">(dejar vacio para no cambiar)</small></label>
                                <input type="password" name="txtClave"
                                       class="form-control"
                                       placeholder="Nueva contraseña">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Teléfono</label>
                                <input type="text" name="txtTelefono"
                                       value="<%=u.getTelefono()%>"
                                       class="form-control">
                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Correo</label>
                                <input type="email" name="txtCorreo"
                                       value="<%=u.getCorreo()%>"
                                       class="form-control">
                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Rol</label>
                                <select name="cmbRol" class="form-select" required>
                                    <% for(BeanRol r : listaRoles){ %>
                                        <option value="<%=r.getIdRol()%>"
                                        <%= (r.getIdRol() == u.getIdRol()) ? "selected" : "" %>>
                                            <%=r.getNombreRol()%>
                                        </option>
                                    <% } %>
                                </select>
                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Estado</label>
                                <select name="cmbEstado" class="form-select">
                                    <option value="ACTIVO"
                                    <%= u.getEstado().equals("ACTIVO") ? "selected" : "" %>>
                                    ACTIVO
                                    </option>
                                    <option value="INACTIVO"
                                    <%= u.getEstado().equals("INACTIVO") ? "selected" : "" %>>
                                    INACTIVO
                                    </option>
                                </select>
                            </div>

                        </div>

                        <button type="submit" class="btn btn-warning">Actualizar</button>
                        <a href="bandejaUsuario.jsp" class="btn btn-secondary">Volver</a>

                    </form>

                </div>
            </div>

        </div><!-- cierra col-md-10 abierto por header.jsp -->
    </div>

<%
String tipo = (String) session.getAttribute("tipo");
String mensaje = (String) session.getAttribute("mensaje");

if (mensaje != null) {
    String mensajeJs = mensaje
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", " ");
%>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
Swal.fire({
    icon: "<%=tipo%>",
    title: "Sistema",
    text: "<%=mensajeJs%>",
    confirmButtonText: "Aceptar"
});
</script>
<%
session.removeAttribute("tipo");
session.removeAttribute("mensaje");
}
%>
</body>
</html>