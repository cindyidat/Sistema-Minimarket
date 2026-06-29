package servlet;

import java.io.IOException;
import util.Auditoria;

import bean.BeanProducto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysql.Sql_Producto;

@WebServlet("/ServletProductoAgregar")
public class ServletProductoAgregar extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        BeanProducto p =
                new BeanProducto();

        p.setNombreProducto(
                request.getParameter("txtNombre"));

        p.setDescripcion(
                request.getParameter("txtDescripcion"));

        p.setPrecioCompra(
                Double.parseDouble(
                request.getParameter("txtPrecioCompra")));

        p.setPrecioVenta(
                Double.parseDouble(
                request.getParameter("txtPrecioVenta")));

        p.setStock(
                Integer.parseInt(
                request.getParameter("txtStock")));

        p.setStockMinimo(
                Integer.parseInt(
                request.getParameter("txtStockMinimo")));

        p.setIdCategoria(
                Integer.parseInt(
                request.getParameter("cmbCategoria")));

        p.setIdProveedor(
                Integer.parseInt(
                request.getParameter("cmbProveedor")));

        p.setEstado("ACTIVO");

        Sql_Producto sql =
                new Sql_Producto();

        sql.registrarProducto(p);
        HttpSession sesion = request.getSession();
        Auditoria.registrar(sesion, "Agregó producto: " + p.getNombreProducto());
        sesion.setAttribute("tipo", "success");
        sesion.setAttribute("mensaje", "Producto registrado correctamente.");
        response.sendRedirect(
                "bandejaProducto.jsp");
      
    }
}