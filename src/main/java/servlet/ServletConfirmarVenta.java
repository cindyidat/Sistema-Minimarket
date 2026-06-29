package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import util.Auditoria;

import bean.BeanCarrito;
import bean.BeanDetalleVenta;
import bean.BeanProducto;
import bean.BeanUsuario;
import bean.BeanVenta;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysql.Sql_DetalleVenta;
import mysql.Sql_Lote;
import mysql.Sql_Producto;
import mysql.Sql_Venta;

@WebServlet("/ServletConfirmarVenta")
public class ServletConfirmarVenta extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);

        if (sesion == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        BeanUsuario usuario =
                (BeanUsuario) sesion.getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        ArrayList<BeanCarrito> carrito =
                (ArrayList<BeanCarrito>) sesion.getAttribute("carrito");

        if (carrito == null || carrito.isEmpty()) {
            response.sendRedirect("ventaDetalle.jsp");
            return;
        }

        // ── MÉTODO DE PAGO ──────────────────────────────
        String metodoPago = request.getParameter("metodoPago");
        if (metodoPago == null || metodoPago.isEmpty()) {
            metodoPago = "EFECTIVO";
        }

        // ── CALCULAR IGV POR TIPO DE PRODUCTO ───────────
        double opGravada   = 0;
        double opExonerada = 0;

        for (BeanCarrito item : carrito) {
            if ("GRAVADO".equals(item.getTipoIgv())) {
                opGravada   += item.getSubtotal();
            } else {
                opExonerada += item.getSubtotal();
            }
        }

        double igv   = Math.round(opGravada * 0.18 * 100.0) / 100.0;
        double total = Math.round((opGravada + opExonerada + igv) * 100.0) / 100.0;

        // ── CALCULAR VUELTO (solo si es efectivo) ───────
        double vuelto = 0;
        if ("EFECTIVO".equals(metodoPago)) {
            String montoStr = request.getParameter("montoEntrega");
            if (montoStr != null && !montoStr.isEmpty()) {
                double montoEntrega = Double.parseDouble(montoStr);
                vuelto = Math.round((montoEntrega - total) * 100.0) / 100.0;
                if (vuelto < 0) vuelto = 0;
            }
        }

        // ── REGISTRAR VENTA ─────────────────────────────
        BeanVenta venta = new BeanVenta();
        venta.setFechaVenta(LocalDate.now().toString());
        venta.setTotal(total);
        venta.setIgv(igv);
        venta.setMetodoPago(metodoPago);
        venta.setVuelto(vuelto);
        venta.setIdUsuario(usuario.getIdUsuario());

        Sql_Venta sqlVenta = new Sql_Venta();
        int idVenta = sqlVenta.registrarVentaRetornandoId(venta);

        // ── REGISTRAR DETALLE + ACTUALIZAR STOCK ────────
        Sql_DetalleVenta sqlDetalle = new Sql_DetalleVenta();
        Sql_Producto     sqlProducto = new Sql_Producto();
        Sql_Lote         sqlLote = new Sql_Lote();

        boolean stockBajo      = false;
        String  nombreProducto = "";
        int     stockActual    = 0;
        int     stockMinimo    = 0;

        for (BeanCarrito item : carrito) {

            BeanDetalleVenta detalle = new BeanDetalleVenta();
            detalle.setIdVenta(idVenta);
            detalle.setIdProducto(item.getIdProducto());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecio(item.getPrecio());
            detalle.setSubtotal(item.getSubtotal());

            sqlDetalle.registrarDetalle(detalle);

            sqlProducto.actualizarStock(
                    item.getIdProducto(),
                    item.getCantidad());

            sqlLote.descontarStockFEFO(
                    item.getIdProducto(),
                    item.getCantidad());

            BeanProducto producto =
                    sqlProducto.buscarProducto(item.getIdProducto());

            if (producto.getStock() <= producto.getStockMinimo()) {
                stockBajo      = true;
                nombreProducto = producto.getNombreProducto();
                stockActual    = producto.getStock();
                stockMinimo    = producto.getStockMinimo();
            }
        }

        // ── LIMPIAR CARRITO ──────────────────────────────
        sesion.removeAttribute("carrito");

        // ── GUARDAR DATOS DEL COMPROBANTE EN SESIÓN ─────
        sesion.setAttribute("idVenta",      idVenta);
        sesion.setAttribute("opGravada",    opGravada);
        sesion.setAttribute("opExonerada",  opExonerada);
        sesion.setAttribute("igv",          igv);
        sesion.setAttribute("total",        total);
        sesion.setAttribute("metodoPago",   metodoPago);
        sesion.setAttribute("vuelto",       vuelto);

        // ── MENSAJE DE RESULTADO ─────────────────────────
        if (stockBajo) {
            sesion.setAttribute("tipo", "warning");
            sesion.setAttribute("mensaje",
                    "Venta registrada correctamente. El producto \""
                    + nombreProducto
                    + "\" quedó con "
                    + stockActual
                    + " unidades (Stock mínimo: "
                    + stockMinimo
                    + "). Se recomienda realizar un nuevo pedido al proveedor.");
        } else {
            sesion.setAttribute("tipo", "success");
            sesion.setAttribute("mensaje", "Venta registrada correctamente.");
        }
        Auditoria.registrar(sesion, "Confirmó venta por S/. " + total);
        response.sendRedirect("ventaDetalle.jsp");
    }
}