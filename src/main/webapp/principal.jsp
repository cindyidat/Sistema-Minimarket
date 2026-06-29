<%@ page language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<%@page import="bean.BeanUsuario"%>
<%@page import="mysql.Sql_Dashboard"%>

<%
BeanUsuario usuarioSesion =
(BeanUsuario)session.getAttribute("usuario");

if(usuarioSesion==null){
    response.sendRedirect("login.jsp");
    return;
}
int rolActual      = usuarioSesion.getIdRol();
boolean esAdmin      = (rolActual == 1);
boolean esAdminOJefe = (rolActual == 1 || rolActual == 2);

// Si el rol no ve las tarjetas extra (Proveedores/Usuarios/Ventas/Monto),
// las tarjetas fijas (Productos, Stock Bajo) se ven solas en su fila,
// asi que se ensanchan para no dejar espacio vacio.
String claseColFija =
        esAdminOJefe ? "col-md-4 col-sm-6 mb-4" : "col-md-6 mb-4";

Sql_Dashboard dashboardPrincipal =
new Sql_Dashboard();

int totalProductos =
dashboardPrincipal.totalProductos();

int totalProveedores =
dashboardPrincipal.totalProveedores();

int totalUsuarios =
dashboardPrincipal.totalUsuarios();

int totalVentas =
dashboardPrincipal.totalVentas();

double montoVendido =
dashboardPrincipal.montoVendido();

int stockBajoPrincipal =
dashboardPrincipal.stockBajo();

int porVencerPrincipal =
dashboardPrincipal.productosPorVencer();

int vencidosPrincipal =
dashboardPrincipal.productosVencidos();

request.setAttribute("paginaActiva", "inicio");
request.setAttribute("tituloPagina", "Panel Administrativo");
%>

<!DOCTYPE tml>
<html>
<head>

<meta charset="ISO-8859-1">

<title>Panel Principal</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
rel="stylesheet">
<link rel="stylesheet"
href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link href="css/estilo.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

</head>

<body>

<div class="container-fluid">

    <div class="row">

        <%@ include file="header.jsp" %>

            <%
            if(stockBajoPrincipal > 0){
            %>

            <div class="alert alert-danger">

                 Hay
                <strong><%=stockBajoPrincipal%></strong>
                productos con stock bajo.

            </div>

            <%
            }
            %>

            <%
            if(porVencerPrincipal > 0){
            %>

            <div class="alert alert-warning">

                 Hay
                <strong><%=porVencerPrincipal%></strong>
                lotes proximos a vencer.

            </div>

            <%
            }
            %>

            <%
            if(vencidosPrincipal > 0){
            %>

            <div class="alert alert-dark">

                Hay
                <strong><%=vencidosPrincipal%></strong>
                lotes vencidos.

            </div>

            <%
            }
            %>

            <div class="row justify-content-center">

                <div class="<%=claseColFija%>">

                    <div class="card card-dashboard">

                        <div class="card-body d-flex align-items-center gap-3">

                            <div class="icon-circle icon-success">
                                <i class="bi bi-box-seam-fill"></i>
                            </div>

                            <div>
                                <h6>Total Productos</h6>
                                <div class="numero text-success">
                                    <%=totalProductos%>
                                </div>
                            </div>

                        </div>

                    </div>

                </div>

                <% if(esAdminOJefe) { %>
                <div class="col-md-4 col-sm-6 mb-4">

                    <div class="card card-dashboard">

                        <div class="card-body d-flex align-items-center gap-3">

                            <div class="icon-circle icon-primary">
                                <i class="bi bi-truck"></i>
                            </div>

                            <div>
                                <h6>Total Proveedores</h6>
                                <div class="numero text-primary">
                                    <%=totalProveedores%>
                                </div>
                            </div>

                        </div>

                    </div>

                </div>
                <% } %>

                <% if(esAdmin) { %>
                <div class="col-md-4 col-sm-6 mb-4">

                    <div class="card card-dashboard">

                        <div class="card-body d-flex align-items-center gap-3">

                            <div class="icon-circle icon-dark">
                                <i class="bi bi-people-fill"></i>
                            </div>

                            <div>
                                <h6>Total Usuarios</h6>
                                <div class="numero text-dark">
                                    <%=totalUsuarios%>
                                </div>
                            </div>

                        </div>

                    </div>

                </div>
                <% } %>

            </div>

            <div class="row justify-content-center">

                <% if(esAdminOJefe) { %>
                <div class="col-md-4 col-sm-6 mb-4">

                    <div class="card card-dashboard">

                        <div class="card-body d-flex align-items-center gap-3">

                            <div class="icon-circle icon-primary">
                                <i class="bi bi-cart-check-fill"></i>
                            </div>

                            <div>
                                <h6>Total Ventas</h6>
                                <div class="numero" style="color:var(--azul);">
                                    <%=totalVentas%>
                                </div>
                            </div>

                        </div>

                    </div>

                </div>

                <div class="col-md-4 col-sm-6 mb-4">

                    <div class="card card-dashboard">

                        <div class="card-body d-flex align-items-center gap-3">

                            <div class="icon-circle icon-success">
                                <i class="bi bi-cash-coin"></i>
                            </div>

                            <div>
                                <h6>Monto Vendido</h6>
                                <div class="numero text-success">
                                    S/ <%=String.format("%.2f", montoVendido)%>
                                </div>
                            </div>

                        </div>

                    </div>

                </div>
                <% } %>

                <div class="<%=claseColFija%>">

                    <div class="card card-dashboard">

                        <div class="card-body d-flex align-items-center gap-3">

                            <div class="icon-circle icon-danger">
                                <i class="bi bi-exclamation-triangle-fill"></i>
                            </div>

                            <div>
                                <h6>Productos con Stock Bajo</h6>
                                <div class="numero text-danger">
                                    <%=stockBajoPrincipal%>
                                </div>
                            </div>

                        </div>

                    </div>

                </div>

            </div>

            <!-- NUEVO MODULO FEFO -->

            <div class="row">

                <div class="col-md-4 mb-4">

                    <div class="card card-dashboard">

                        <div class="card-body text-center">

                            <div class="icon-circle icon-primary mx-auto mb-2">
                                <i class="bi bi-archive-fill"></i>
                            </div>

                            <h5>Control FEFO</h5>

                            <p>
                                Gestion de lotes por fecha de vencimiento.
                            </p>

                            <a href="bandejaLote.jsp"
                               class="btn btn-success">

                                Ver Lotes

                            </a>

                        </div>

                    </div>

                </div>

                <div class="col-md-4 mb-4">

                    <div class="card card-dashboard">

                        <div class="card-body text-center">

                            <div class="icon-circle icon-warning mx-auto mb-2">
                                <i class="bi bi-clock-history"></i>
                            </div>

                            <h5>Productos por Vencer</h5>

                            <p>
                                Alertas de productos proximos a vencer.
                            </p>

                            <a href="bandejaVencimientos.jsp"
                               class="btn btn-warning">

                                Ver Alertas

                            </a>

                        </div>

                    </div>

                </div>

                <div class="col-md-4 mb-4">

                    <div class="card card-dashboard">

                        <div class="card-body text-center">

                            <div class="icon-circle icon-danger mx-auto mb-2">
                                <i class="bi bi-trash-fill"></i>
                            </div>

                            <h5>Control de Mermas</h5>

                            <p>
                                Registro de productos vencidos o danados.
                            </p>

                            <a href="bandejaMerma.jsp"
                               class="btn btn-danger">

                                Ver Mermas

                            </a>

                        </div>

                    </div>

                </div>

            </div>

            <div class="card card-resumen">

                <div class="card-body">

                    <h4>

                        Resumen General

                    </h4>

                    <hr>

                    <p>

                        Sistema de Gestion para Minimarket.
                        Desde este panel puede administrar
                        productos, proveedores, inventario,
                        ventas, control FEFO, lotes,
                        productos proximos a vencer
                        y gestion de mermas.

                    </p>

                </div>

            </div>

        </div><!-- cierra col-md-10 abierto por header.jsp -->

    </div>
<%
String tipoAcceso = (String) session.getAttribute("tipoAcceso");
String mensajeAcceso = (String) session.getAttribute("mensajeAcceso");

if (mensajeAcceso != null) {
    String mensajeAccesoJs = mensajeAcceso
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", " ");
%>

<script>
Swal.fire({
    icon: "<%=tipoAcceso%>",
    title: "Control de Acceso",
    text: "<%=mensajeAccesoJs%>",
    confirmButtonText: "Aceptar"
});
</script>

<%
session.removeAttribute("tipoAcceso");
session.removeAttribute("mensajeAcceso");
}
%>


</body>
</html>