<%@ page language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<%@page import="bean.BeanProveedor"%>
<%@page import="mysql.Sql_Proveedor"%>

<%
int id =
Integer.parseInt(
request.getParameter("id"));

Sql_Proveedor sql =
new Sql_Proveedor();

BeanProveedor p =
sql.buscarProveedor(id);

request.setAttribute("paginaActiva", "proveedores");
request.setAttribute("tituloPagina", "Editar Proveedor");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Editar Proveedor</title>
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
                        <h4 class="mb-0">Editar Proveedor</h4>
                    </div>

                    <hr>

                    <form action="ServletProveedorActualizar" method="post">

                        <input type="hidden" name="txtId" value="<%=p.getIdProveedor()%>">

                        <div class="mb-3">
                            <label class="form-label">Razon Social</label>
                            <input type="text" name="txtRazonSocial"
                                   value="<%=p.getRazonSocial()%>"
                                   class="form-control"
                                   required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">RUC</label>
                            <input type="text" name="txtRuc"
                                   value="<%=p.getRuc()%>"
                                   class="form-control"
                                   maxlength="11"
                                   required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Direccion</label>
                            <input type="text" name="txtDireccion"
                                   value="<%=p.getDireccion()%>"
                                   class="form-control"
                                   required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Telefono</label>
                            <input type="text" name="txtTelefono"
                                   value="<%=p.getTelefono()%>"
                                   class="form-control"
                                   required>
                        </div>

                        <button type="submit" class="btn btn-warning">Actualizar</button>
                        <a href="bandejaProveedor.jsp" class="btn btn-secondary">Volver</a>

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