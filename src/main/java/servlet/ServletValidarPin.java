package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import bean.BeanUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysql.Sql_Usuario;
import util.Auditoria;

@WebServlet("/ServletValidarPin")
public class ServletValidarPin extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String usuarioSupervisor =
                request.getParameter("usuarioSupervisor");

        String pin =
                request.getParameter("pin");

        Sql_Usuario sql =
                new Sql_Usuario();

        BeanUsuario supervisor =
                sql.validarPinSupervisor(usuarioSupervisor, pin);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        if (supervisor != null) {

            HttpSession sesion = request.getSession();

            sesion.setAttribute(
                    "supervisorAutorizoAccion",
                    supervisor.getNombre());

            Auditoria.registrar(sesion,
                    "PIN validado por supervisor: " + supervisor.getNombre());

            out.print(
                    "{\"autorizado\":true,"
                    + "\"nombreSupervisor\":\""
                    + supervisor.getNombre()
                    + "\"}");

        } else {

            out.print("{\"autorizado\":false}");
        }

        out.flush();
    }
}