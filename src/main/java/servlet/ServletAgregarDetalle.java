package servlet;

import java.io.IOException;
import java.util.ArrayList;

import bean.BeanCarrito;
import bean.BeanProducto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysql.Sql_Producto;

@WebServlet("/ServletAgregarDetalle")
public class ServletAgregarDetalle extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int idProducto =
                Integer.parseInt(
                request.getParameter("idProducto"));

        int cantidad =
                Integer.parseInt(
                request.getParameter("cantidad"));

        Sql_Producto sqlProducto =
                new Sql_Producto();

        BeanProducto producto =
                sqlProducto.buscarProducto(idProducto);

        HttpSession sesion =
                request.getSession();

        ArrayList<BeanCarrito> carrito =
                (ArrayList<BeanCarrito>)
                sesion.getAttribute("carrito");

        if (carrito == null) {
            carrito = new ArrayList<BeanCarrito>();
        }

        // SUMAR LO QUE YA ESTÁ EN EL CARRITO PARA ESTE PRODUCTO
        int cantidadEnCarrito = 0;
        for (BeanCarrito item : carrito) {
            if (item.getIdProducto() == idProducto) {
                cantidadEnCarrito += item.getCantidad();
            }
        }

        int cantidadTotal = cantidadEnCarrito + cantidad;

        // VALIDAR STOCK DISPONIBLE
        if (cantidadTotal > producto.getStock()) {
            sesion.setAttribute("tipo", "error");
            sesion.setAttribute("mensaje",
                    "Stock insuficiente. Solo hay "
                    + producto.getStock()
                    + " unidades disponibles del producto \""
                    + producto.getNombreProducto()
                    + "\""
                    + (cantidadEnCarrito > 0
                        ? " (ya tienes " + cantidadEnCarrito + " en el carrito)."
                        : "."));
            response.sendRedirect("ventaDetalle.jsp");
            return;
        }

        // CREAR ITEM DEL CARRITO
        BeanCarrito item = new BeanCarrito();
        item.setIdProducto(producto.getIdProducto());
        item.setNombreProducto(producto.getNombreProducto());
        item.setCantidad(cantidad);
        item.setPrecio(producto.getPrecioVenta());
        item.setSubtotal(cantidad * producto.getPrecioVenta());
        item.setTipoIgv(producto.getTipoIgv()); // ← nuevo

        carrito.add(item);

        sesion.setAttribute("carrito", carrito);

        response.sendRedirect("ventaDetalle.jsp");
    }
}