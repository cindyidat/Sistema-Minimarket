package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import bean.BeanProducto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysql.Sql_Producto;

@WebServlet("/ServletBuscarProducto")
public class ServletBuscarProducto extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String texto = request.getParameter("texto");

        if (texto == null) {
            texto = "";
        }

        Sql_Producto sqlProducto = new Sql_Producto();

        ArrayList<BeanProducto> lista =
                sqlProducto.buscarProductosPorNombre(texto);

        StringBuilder json = new StringBuilder();

        json.append("[");

        for (int i = 0; i < lista.size(); i++) {

            BeanProducto p = lista.get(i);

            if (i > 0) {
                json.append(",");
            }

            json.append("{");
            json.append("\"id\":").append(p.getIdProducto()).append(",");
            json.append("\"nombre\":\"").append(escaparJson(p.getNombreProducto())).append("\",");
            json.append("\"stock\":").append(p.getStock()).append(",");
            json.append("\"precioVenta\":").append(p.getPrecioVenta());
            json.append("}");
        }

        json.append("]");

        PrintWriter out = response.getWriter();
        out.print(json.toString());
        out.flush();
    }

    // Escapa comillas y backslashes para que el JSON no se rompa
    // si el nombre del producto contiene caracteres especiales
    private String escaparJson(String texto) {

        if (texto == null) {
            return "";
        }

        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}