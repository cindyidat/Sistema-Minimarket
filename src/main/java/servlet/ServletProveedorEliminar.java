package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysql.Sql_Proveedor;

@WebServlet("/ServletProveedorEliminar")
public class ServletProveedorEliminar extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int id =
                Integer.parseInt(
                request.getParameter("id"));

        Sql_Proveedor sql =
                new Sql_Proveedor();

        boolean eliminado = sql.eliminarProveedor(id);

        HttpSession sesion = request.getSession();

        if (eliminado) {

            sesion.setAttribute("tipo", "success");
            sesion.setAttribute("mensaje", "Proveedor eliminado correctamente.");

        } else {

            sesion.setAttribute("tipo", "error");
            sesion.setAttribute("mensaje",
                    "No se puede eliminar este proveedor porque tiene "
                    + "productos asociados. Primero reasigna o elimina "
                    + "esos productos.");
        }

        response.sendRedirect(
                "bandejaProveedor.jsp");
    }
}