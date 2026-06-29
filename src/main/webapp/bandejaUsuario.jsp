<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.BeanUsuario"%>
<%@page import="mysql.Sql_Usuario"%>

<%
Sql_Usuario sql = new Sql_Usuario();
ArrayList<BeanUsuario> lista = sql.listarUsuarios();

request.setAttribute("paginaActiva", "usuarios");
request.setAttribute("tituloPagina", "Panel Administrativo");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Usuarios</title>
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
                        <h4 class="mb-0">Gestión de Trabajadores</h4>
                        <a href="regUsuario.jsp" class="btn btn-success">
                            <i class="bi bi-plus-circle-fill me-1"></i>
                            Nuevo Trabajador
                        </a>
                    </div>

                    <hr>

                    <div class="table-responsive">
                        <table class="table table-bordered table-hover align-middle">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>DNI</th>
                                    <th>Usuario</th>
                                    <th>Rol</th>
                                    <th>Teléfono</th>
                                    <th>Correo</th>
                                    <th>Estado</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                            <% for(BeanUsuario u : lista){ %>
                                <tr>
                                    <td><%=u.getIdUsuario()%></td>
                                    <td><%=u.getNombre()%></td>
                                    <td><%=u.getDni()%></td>
                                    <td><%=u.getUsuario()%></td>
                                    <td><%=u.getNombreRol()%></td>
                                    <td><%=u.getTelefono()%></td>
                                    <td><%=u.getCorreo()%></td>
                                    <td><%=u.getEstado()%></td>
			                    <td>
								    <a href="actUsuario.jsp?id=<%=u.getIdUsuario()%>" class="btn btn-warning btn-sm">Editar</a>
								    <a href="contratoUsuario.jsp?id=<%=u.getIdUsuario()%>" class="btn btn-info btn-sm">Contrato</a>
								    <a href="ServletUsuarioEliminar?id=<%=u.getIdUsuario()%>"
								       class="btn btn-danger btn-sm"
								       onclick="return confirm('Desea eliminar este trabajador?');">Eliminar</a>
								</td>
								  </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>

                    <a href="principal.jsp" class="btn btn-secondary">Volver</a>

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