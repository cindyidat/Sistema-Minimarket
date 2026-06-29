<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<%
request.setAttribute("paginaActiva", "proveedores");
request.setAttribute("tituloPagina", "Panel Administrativo");
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">

<title>Registrar Proveedor</title>

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

                    <h4 class="mb-3">Registrar Proveedor</h4>
                    <hr>

                    <form action="ServletProveedorAgregar" method="post">

                        <div class="mb-3">
                            <label>Razon Social</label>
                            <input type="text"
                                   name="txtRazonSocial"
                                   class="form-control"
                                   required>
                        </div>

                        <div class="mb-3">
                            <label>RUC</label>
                            <input type="text"
                                   name="txtRuc"
                                   class="form-control"
                                   required>
                        </div>

                        <div class="mb-3">
                            <label>Direccion</label>
                            <input type="text"
                                   name="txtDireccion"
                                   class="form-control"
                                   required>
                        </div>

                        <div class="mb-3">
                            <label>Telefono</label>
                            <input type="text"
                                   name="txtTelefono"
                                   class="form-control"
                                   required>
                        </div>

                        <button type="submit"
                                class="btn btn-success">
                            Guardar
                        </button>

                        <a href="bandejaProveedor.jsp"
                           class="btn btn-secondary">
                            Volver
                        </a>

                    </form>

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