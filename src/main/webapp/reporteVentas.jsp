<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.BeanVenta"%>
<%@page import="mysql.Sql_Venta"%>

<%
int filasPorPagina = 14;

String paramPagina = request.getParameter("pagina");
int paginaActual = (paramPagina != null) ? Integer.parseInt(paramPagina) : 1;

Sql_Venta sql = new Sql_Venta();

int totalVentas = sql.contarVentas();
int totalPaginas = (int) Math.ceil((double) totalVentas / filasPorPagina);
int offset = (paginaActual - 1) * filasPorPagina;

ArrayList<BeanVenta> lista = sql.listarVentasPaginado(offset, filasPorPagina);

request.setAttribute("paginaActiva", "reporteVentas");
request.setAttribute("tituloPagina", "Panel Administrativo");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Reporte de Ventas</title>
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
                        <h4 class="mb-0">Reporte de Ventas</h4>
                        <a href="ventaDetalle.jsp" class="btn btn-success">
                            <i class="bi bi-plus-circle-fill me-1"></i>
                            Nueva Venta
                        </a>
                    </div>

                    <hr>

                    <div class="table-responsive">
                        <table class="table table-bordered table-hover align-middle">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Fecha</th>
                                    <th>Total</th>
                                    <th>Usuario</th>
                                </tr>
                            </thead>
                            <tbody>
                            <% for(BeanVenta v : lista){ %>
                                <tr>
                                    <td><%=v.getIdVenta()%></td>
                                    <td><%=v.getFechaVenta()%></td>
                                    <td>S/ <%=String.format("%.2f", v.getTotal())%></td>
                                    <td><%=v.getIdUsuario()%></td>
                                </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>

                    <!-- Paginación -->
                    <nav>
                        <ul class="pagination justify-content-center mt-3">

                            <li class="page-item <%= (paginaActual == 1) ? "disabled" : "" %>">
                                <a class="page-link" href="reporteVentas.jsp?pagina=<%=paginaActual - 1%>">Anterior</a>
                            </li>

                            <% for(int i = 1; i <= totalPaginas; i++){ %>
                                <li class="page-item <%= (i == paginaActual) ? "active" : "" %>">
                                    <a class="page-link" href="reporteVentas.jsp?pagina=<%=i%>"><%=i%></a>
                                </li>
                            <% } %>

                            <li class="page-item <%= (paginaActual == totalPaginas) ? "disabled" : "" %>">
                                <a class="page-link" href="reporteVentas.jsp?pagina=<%=paginaActual + 1%>">Siguiente</a>
                            </li>

                        </ul>
                    </nav>

                </div>
            </div>

        </div><!-- cierra col-md-10 abierto por header.jsp -->
    </div>
</div>

</body>
</html>