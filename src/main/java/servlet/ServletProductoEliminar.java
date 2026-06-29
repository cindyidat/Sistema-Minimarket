package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysql.Sql_Producto;
import util.Auditoria;

@WebServlet("/ServletProductoEliminar")
public class ServletProductoEliminar extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int id =
                Integer.parseInt(
                request.getParameter("id"));

        Sql_Producto sql =
                new Sql_Producto();

        boolean eliminado = sql.eliminarProducto(id);
        HttpSession sesion = request.getSession();

        if (eliminado) {
        	Auditoria.registrar(sesion, "Eliminó producto ID: " + id);
            sesion.setAttribute("tipo", "success");
            sesion.setAttribute("mensaje", "Producto eliminado correctamente.");
        } else {
            sesion.setAttribute("tipo", "error");
            sesion.setAttribute("mensaje",
                    "No se puede eliminar este producto porque tiene "
                    + "ventas, lotes o mermas asociadas.");
        }

        response.sendRedirect(
                "bandejaProducto.jsp");
    }
}