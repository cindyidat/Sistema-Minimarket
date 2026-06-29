package servlet;

import java.io.IOException;
import util.Auditoria;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysql.Sql_Usuario;

@WebServlet("/ServletUsuarioEliminar")
public class ServletUsuarioEliminar extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int id =
                Integer.parseInt(
                request.getParameter("id"));

        Sql_Usuario sql =
                new Sql_Usuario();

        sql.eliminarUsuario(id);
        HttpSession sesion = request.getSession();
        Auditoria.registrar(sesion, "Eliminó usuario ID: " + id);
        sesion.setAttribute("tipo", "success");
        sesion.setAttribute("mensaje", "Usuario eliminado correctamente.");
        response.sendRedirect(
                "bandejaUsuario.jsp");
    }
}