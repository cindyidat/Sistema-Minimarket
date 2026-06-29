<%@ page language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<%
request.setAttribute("paginaActiva", "mermas");
request.setAttribute("tituloPagina", "Panel Administrativo");
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">

<title>Registrar Merma</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
rel="stylesheet">
<link rel="stylesheet"
href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="js/validarPinSupervisor.js"></script>

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
                        Registrar Merma
                    </h4>

                    <div class="alert alert-danger d-flex align-items-center" role="alert">
                        <i class="bi bi-info-circle-fill me-2"></i>
                        <div>
                            Esta acción registra una <strong>pérdida de inventario</strong>
                            (producto vencido, dañado o no apto para la venta).
                            El stock se descontará de forma permanente.
                        </div>
                    </div>

                    <hr>

                    <form action="ServletMerma"
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
                                <label class="form-label">Motivo</label>
                                <select name="txtMotivo"
                                        id="cmbMotivo"
                                        class="form-select"
                                        required>
                                    <option value="" selected disabled>
                                        Selecciona un motivo...
                                    </option>
                                    <option value="Vencido">Vencido</option>
                                    <option value="Dañado">Dañado</option>
                                    <option value="Robo">Robo</option>
                                    <option value="Faltante sin explicacion">
                                        Faltante sin explicación
                                    </option>
                                </select>
                                <small class="text-muted">
                                    "Robo" y "Faltante sin explicación" requieren
                                    autorización del Jefe de Inventario.
                                </small>
                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Fecha</label>
                                <input type="date"
                                       name="txtFecha"
                                       class="form-control"
                                       required>
                            </div>

                        </div>

                        <button type="submit"
                                class="btn btn-danger"
                                onclick="return confirm('¿Confirma el registro de esta merma? Esta acción no se puede deshacer.');">
                            <i class="bi bi-trash-fill me-1"></i>
                            Guardar
                        </button>

                        <a href="bandejaMerma.jsp"
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

    // Motivos que requieren autorizacion del Jefe de Inventario
    // porque no dejan evidencia fisica verificable (a diferencia de
    // un producto vencido o danado, que cualquiera puede confirmar
    // con solo verlo).
    var MOTIVOS_SENSIBLES = ["Robo", "Faltante sin explicacion"];

    var formMerma = document.querySelector("form");

    formMerma.addEventListener("submit", function (e) {

        var idProducto = document.getElementById("idProductoSeleccionado").value;

        if (idProducto === "") {
            e.preventDefault();
            alert("Por favor selecciona un producto de la lista de sugerencias.");
            return;
        }

        var motivo = document.getElementById("cmbMotivo").value;

        if (MOTIVOS_SENSIBLES.indexOf(motivo) !== -1
                && !formMerma.dataset.autorizado) {

            e.preventDefault();

            solicitarPinSupervisor(function (nombreSupervisor) {

                // El PIN ya fue validado en el servidor por
                // ServletValidarPin, que ademas dejo la
                // autorizacion guardada en la sesion. Aqui solo
                // se reenvia el formulario; ServletMerma es quien
                // vuelve a comprobar esa autorizacion del lado
                // del servidor antes de guardar la merma.
                formMerma.dataset.autorizado = "true";
                formMerma.requestSubmit();
            });
        }
    });

</script>

</body>
</html>