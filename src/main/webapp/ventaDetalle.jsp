<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.BeanCarrito"%>
<%@page import="bean.BeanProducto"%>
<%@page import="mysql.Sql_Producto"%>

<%
Sql_Producto sqlProducto = new Sql_Producto();
ArrayList<BeanProducto> productos = sqlProducto.listarProductosActivos();
ArrayList<BeanCarrito> carrito = (ArrayList<BeanCarrito>) session.getAttribute("carrito");

if(carrito == null){
    carrito = new ArrayList<BeanCarrito>();
}

// CALCULAR IGV POR TIPO DE PRODUCTO
double opGravada   = 0;
double opExonerada = 0;

for(BeanCarrito c : carrito){
    if("GRAVADO".equals(c.getTipoIgv())){
        opGravada   += c.getSubtotal();
    } else {
        opExonerada += c.getSubtotal();
    }
}

double igv   = Math.round(opGravada * 0.18 * 100.0) / 100.0;
double total = Math.round((opGravada + opExonerada + igv) * 100.0) / 100.0;

request.setAttribute("paginaActiva", "venta");
request.setAttribute("tituloPagina", "Panel Administrativo");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registro de Venta</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<link href="css/estilo.css" rel="stylesheet">
</head>

<body>

<div class="container-fluid">
    <div class="row">

        <%@ include file="header.jsp" %>

            <div class="card card-resumen">
                <div class="card-body">

                    <h4 class="mb-3">Registro de Venta</h4>
                    <hr>

                    <!-- FORMULARIO AGREGAR PRODUCTO -->
                    <form action="ServletAgregarDetalle" method="post">
                        <div class="row">

                            <div class="col-md-5 position-relative">
                                <label>Producto</label>
                                <input type="text"
                                       id="buscarProducto"
                                       class="form-control"
                                       placeholder="Escribe el nombre del producto..."
                                       autocomplete="off"
                                       required>
                                <input type="hidden"
                                       id="idProductoSeleccionado"
                                       name="idProducto">
                                <div id="sugerenciasProducto"
                                     class="list-group"
                                     style="position:absolute; z-index:1000; width:100%;">
                                </div>
                            </div>

                            <div class="col-md-3">
                                <label>Cantidad</label>
                                <input type="number"
                                       min="1"
                                       value=""
                                       name="cantidad"
                                       id="txtCantidad"
                                       class="form-control">
                            </div>

                            <div class="col-md-2">
                                <br>
                                <button type="submit" class="btn btn-success">Agregar</button>
                            </div>

                        </div>
                    </form>

                    <hr>

                    <!-- TABLA DEL CARRITO -->
                    <table class="table table-bordered table-hover align-middle">
                        <thead>
                            <tr>
                                <th>Producto</th>
                                <th>Cantidad</th>
                                <th>Precio</th>
                                <th>Subtotal</th>
                            </tr>
                        </thead>
                        <tbody>
                        <% for(BeanCarrito c : carrito){ %>
                            <tr>
                                <td><%=c.getNombreProducto()%></td>
                                <td><%=c.getCantidad()%></td>
                                <td>S/ <%=String.format("%.2f", c.getPrecio())%></td>
                                <td>S/ <%=String.format("%.2f", c.getSubtotal())%></td>
                            </tr>
                        <% } %>
                        </tbody>
                    </table>

                    <!-- RESUMEN IGV -->
                    <table class="table table-borderless w-auto ms-auto">
                        <tr>
                            <td>OP. Exonerada:</td>
                            <td class="text-end"><strong>S/ <%=String.format("%.2f", opExonerada)%></strong></td>
                        </tr>
                        <tr>
                            <td>OP. Gravada:</td>
                            <td class="text-end"><strong>S/ <%=String.format("%.2f", opGravada)%></strong></td>
                        </tr>
                        <tr>
                            <td>IGV (18%):</td>
                            <td class="text-end"><strong>S/ <%=String.format("%.2f", igv)%></strong></td>
                        </tr>
                        <tr class="border-top">
                            <td><h5>Total:</h5></td>
                            <td class="text-end"><h5>S/ <%=String.format("%.2f", total)%></h5></td>
                        </tr>
                    </table>

                    <!-- FORMULARIO CONFIRMAR VENTA -->
                    <form action="ServletConfirmarVenta" method="post">

                        <!-- Método de pago -->
                        <div class="row mb-3">
                            <div class="col-md-4">
                                <label><strong>Método de Pago:</strong></label>
                                <select name="metodoPago" id="metodoPago"
                                        class="form-select"
                                        onchange="mostrarEfectivo(this.value)">
                                    <option value="EFECTIVO"> Efectivo</option>
                                    <option value="YAPE">Yape</option>
                                    <option value="PLIN"> Plin</option>
                                    <option value="TARJETA_DEBITO"> Tarjeta Débito</option>
                                    <option value="TARJETA_CREDITO">Tarjeta Crédito</option>
                                </select>
                            </div>
                        </div>

                        <!-- Monto entregado y vuelto (solo efectivo) -->
                        <div id="divEfectivo" class="row mb-3">
                            <div class="col-md-3">
                                <label><strong>Monto entregado:</strong></label>
                                <input type="number"
							       name="montoEntrega"
							       id="montoEntrega"
							       class="form-control"
							       step="0.05"
							       min="0"
							       placeholder="S/ 0.00"
							       oninput="calcularVuelto(this.value)">
                            </div>
                            <div class="col-md-3 d-flex align-items-end">
                                <h5>Vuelto: S/ <span id="vuelto">0.00</span></h5>
                            </div>
                        </div>

                        <input type="hidden" id="totalHidden" value="<%=total%>">

                        <button class="btn btn-primary">Confirmar Venta</button>
                        <a href="principal.jsp" class="btn btn-secondary ms-2">Volver</a>

                    </form>

                </div>
            </div>

        </div>
    </div>

<script src="js/autocompleteProducto.js"></script>
<script>
    initAutocompleteProducto({
        inputId: "buscarProducto",
        hiddenId: "idProductoSeleccionado",
        sugerenciasId: "sugerenciasProducto"
    });

    document.querySelector("form[action='ServletAgregarDetalle']").addEventListener("submit", function (e) {
        var idProducto = document.getElementById("idProductoSeleccionado").value;
        if (idProducto === "") {
            e.preventDefault();
            alert("Por favor selecciona un producto de la lista de sugerencias.");
        }
    });

    window.addEventListener("load", function () {
        document.getElementById("buscarProducto").value = "";
        document.getElementById("idProductoSeleccionado").value = "";
        document.getElementById("txtCantidad").value = "";
    });

    function mostrarEfectivo(metodo) {
        document.getElementById("divEfectivo").style.display =
            metodo === "EFECTIVO" ? "flex" : "none";
    }

    function calcularVuelto(monto) {
        let total   = parseFloat(document.getElementById("totalHidden").value);
        let entrega = parseFloat(monto) || 0;
        let vuelto  = entrega - total;
        document.getElementById("vuelto").textContent =
            vuelto >= 0 ? vuelto.toFixed(2) : "0.00";
    }
</script>

<%
String tipo = (String) session.getAttribute("tipo");
String mensaje = (String) session.getAttribute("mensaje");

if (mensaje != null) {
    String mensajeJs = mensaje
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", " ");
%>

<script>
Swal.fire({
    icon: "<%=tipo%>",
    title: "Sistema de Ventas",
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