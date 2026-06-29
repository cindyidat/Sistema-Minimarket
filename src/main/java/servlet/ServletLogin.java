package servlet;

import java.io.IOException;

import bean.BeanUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysql.Sql_Auditoria;
import mysql.Sql_Usuario;

@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String usuario =
                request.getParameter("txtUsuario");

        String clave =
                request.getParameter("txtClave");

        Sql_Usuario sql =
                new Sql_Usuario();

        BeanUsuario user =
                sql.validarUsuario(usuario, clave);

        if (user != null) {

            HttpSession sesion =
                    request.getSession();

            sesion.setAttribute("usuario", user);

            new Sql_Auditoria().registrar(
                    user.getUsuario(),
                    "Inició sesión");

            response.sendRedirect("principal.jsp");

        } else {

            new Sql_Auditoria().registrar(
                    usuario,
                    "Intento de login fallido");

            response.sendRedirect("login.jsp?error=1");
        }
    }
}