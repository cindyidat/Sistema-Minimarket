package servlet;

import java.io.IOException;
import util.Auditoria;

import bean.BeanLote;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysql.Sql_Lote;

@WebServlet("/ServletLote")
public class ServletLote extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        BeanLote l = new BeanLote();

        l.setIdProducto(
                Integer.parseInt(
                request.getParameter("cmbProducto")));

        l.setCantidad(
                Integer.parseInt(
                request.getParameter("txtCantidad")));

        l.setFechaIngreso(
                request.getParameter("txtFechaIngreso"));

        l.setFechaVencimiento(
                request.getParameter("txtFechaVencimiento"));

        Sql_Lote sql = new Sql_Lote();

        sql.registrarLote(l);

        HttpSession sesion = request.getSession();
        Auditoria.registrar(sesion, "Registró lote de producto ID: " + l.getIdProducto());
        sesion.setAttribute("tipo", "success");
        sesion.setAttribute("mensaje", "Lote registrado correctamente.");
        response.sendRedirect("bandejaLote.jsp");
    }
}