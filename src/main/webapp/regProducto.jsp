<%@ page language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.BeanCategoria"%>
<%@page import="bean.BeanProveedor"%>
<%@page import="mysql.Sql_Categoria"%>
<%@page import="mysql.Sql_Proveedor"%>

<%
request.setAttribute("paginaActiva", "productos");
request.setAttribute("tituloPagina", "Panel Administrativo");

Sql_Categoria sqlCat = new Sql_Categoria();
ArrayList<BeanCategoria> categorias = sqlCat.listarCategorias();

Sql_Proveedor sqlProv = new Sql_Proveedor();
ArrayList<BeanProveedor> proveedores = sqlProv.listarProveedores();
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
<title>Registrar Producto</title>

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

                    <h4 class="mb-3">Registrar Producto</h4>
                    <hr>

                    <form action="ServletProductoAgregar" method="post">

                        <div class="row">

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Nombre</label>
                                <input type="text"
                                       name="txtNombre"
                                       class="form-control"
                                       required>
                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Descripcion</label>
                                <input type="text"
                                       name="txtDescripcion"
                                       class="form-control"
                                       required>
                            </div>

                            <div class="col-md-3 mb-3">
                                <label class="form-label">Precio Compra</label>
                                <input type="number"
                                       step="0.01"
                                       name="txtPrecioCompra"
                                       class="form-control"
                                       required>
                            </div>

                            <div class="col-md-3 mb-3">
                                <label class="form-label">Precio Venta</label>
                                <input type="number"
                                       step="0.01"
                                       name="txtPrecioVenta"
                                       class="form-control"
                                       required>
                            </div>

                            <div class="col-md-3 mb-3">
                                <label class="form-label">Stock</label>
                                <input type="number"
                                       name="txtStock"
                                       class="form-control"
                                       required>
                            </div>

                            <div class="col-md-3 mb-3">
                                <label class="form-label">Stock Minimo</label>
                                <input type="number"
                                       name="txtStockMinimo"
                                       class="form-control"
                                       required>
                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Categoria</label>
                                <select name="cmbCategoria" class="form-select" required>
                                    <option value="">-- Seleccione --</option>
                                    <% for(BeanCategoria c : categorias){ %>
                                    <option value="<%=c.getIdCategoria()%>">
                                        <%=c.getNombreCategoria()%>
                                    </option>
                                    <% } %>
                                </select>
                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Proveedor</label>
                                <select name="cmbProveedor" class="form-select" required>
                                    <option value="">-- Seleccione --</option>
                                    <% for(BeanProveedor p : proveedores){ %>
                                    <option value="<%=p.getIdProveedor()%>">
                                        <%=p.getRazonSocial()%>
                                    </option>
                                    <% } %>
                                </select>
                            </div>

                        </div>

                        <button type="submit" class="btn btn-success">
                            Guardar
                        </button>

                        <a href="bandejaProducto.jsp" class="btn btn-secondary">
                            Volver
                        </a>

                    </form>

                </div>
            </div>

        </div>
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