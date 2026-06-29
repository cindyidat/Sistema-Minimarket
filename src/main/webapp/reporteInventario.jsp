<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.BeanProducto"%>
<%@page import="mysql.Sql_Producto"%>

<%
Sql_Producto sql = new Sql_Producto();
ArrayList<BeanProducto> lista = sql.listarProductos();

request.setAttribute("paginaActiva", "inventario");
request.setAttribute("tituloPagina", "Panel Administrativo");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Reporte de Inventario</title>
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
                        <h4 class="mb-0">Reporte de Inventario</h4>
                    </div>

                    <hr>

                    <div class="table-responsive">
                        <table class="table table-bordered table-hover align-middle">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Producto</th>
                                    <th>Stock</th>
                                    <th>Stock Mínimo</th>
                                    <th>Estado</th>
                                </tr>
                            </thead>
                            <tbody>
                            <% for(BeanProducto p : lista){ %>
                                <tr>
                                    <td><%=p.getIdProducto()%></td>
                                    <td><%=p.getNombreProducto()%></td>
                                    <td><%=p.getStock()%></td>
                                    <td><%=p.getStockMinimo()%></td>
                                    <td><%=p.getEstado()%></td>
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

</body>
</html>