<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.BeanAuditoria"%>
<%@page import="mysql.Sql_Auditoria"%>

<%
int filasPorPagina = 15;
String paramPagina = request.getParameter("pagina");
int paginaActual = (paramPagina != null) ? Integer.parseInt(paramPagina) : 1;

Sql_Auditoria sql = new Sql_Auditoria();
int totalRegistros = sql.contarAuditoria();
int totalPaginas = (int) Math.ceil((double) totalRegistros / filasPorPagina);
int offset = (paginaActual - 1) * filasPorPagina;

ArrayList<BeanAuditoria> lista = sql.listarAuditoriaPaginado(offset, filasPorPagina);

request.setAttribute("paginaActiva", "auditoria");
request.setAttribute("tituloPagina", "Panel Administrativo");
%>

<!DOCTYPE html>
<html><head>
<meta charset="ISO-8859-1">
<title>Auditoria</title>
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
                        <h4 class="mb-0">Registro de Auditoria</h4>
                    </div>

                    <hr>

                    <div class="row mb-3">
                        <div class="col-md-4">
                            <input type="text" id="filtroUsuario" class="form-control"
                                   placeholder="Buscar por usuario...">
                        </div>
                        <div class="col-md-4">
                            <input type="date" id="filtroFecha" class="form-control">
                        </div>
                        <div class="col-md-2">
                            <button class="btn btn-primary w-100" onclick="filtrar()">
                                <i class="bi bi-search me-1"></i> Filtrar
                            </button>
                        </div>
                        <div class="col-md-2">
                            <button class="btn btn-secondary w-100" onclick="limpiar()">
                                <i class="bi bi-x-circle me-1"></i> Limpiar
                            </button>
                        </div>
                    </div>

                    <div class="table-responsive">
                        <table class="table table-bordered table-hover align-middle" id="tablaAuditoria">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Usuario</th>
                                    <th>Accion</th>
                                    <th>Fecha</th>
                                </tr>
                            </thead>
                            <tbody>
                            <% for(BeanAuditoria a : lista){ %>
                                <tr>
                                    <td><%=a.getIdAuditoria()%></td>
                                    <td><%=a.getUsuario()%></td>
                                    <td><%=a.getAccion()%></td>
                                    <td><%=a.getFecha()%></td>
                                </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>

                    <!-- Paginacion -->
                    <nav>
                        <ul class="pagination justify-content-center mt-3">
                            <li class="page-item <%= (paginaActual == 1) ? "disabled" : "" %>">
                                <a class="page-link" href="bandejaAuditoria.jsp?pagina=<%=paginaActual - 1%>">Anterior</a>
                            </li>
                            <% for(int i = 1; i <= totalPaginas; i++){ %>
                                <li class="page-item <%= (i == paginaActual) ? "active" : "" %>">
                                    <a class="page-link" href="bandejaAuditoria.jsp?pagina=<%=i%>"><%=i%></a>
                                </li>
                            <% } %>
                            <li class="page-item <%= (paginaActual == totalPaginas) ? "disabled" : "" %>">
                                <a class="page-link" href="bandejaAuditoria.jsp?pagina=<%=paginaActual + 1%>">Siguiente</a>
                            </li>
                        </ul>
                    </nav>

                    <a href="principal.jsp" class="btn btn-secondary">Volver</a>

                </div>
            </div>

        </div>
    </div>

<script>
function filtrar() {
    const usuario = document.getElementById("filtroUsuario").value.toLowerCase();
    const fecha   = document.getElementById("filtroFecha").value;
    const filas   = document.querySelectorAll("#tablaAuditoria tbody tr");
    filas.forEach(fila => {
        const tdUsuario = fila.cells[1].textContent.toLowerCase();
        const tdFecha   = fila.cells[3].textContent;
        const coincideUsuario = tdUsuario.includes(usuario);
        const coincideFecha   = fecha === "" || tdFecha.startsWith(fecha);
        fila.style.display = (coincideUsuario && coincideFecha) ? "" : "none";
    });
}

function limpiar() {
    document.getElementById("filtroUsuario").value = "";
    document.getElementById("filtroFecha").value   = "";
    document.querySelectorAll("#tablaAuditoria tbody tr")
            .forEach(f => f.style.display = "");
}
</script>

</body>
</html>