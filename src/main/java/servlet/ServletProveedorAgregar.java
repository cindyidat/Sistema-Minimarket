package servlet;

import java.io.IOException;

import bean.BeanProveedor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysql.Sql_Proveedor;

@WebServlet("/ServletProveedorAgregar")
public class ServletProveedorAgregar extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        BeanProveedor p = new BeanProveedor();

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

        sql.registrarProveedor(p);
        HttpSession sesion = request.getSession();
        sesion.setAttribute("tipo", "success");
        sesion.setAttribute("mensaje", "Proveedor registrado correctamente.");
        response.sendRedirect(
                "bandejaProveedor.jsp");
    }
}