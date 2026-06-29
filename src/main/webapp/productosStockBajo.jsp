<%@ page language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<%@page import="java.util.ArrayList"%>
<%@page import="bean.BeanProducto"%>
<%@page import="mysql.Sql_Producto"%>

<%
Sql_Producto sql =
new Sql_Producto();

ArrayList<BeanProducto> lista =
sql.listarProductosStockBajo();

request.setAttribute("paginaActiva", "stockBajo");
request.setAttribute("tituloPagina", "Panel Administrativo");
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">

<title>Productos con Stock Bajo</title>

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

            <div class="card card-resumen" style="border-top:4px solid #dc3545;">

                <div class="card-body">

                    <h4 class="mb-3 text-danger">
                        <i class="bi bi-exclamation-triangle-fill me-2"></i>
                        Productos con Stock Bajo
                    </h4>

                    <% if(lista.size() == 0){ %>

                    <div class="alert alert-success">
                        No existen productos con stock bajo.
                    </div>

                    <% } else { %>

                    <div class="alert alert-danger d-flex align-items-center">
                        <i class="bi bi-info-circle-fill me-2"></i>
                        <div>
                            Se encontraron
                            <strong><%=lista.size()%></strong>
                            productos con stock bajo. Considera reabastecerlos pronto.
                        </div>
                    </div>

                    <hr>

                    <div class="table-responsive">
                        <table class="table table-bordered table-hover align-middle">

                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Producto</th>
                                    <th>Precio Venta</th>
                                    <th>Stock Actual</th>
                                    <th>Stock Minimo</th>
                                    <th>Estado</th>
                                </tr>
                            </thead>

                            <tbody>

                            <%
                            for(BeanProducto p : lista){
                            %>

                            <tr>
                                <td><%=p.getIdProducto()%></td>
                                <td><%=p.getNombreProducto()%></td>
                                <td>S/ <%=p.getPrecioVenta()%></td>
                                <td class="text-danger fw-bold"><%=p.getStock()%></td>
                                <td><%=p.getStockMinimo()%></td>
                                <td>
                                    <span class="badge bg-danger">
                                        STOCK BAJO
                                    </span>
                                </td>
                            </tr>

                            <%
                            }
                            %>

                            </tbody>

                        </table>
                    </div>

                    <% } %>

                    <a href="reporteInventario.jsp"
                       class="btn btn-secondary">
                        Volver
                    </a>

                </div>

            </div>

        </div><!-- cierra col-md-10 abierto por header.jsp -->
    </div>


</body>
</html>