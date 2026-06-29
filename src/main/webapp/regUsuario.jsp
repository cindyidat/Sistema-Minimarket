<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<%@page import="java.util.ArrayList"%>
<%@page import="bean.BeanRol"%>
<%@page import="mysql.Sql_Rol"%>

<%
Sql_Rol sqlRol = new Sql_Rol();
ArrayList<BeanRol> listaRoles = sqlRol.listarRoles();

request.setAttribute("paginaActiva", "usuarios");
request.setAttribute("tituloPagina", "Panel Administrativo");
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">

<title>Registrar Trabajador</title>

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

                    <h4 class="mb-3">Registrar Trabajador</h4>
                    <hr>

                    <form action="ServletUsuarioAgregar" method="post">

                        <div class="row">

                            <div class="col-md-6 mb-3">
                                <label>Nombre</label>
                                <input type="text"
                                       name="txtNombre"
                                       class="form-control"
                                       required>
                            </div>

                            <div class="col-md-6 mb-3">
                                <label>DNI</label>
                                <input type="text"
                                       name="txtDni"
                                       class="form-control"
                                       maxlength="8"
                                       required>
                            </div>

                            <div class="col-md-6 mb-3">
                                <label>Usuario</label>
                                <input type="text"
                                       name="txtUsuario"
                                       class="form-control"
                                       required>
                            </div>

                            <div class="col-md-6 mb-3">
                                <label>Clave</label>
                                <input type="password"
                                       name="txtClave"
                                       class="form-control"
                                       required>
                            </div>

                            <div class="col-md-6 mb-3">
                                <label>Teléfono</label>
                                <input type="text"
                                       name="txtTelefono"
                                       class="form-control">
                            </div>

                            <div class="col-md-6 mb-3">
                                <label>Correo</label>
                                <input type="email"
                                       name="txtCorreo"
                                       class="form-control">
                            </div>

                            <div class="col-md-6 mb-3">
                                <label>Rol</label>
                                <select name="cmbRol" class="form-select" required>
                                    <% for(BeanRol r : listaRoles){ %>
                                        <option value="<%=r.getIdRol()%>">
                                            <%=r.getNombreRol()%>
                                        </option>
                                    <% } %>
                                </select>
                            </div>

                            <div class="col-md-6 mb-3">
                                <label>Estado</label>
                                <select name="cmbEstado" class="form-select">
                                    <option value="ACTIVO">ACTIVO</option>
                                    <option value="INACTIVO">INACTIVO</option>
                                </select>
                            </div>

                        </div>

                        <button type="submit"
                                class="btn btn-success">
                            Guardar
                        </button>

                        <a href="bandejaUsuario.jsp"
                           class="btn btn-secondary">
                            Volver
                        </a>

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