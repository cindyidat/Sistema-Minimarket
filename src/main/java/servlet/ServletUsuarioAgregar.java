package servlet;

import java.io.IOException;
import util.Auditoria;

import bean.BeanUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysql.Sql_Usuario;

@WebServlet("/ServletUsuarioAgregar")
public class ServletUsuarioAgregar extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        BeanUsuario u = new BeanUsuario();

        u.setNombre(
                request.getParameter("txtNombre"));

        u.setDni(
                request.getParameter("txtDni"));

        u.setUsuario(
                request.getParameter("txtUsuario"));

        u.setClave(
                request.getParameter("txtClave"));

        u.setIdRol(
                Integer.parseInt(
                request.getParameter("cmbRol")));

        u.setTelefono(
                request.getParameter("txtTelefono"));

        u.setCorreo(
                request.getParameter("txtCorreo"));

        u.setEstado(
                request.getParameter("cmbEstado"));

        Sql_Usuario sql =
                new Sql_Usuario();

        sql.registrarUsuario(u);
        HttpSession sesion = request.getSession();
        Auditoria.registrar(sesion, "Creó usuario: " + u.getUsuario());
        sesion.setAttribute("tipo", "success");
        sesion.setAttribute("mensaje", "Usuario registrado correctamente.");
        response.sendRedirect(
                "bandejaUsuario.jsp");
    }
}