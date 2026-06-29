<%@ page language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<%@page import="java.util.ArrayList"%>
<%@page import="bean.BeanLote"%>
<%@page import="mysql.Sql_Lote"%>

<%
int filasPorPagina = 15;
String paramPagina = request.getParameter("pagina");
int paginaActual = (paramPagina != null) ? Integer.parseInt(paramPagina) : 1;

Sql_Lote sql = new Sql_Lote();
int totalLotes = sql.contarLotes();
int totalPaginas = (int) Math.ceil((double) totalLotes / filasPorPagina);
int offset = (paginaActual - 1) * filasPorPagina;

ArrayList<BeanLote> lista = sql.listarLotesPaginado(offset, filasPorPagina);

request.setAttribute("paginaActiva", "lotes");
request.setAttribute("tituloPagina", "Panel Administrativo");
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">

<title>Gestión de Lotes</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
rel="stylesheet">
<link rel="stylesheet"
href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
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

                        <h4 class="mb-0">
                            Gestión de Lotes
                        </h4>

                        <a href="registrarLote.jsp"
                           class="btn btn-success">

                            <i class="bi bi-plus-circle-fill me-1"></i>
                            Nuevo Lote

                        </a>

                    </div>

                    <hr>

                    <div class="table-responsive">

                        <table class="table table-bordered table-hover align-middle">

                            <thead>

                            <tr>

                                <th>ID Lote</th>
                                <th>Producto</th>
                                <th>Cantidad</th>
                                <th>Fecha Ingreso</th>
                                <th>Fecha Vencimiento</th>

                            </tr>

                            </thead>

                            <tbody>

                            <%

                            for(BeanLote l : lista){

                            %>

                            <tr>

                                <td><%=l.getIdLote()%></td>

                                <td><%=l.getNombreProducto()%></td>

                                <td><%=l.getCantidad()%></td>

                                <td><%=l.getFechaIngreso()%></td>

                                <td><%=l.getFechaVencimiento()%></td>

                            </tr>

                            <%

                            }

                            %>

                            </tbody>

                        </table>
                        <nav>
					    <ul class="pagination justify-content-center mt-3">
					
					        <li class="page-item <%= (paginaActual == 1) ? "disabled" : "" %>">
					            <a class="page-link" href="bandejaLote.jsp?pagina=<%=paginaActual - 1%>">Anterior</a>
					        </li>
					
					        <% for(int i = 1; i <= totalPaginas; i++){ %>
					            <li class="page-item <%= (i == paginaActual) ? "active" : "" %>">
					                <a class="page-link" href="bandejaLote.jsp?pagina=<%=i%>"><%=i%></a>
					            </li>
					        <% } %>
					
					        <li class="page-item <%= (paginaActual == totalPaginas) ? "disabled" : "" %>">
					            <a class="page-link" href="bandejaLote.jsp?pagina=<%=paginaActual + 1%>">Siguiente</a>
					        </li>
					
					    </ul>
					</nav>

                    </div>

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