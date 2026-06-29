<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.BeanProducto"%>
<%@page import="bean.BeanUsuario"%>
<%@page import="mysql.Sql_Producto"%>

<%
BeanUsuario usuarioSesion =
        (BeanUsuario) session.getAttribute("usuario");

boolean esSupervisor =
        (usuarioSesion.getIdRol() == 1)
        || (usuarioSesion.getIdRol() == 2);

int filasPorPagina = 15;

String paramPagina = request.getParameter("pagina");
int paginaActual = (paramPagina != null) ? Integer.parseInt(paramPagina) : 1;

Sql_Producto sql = new Sql_Producto();

int totalProductos = sql.contarProductos();
int totalPaginas = (int) Math.ceil((double) totalProductos / filasPorPagina);
int offset = (paginaActual - 1) * filasPorPagina;

ArrayList<BeanProducto> lista = sql.listarProductosPaginado(offset, filasPorPagina);

request.setAttribute("paginaActiva", "productos");
request.setAttribute("tituloPagina", "Panel Administrativo");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Productos</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link href="css/estilo.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="js/validarPinSupervisor.js"></script>
</head>

<body>

<div class="container-fluid">
    <div class="row">

        <%@ include file="header.jsp" %>

            <div class="card card-resumen">
                <div class="card-body">

                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h4 class="mb-0">Gestión de Productos</h4>
                        <a href="regProducto.jsp" class="btn btn-success">
                            <i class="bi bi-plus-circle-fill me-1"></i>
                            Nuevo Producto
                        </a>
                    </div>

                    <hr>

                    <div class="table-responsive">
                        <table class="table table-bordered table-hover align-middle">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Categoría</th>
                                    <th>Precio Venta</th>
                                    <th>Stock</th>
                                    <th>Estado</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                            <% for(BeanProducto p : lista){ %>
                                <tr>
                                    <td><%=p.getIdProducto()%></td>
                                    <td><%=p.getNombreProducto()%></td>
                                    <td><%=p.getNombreCategoria()%></td>
                                    <td>S/ <%=p.getPrecioVenta()%></td>
                                    <td><%=p.getStock()%></td>
                                    <td><%=p.getEstado()%></td>
                                    <td>
                                        <a href="actProducto.jsp?id=<%=p.getIdProducto()%>" class="btn btn-warning btn-sm">Editar</a>
	                                        <button type="button"
										        class="btn btn-danger btn-sm"
										        onclick="confirmarEliminarProducto(<%=p.getIdProducto()%>, <%=esSupervisor%>)">Eliminar</button>
	                                    </td>
                                </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>

                    <!-- Paginación -->
                    <nav>
                        <ul class="pagination justify-content-center mt-3">

                            <li class="page-item <%= (paginaActual == 1) ? "disabled" : "" %>">
                                <a class="page-link" href="bandejaProducto.jsp?pagina=<%=paginaActual - 1%>">Anterior</a>
                            </li>

                            <% for(int i = 1; i <= totalPaginas; i++){ %>
                                <li class="page-item <%= (i == paginaActual) ? "active" : "" %>">
                                    <a class="page-link" href="bandejaProducto.jsp?pagina=<%=i%>"><%=i%></a>
                                </li>
                            <% } %>

                            <li class="page-item <%= (paginaActual == totalPaginas) ? "disabled" : "" %>">
                                <a class="page-link" href="bandejaProducto.jsp?pagina=<%=paginaActual + 1%>">Siguiente</a>
                            </li>

                        </ul>
                    </nav>

                    <a href="principal.jsp" class="btn btn-secondary">Volver</a>

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
				
				<script>
				function confirmarEliminarProducto(idProducto, esSupervisor) {

				    if (esSupervisor) {

				        // El usuario logueado ya es Administrador o Jefe Inventario:
				        // no necesita autorizarse a si mismo con el PIN.
				        Swal.fire({
				            title: "¿Eliminar producto?",
				            text: "Esta acción no se puede deshacer.",
				            icon: "warning",
				            showCancelButton: true,
				            confirmButtonText: "Eliminar",
				            cancelButtonText: "Cancelar"
				        }).then(function (resultado) {

				            if (resultado.isConfirmed) {
				                window.location.href =
				                        "ServletProductoEliminar?id=" + idProducto;
				            }
				        });

				        return;
				    }

				    // El usuario logueado NO es supervisor: necesita autorizacion.
				    Swal.fire({
				        title: "¿Eliminar producto?",
				        text: "Esta acción requiere autorización de un supervisor.",
				        icon: "warning",
				        showCancelButton: true,
				        confirmButtonText: "Continuar",
				        cancelButtonText: "Cancelar"
				    }).then(function (resultado) {

				        if (resultado.isConfirmed) {

				            solicitarPinSupervisor(function (nombreSupervisor) {

				                window.location.href =
				                        "ServletProductoEliminar?id=" + idProducto;
				            });
				        }
				    });
				
}
</script>

</body>
</html>