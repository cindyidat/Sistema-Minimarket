package servlet;

import java.io.IOException;
import java.time.LocalDate;
import util.Auditoria;

import bean.BeanContrato;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysql.Sql_Contrato;

@WebServlet("/ServletContratoRegistrar")
public class ServletContratoRegistrar extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        BeanContrato c =
                new BeanContrato();

        int idUsuario =
                Integer.parseInt(
                request.getParameter("txtIdUsuario"));

        c.setIdUsuario(idUsuario);

        c.setTipoContrato(
                request.getParameter("cmbTipoContrato"));

        c.setSueldo(
                Double.parseDouble(
                request.getParameter("txtSueldo")));

        String fechaInicio =
                request.getParameter("txtFechaInicio");

        if(fechaInicio == null || fechaInicio.isEmpty()){
            fechaInicio = LocalDate.now().toString();
        }

        c.setFechaInicio(fechaInicio);

        Sql_Contrato sql =
                new Sql_Contrato();

        sql.registrarContrato(c);
        HttpSession sesion = request.getSession();
        Auditoria.registrar(sesion, "Registró contrato para usuario ID: " + idUsuario);
        sesion.setAttribute("tipo", "success");
        sesion.setAttribute("mensaje", "Contrato registrado correctamente.");
        response.sendRedirect(
                "contratoUsuario.jsp?id=" + idUsuario);
    }
}