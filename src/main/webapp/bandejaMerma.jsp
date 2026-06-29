<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.BeanMerma"%>
<%@page import="mysql.Sql_Merma"%>

<%
Sql_Merma sql = new Sql_Merma();
ArrayList<BeanMerma> lista = sql.listarMermas();

request.setAttribute("paginaActiva", "mermas");
request.setAttribute("tituloPagina", "Panel Administrativo");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Gestión de Mermas</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link href="css/estilo.css" rel="stylesheet">

</head>

<body>

<div class="container-fluid">
    <div class="row">

        <%@ include file="header.jsp" %>

            <div class="card card-resumen" style="border-top:4px solid #dc3545;">
                <div class="card-body">

                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h4 class="mb-0 text-danger">
                            <i class="bi bi-trash-fill me-2"></i>
                            Gestión de Mermas
                        </h4>
                        <a href="registrarMerma.jsp" class="btn btn-danger">
                            <i class="bi bi-plus-circle-fill me-1"></i>
                            Nueva Merma
                        </a>
                    </div>

                    <hr>

                    <div class="table-responsive">
                        <table class="table table-bordered table-hover align-middle">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>ID Producto</th>
                                    <th>Cantidad</th>
                                    <th>Motivo</th>
                                    <th>Fecha</th>
                                </tr>
                            </thead>
                            <tbody>
                            <% for(BeanMerma m : lista){ %>
                                <tr>
                                    <td><%=m.getIdMerma()%></td>
                                    <td><%=m.getIdProducto()%></td>
                                    <td><%=m.getCantidad()%></td>
                                    <td><%=m.getMotivo()%></td>
                                    <td><%=m.getFecha()%></td>
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