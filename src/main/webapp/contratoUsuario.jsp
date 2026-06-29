<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.BeanUsuario"%>
<%@page import="bean.BeanRol"%>
<%@page import="bean.BeanContrato"%>
<%@page import="mysql.Sql_Usuario"%>
<%@page import="mysql.Sql_Rol"%>
<%@page import="mysql.Sql_Contrato"%>

<%
int id =
Integer.parseInt(
request.getParameter("id"));

Sql_Usuario sqlUsuario =
new Sql_Usuario();

BeanUsuario u =
sqlUsuario.buscarUsuario(id);

Sql_Rol sqlRol =
new Sql_Rol();

ArrayList<BeanRol> listaRoles =
sqlRol.listarRoles();

String nombreRol = "";

for(BeanRol r : listaRoles){
    if(r.getIdRol() == u.getIdRol()){
        nombreRol = r.getNombreRol();
    }
}

Sql_Contrato sqlContrato =
new Sql_Contrato();

BeanContrato vigente =
sqlContrato.buscarContratoVigente(id);

ArrayList<BeanContrato> historial =
sqlContrato.listarContratosPorUsuario(id);

request.setAttribute("paginaActiva", "usuarios");
request.setAttribute("tituloPagina", "Contrato del Trabajador");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Contrato del Trabajador</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link href="css/estilo.css" rel="stylesheet">
</head>

<body>

<div class="container-fluid">
    <div class="row">

        <%@ include file="header.jsp" %>

            <div class="card card-resumen mb-4">
                <div class="card-body">

                    <h4 class="mb-0">
                        Contrato de <%=u.getNombre()%>
                    </h4>
                    <div class="text-muted mb-3">
                        Usuario: <%=u.getUsuario()%> | Rol: <%=nombreRol%>
                    </div>

                    <hr>

                    <% if(vigente != null){ %>

                        <div class="alert alert-success">
                            <strong>Contrato vigente:</strong>
                            <%=vigente.getTipoContrato()%>
                            - S/ <%=vigente.getSueldo()%>
                            - Desde <%=vigente.getFechaInicio()%>
                        </div>

                    <% } else { %>

                        <div class="alert alert-warning">
                            Este trabajador no tiene un contrato vigente registrado.
                        </div>

                    <% } %>

                    <h5 class="mt-4">
                        <%= (vigente != null) ? "Registrar Nuevo Contrato (renovacion / cambio)" : "Registrar Primer Contrato" %>
                    </h5>

                    <form action="ServletContratoRegistrar" method="post">

                        <input type="hidden" name="txtIdUsuario" value="<%=u.getIdUsuario()%>">

                        <div class="row">

                            <div class="col-md-4 mb-3">
                                <label class="form-label">Tipo de Contrato</label>
                                <select name="cmbTipoContrato" class="form-select" required>
                                    <option value="Indefinido">Indefinido</option>
                                    <option value="Plazo Fijo">Plazo Fijo</option>
                                    <option value="Part Time">Part Time</option>
                                </select>
                            </div>

                            <div class="col-md-4 mb-3">
                                <label class="form-label">Sueldo (S/)</label>
                                <input type="number" step="0.01" name="txtSueldo"
                                       class="form-control" required>
                            </div>

                            <div class="col-md-4 mb-3">
                                <label class="form-label">Fecha de Inicio</label>
                                <input type="date" name="txtFechaInicio"
                                       class="form-control" required>
                            </div>

                        </div>

                        <button type="submit" class="btn btn-success">
                            Guardar Contrato
                        </button>

                        <a href="bandejaUsuario.jsp" class="btn btn-secondary">
                            Volver
                        </a>

                    </form>

                </div>
            </div>

            <div class="card card-resumen">
                <div class="card-body">

                    <h5 class="mb-3">Historial de Contratos</h5>

                    <div class="table-responsive">
                        <table class="table table-bordered table-hover align-middle">
                            <thead>
                                <tr>
                                    <th>Tipo</th>
                                    <th>Sueldo</th>
                                    <th>Fecha Inicio</th>
                                    <th>Fecha Fin</th>
                                    <th>Estado</th>
                                </tr>
                            </thead>
                            <tbody>
                            <% for(BeanContrato c : historial){ %>
                                <tr>
                                    <td><%=c.getTipoContrato()%></td>
                                    <td>S/ <%=c.getSueldo()%></td>
                                    <td><%=c.getFechaInicio()%></td>
                                    <td><%=(c.getFechaFin() != null) ? c.getFechaFin() : "-"%></td>
                                    <td>
                                        <% if(c.getEstado().equals("VIGENTE")){ %>
                                            <span class="badge bg-success">VIGENTE</span>
                                        <% } else { %>
                                            <span class="badge bg-secondary">FINALIZADO</span>
                                        <% } %>
                                    </td>
                                </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>

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