<%@ page language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<%@page import="bean.BeanUsuario"%>
<%@page import="mysql.Sql_Dashboard"%>

<%
BeanUsuario usuario =
(BeanUsuario)session.getAttribute("usuario");

if(usuario==null){
    response.sendRedirect("login.jsp");
    return;
}

Sql_Dashboard dashboard =
new Sql_Dashboard();

int stockBajo =
dashboard.stockBajo();

int porVencer =
dashboard.productosPorVencer();

int vencidos =
dashboard.productosVencidos();

int totalNotificaciones =
dashboard.totalNotificaciones();

String paginaActiva =
(request.getAttribute("paginaActiva") != null)
? (String) request.getAttribute("paginaActiva") : "";

String tituloPagina =
(request.getAttribute("tituloPagina") != null)
? (String) request.getAttribute("tituloPagina") : "Panel Administrativo";

String subtituloPagina =
(request.getAttribute("subtituloPagina") != null)
? (String) request.getAttribute("subtituloPagina") : "Tienda Mass - Sistema de Gestion FEFO";
%>

<div class="col-md-2 sidebar">

    <h3 class="p-3">
        Minimarket
    </h3>

    <a href="principal.jsp" class="<%= paginaActiva.equals("inicio") ? "active" : "" %>">
        <i class="bi bi-house-door-fill"></i>
        Inicio
    </a>

    <a href="bandejaProducto.jsp" class="<%= paginaActiva.equals("productos") ? "active" : "" %>">
        <i class="bi bi-box-seam-fill"></i>
        Productos
    </a>

    <a href="bandejaProveedor.jsp" class="<%= paginaActiva.equals("proveedores") ? "active" : "" %>">
        <i class="bi bi-truck"></i>
        Proveedores
    </a>

    <a href="reporteInventario.jsp" class="<%= paginaActiva.equals("inventario") ? "active" : "" %>">
        <i class="bi bi-clipboard-data-fill"></i>
        Inventario
    </a>

    <a href="productosStockBajo.jsp" class="<%= paginaActiva.equals("stockBajo") ? "active" : "" %>">
        <i class="bi bi-exclamation-circle-fill"></i>
        Stock Bajo
    </a>

    <a href="bandejaLote.jsp" class="<%= paginaActiva.equals("lotes") ? "active" : "" %>">
        <i class="bi bi-archive-fill"></i>
        Gestion de Lotes
    </a>

    <a href="bandejaVencimientos.jsp" class="<%= paginaActiva.equals("vencimientos") ? "active" : "" %>">
        <i class="bi bi-exclamation-triangle-fill"></i>
        Productos por Vencer
    </a>

    <a href="bandejaMerma.jsp" class="<%= paginaActiva.equals("mermas") ? "active" : "" %>">
        <i class="bi bi-trash-fill"></i>
        Gestion de Mermas
    </a>

    <a href="ventaDetalle.jsp" class="<%= paginaActiva.equals("venta") ? "active" : "" %>">
        <i class="bi bi-cart-check-fill"></i>
        Registrar Venta
    </a>

    <a href="reporteVentas.jsp" class="<%= paginaActiva.equals("reporteVentas") ? "active" : "" %>">
        <i class="bi bi-graph-up-arrow"></i>
        Reporte Ventas
    </a>

    <a href="bandejaUsuario.jsp" class="<%= paginaActiva.equals("usuarios") ? "active" : "" %>">
        <i class="bi bi-people-fill"></i>
        Trabajadores
    </a>

    
    <a href="bandejaAuditoria.jsp" class="<%= paginaActiva.equals("auditoria") ? "active" : "" %>">
        <i class="bi bi-shield-check"></i>
        Auditoria
    </a>
   

    <a href="ServletCerrarSesion">
        <i class="bi bi-box-arrow-right"></i>
        Cerrar Sesion
    </a>

</div>

<div class="col-md-10 p-4">

    <div class="d-flex justify-content-between align-items-center header-bienvenida">

        <div>
            <h2>
                <%= tituloPagina %>
            </h2>
            <div class="subtitulo">
                <%= subtituloPagina %>
            </div>
        </div>

        <div class="dropdown">

            <button class="btn btn-notif position-relative" data-bs-toggle="dropdown">

                <i class="bi bi-bell-fill"></i>
                Notificaciones

                <% if(totalNotificaciones > 0){ %>

                <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                    <%=totalNotificaciones%>
                </span>

                <% } %>

            </button>

            <ul class="dropdown-menu dropdown-menu-end" style="min-width:350px;">

                <li>
                    <h6 class="dropdown-header">
                        Centro de Notificaciones
                    </h6>
                </li>

                <% if(stockBajo > 0){ %>

                <li>
                    <a class="dropdown-item text-danger" href="productosStockBajo.jsp">
                        <i class="bi bi-exclamation-circle-fill me-2"></i>
                        Existen
                        <strong><%=stockBajo%></strong>
                        productos con stock bajo
                    </a>
                </li>

                <% } %>

                <% if(porVencer > 0){ %>

                <li>
                    <a class="dropdown-item text-warning" href="bandejaVencimientos.jsp">
                        <i class="bi bi-clock-history me-2"></i>
                        Existen
                        <strong><%=porVencer%></strong>
                        lotes proximos a vencer
                    </a>
                </li>

                <% } %>

                <% if(vencidos > 0){ %>

                <li>
                    <a class="dropdown-item text-dark" href="bandejaMerma.jsp">
                        <i class="bi bi-x-circle-fill me-2"></i>
                        Existen
                        <strong><%=vencidos%></strong>
                        lotes vencidos
                    </a>
                </li>

                <% } %>

                <% if(totalNotificaciones == 0){ %>

                <li>
                    <span class="dropdown-item text-success">
                        No existen alertas pendientes
                    </span>
                </li>

                <% } %>

            </ul>

        </div>

    </div>

    <hr>