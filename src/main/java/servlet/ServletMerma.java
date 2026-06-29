package servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import util.Auditoria;

import bean.BeanMerma;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysql.Sql_Merma;

@WebServlet("/ServletMerma")
public class ServletMerma extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Motivos que no dejan evidencia fisica verificable. A diferencia
    // de un producto vencido o danado (que cualquiera puede confirmar
    // con solo verlo), estos requieren que un supervisor (Administrador
    // o Jefe Inventario) autorice la merma con su PIN antes de
    // registrarla, para evitar que se use como excusa para encubrir
    // un faltante real.
    private static final List<String> MOTIVOS_SENSIBLES =
            Arrays.asList("Robo", "Faltante sin explicacion");

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String motivo =
                request.getParameter("txtMotivo");

        HttpSession sesion =
                request.getSession();

        if (MOTIVOS_SENSIBLES.contains(motivo)) {

            Object autorizadoPor =
                    sesion.getAttribute("supervisorAutorizoAccion");

            if (autorizadoPor == null) {

                sesion.setAttribute("tipo", "error");
                sesion.setAttribute("mensaje",
                        "Este motivo de merma requiere autorizacion "
                        + "del Jefe de Inventario o Administrador "
                        + "mediante su PIN.");

                response.sendRedirect("bandejaMerma.jsp");
                return;
            }

            // Se consume de inmediato: una autorizacion de PIN solo
            // sirve para una merma. La siguiente vez se debe volver
            // a pedir el PIN.
            sesion.removeAttribute("supervisorAutorizoAccion");
        }

        BeanMerma m =
                new BeanMerma();

        m.setIdProducto(
                Integer.parseInt(
                request.getParameter("cmbProducto")));

        m.setCantidad(
                Integer.parseInt(
                request.getParameter("txtCantidad")));

        m.setMotivo(motivo);

        m.setFecha(
                request.getParameter("txtFecha"));

        Sql_Merma sql =
                new Sql_Merma();

        sql.registrarMerma(m);
        Auditoria.registrar(sesion, "Registró merma: " + m.getCantidad() 
        + " unidades — motivo: " + m.getMotivo());
        sesion.setAttribute("tipo", "success");
        sesion.setAttribute("mensaje", "Merma registrada correctamente.");
        response.sendRedirect(
                "bandejaMerma.jsp");
    }
}