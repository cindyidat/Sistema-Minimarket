<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<%@page import="bean.BeanProducto"%>
<%@page import="mysql.Sql_Producto"%>

<%
int id =
Integer.parseInt(
request.getParameter("id"));

Sql_Producto sql =
new Sql_Producto();

BeanProducto p =
sql.buscarProducto(id);

request.setAttribute("paginaActiva", "productos");
request.setAttribute("tituloPagina", "Editar Producto");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Actualizar Producto</title>
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
                        <h4 class="mb-0">Editar Producto</h4>
                    </div>

                    <hr>

                    <form action="ServletProductoActualizar" method="post">

                        <input type="hidden" name="txtId" value="<%=p.getIdProducto()%>">

                        <div class="row">

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Nombre</label>
                                <input type="text" name="txtNombre"
                                       value="<%=p.getNombreProducto()%>"
                                       class="form-control">
                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Descripcion</label>
                                <input type="text" name="txtDescripcion"
                                       value="<%=p.getDescripcion()%>"
                                       class="form-control">
                            </div>

                            <div class="col-md-3 mb-3">
                                <label class="form-label">Precio Compra</label>
                                <input type="number" step="0.01" name="txtPrecioCompra"
                                       value="<%=p.getPrecioCompra()%>"
                                       class="form-control">
                            </div>

                            <div class="col-md-3 mb-3">
                                <label class="form-label">Precio Venta</label>
                                <input type="number" step="0.01" name="txtPrecioVenta"
                                       value="<%=p.getPrecioVenta()%>"
                                       class="form-control">
                            </div>

                            <div class="col-md-3 mb-3">
                                <label class="form-label">Stock</label>
                                <input type="number" name="txtStock"
                                       value="<%=p.getStock()%>"
                                       class="form-control">
                            </div>

                            <div class="col-md-3 mb-3">
                                <label class="form-label">Stock Minimo</label>
                                <input type="number" name="txtStockMinimo"
                                       value="<%=p.getStockMinimo()%>"
                                       class="form-control">
                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Categoria</label>
                                <input type="number" name="cmbCategoria"
                                       value="<%=p.getIdCategoria()%>"
                                       class="form-control">
                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Proveedor</label>
                                <input type="number" name="cmbProveedor"
                                       value="<%=p.getIdProveedor()%>"
                                       class="form-control">
                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Estado</label>
                                <select name="cmbEstado" class="form-select">
                                    <option value="ACTIVO"
                                    <%= p.getEstado().equals("ACTIVO") ? "selected" : "" %>>
                                    ACTIVO
                                    </option>
                                    <option value="INACTIVO"
                                    <%= p.getEstado().equals("INACTIVO") ? "selected" : "" %>>
                                    INACTIVO
                                    </option>
                                </select>
                            </div>

                        </div>

                        <button type="submit" class="btn btn-warning">Actualizar</button>
                        <a href="bandejaProducto.jsp" class="btn btn-secondary">Volver</a>

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