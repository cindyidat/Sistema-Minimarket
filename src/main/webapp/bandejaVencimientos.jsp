<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.BeanLote"%>
<%@page import="mysql.Sql_Lote"%>

<%
Sql_Lote sql = new Sql_Lote();
ArrayList<BeanLote> lista = sql.productosPorVencer();

request.setAttribute("paginaActiva", "vencimientos");
request.setAttribute("tituloPagina", "Panel Administrativo");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Productos por Vencer</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link href="css/estilo.css" rel="stylesheet">
</head>

<body>

<div class="container-fluid">
    <div class="row">

        <%@ include file="header.jsp" %>

            <div class="card card-resumen" style="border-top:4px solid var(--amarillo);">
                <div class="card-body">

                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h4 class="mb-0 text-warning">
                            <i class="bi bi-clock-history me-2"></i>
                            Productos Próximos a Vencer
                        </h4>
                    </div>

                    <div class="alert alert-warning d-flex align-items-center" role="alert">
                        <i class="bi bi-exclamation-triangle-fill me-2"></i>
                        <div>
                            Estos productos vencerán pronto. Prioriza su venta o considera
                            promociones antes de que generen una <strong>merma</strong>.
                        </div>
                    </div>

                    <hr>

                    <div class="table-responsive">
                        <table class="table table-bordered table-hover align-middle">
                            <thead>
                                <tr>
                                    <th>ID Lote</th>
                                    <th>Producto</th>
                                    <th>Cantidad</th>
                                    <th>Fecha Vencimiento</th>
                                </tr>
                            </thead>
                            <tbody>
                            <% for(BeanLote l : lista){ %>
                                <tr>
                                    <td><%=l.getIdLote()%></td>
                                    <td><%=l.getNombreProducto()%></td>
                                    <td><%=l.getCantidad()%></td>
                                    <td><%=l.getFechaVencimiento()%></td>
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