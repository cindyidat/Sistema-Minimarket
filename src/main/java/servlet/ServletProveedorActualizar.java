package servlet;

import java.io.IOException;
import util.Auditoria;

import bean.BeanProveedor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysql.Sql_Proveedor;

@WebServlet("/ServletProveedorActualizar")
public class ServletProveedorActualizar extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        BeanProveedor p =
                new BeanProveedor();

        p.setIdProveedor(
                Integer.parseInt(
                request.getParameter("txtId")));

        p.setRazonSocial(
                request.getParameter("txtRazonSocial"));

        p.setRuc(
                request.getParameter("txtRuc"));

        p.setDireccion(
                request.getParameter("txtDireccion"));

        p.setTelefono(
                request.getParameter("txtTelefono"));

        Sql_Proveedor sql =
                new Sql_Proveedor();

        sql.actualizarProveedor(p);
        HttpSession sesion = request.getSession();
        Auditoria.registrar(sesion, "Actualizó proveedor ID: " + p.getIdProveedor());
        sesion.setAttribute("tipo", "success");
        sesion.setAttribute("mensaje", "Proveedor actualizado correctamente.");
        response.sendRedirect(
                "bandejaProveedor.jsp");
    }
}