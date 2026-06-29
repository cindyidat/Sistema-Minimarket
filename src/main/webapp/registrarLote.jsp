<%@ page language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<%
request.setAttribute("paginaActiva", "lotes");
request.setAttribute("tituloPagina", "Panel Administrativo");
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">

<title>Registrar Lote</title>

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

                    <h4 class="mb-3">Registrar Lote</h4>
                    <hr>

                    <form action="ServletLote"
                          method="post">

                        <div class="row">

                            <div class="col-md-6 mb-3 position-relative">
                                <label class="form-label">Producto</label>

                                <input type="text"
                                       id="buscarProducto"
                                       class="form-control"
                                       placeholder="Escribe el nombre del producto..."
                                       autocomplete="off"
                                       required>

                                <input type="hidden"
                                       id="idProductoSeleccionado"
                                       name="cmbProducto">

                                <div id="sugerenciasProducto"
                                     class="list-group"
                                     style="position:absolute; z-index:1000; width:100%;">
                                </div>

                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Cantidad</label>
                                <input type="number"
                                       name="txtCantidad"
                                       min="1"
                                       class="form-control"
                                       required>
                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Fecha Ingreso</label>
                                <input type="date"
                                       name="txtFechaIngreso"
                                       class="form-control"
                                       required>
                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Fecha Vencimiento</label>
                                <input type="date"
                                       name="txtFechaVencimiento"
                                       class="form-control"
                                       required>
                            </div>

                        </div>

                        <button type="submit"
                                class="btn btn-success">
                            Guardar
                        </button>

                        <a href="bandejaLote.jsp"
                           class="btn btn-secondary">
                            Volver
                        </a>

                    </form>

                </div>

            </div>

        </div><!-- cierra col-md-10 abierto por header.jsp -->
    </div>


<script src="js/autocompleteProducto.js"></script>
<script>

    initAutocompleteProducto({
        inputId: "buscarProducto",
        hiddenId: "idProductoSeleccionado",
        sugerenciasId: "sugerenciasProducto"
    });

    document.querySelector("form").addEventListener("submit", function (e) {

        var idProducto = document.getElementById("idProductoSeleccionado").value;

        if (idProducto === "") {
            e.preventDefault();
            alert("Por favor selecciona un producto de la lista de sugerencias.");
        }
    });

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