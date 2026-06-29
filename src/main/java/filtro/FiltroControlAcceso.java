package filtro;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import bean.BeanUsuario;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import seguridad.ControlAcceso;

@WebFilter("/*")
public class FiltroControlAcceso implements Filter {

    private static final List<String> RUTAS_PUBLICAS = Arrays.asList(
            "/login.jsp",
            "/ServletLogin",
            "/ServletCerrarSesion"
    );

    private static final List<String> PREFIJOS_PUBLICOS = Arrays.asList(
            "/css/",
            "/js/",
            "/icons/"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request   = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String contextPath = request.getContextPath();
        String uri          = request.getRequestURI();
        String ruta         = uri.substring(contextPath.length());

        if (esRutaPublica(ruta)) {
            chain.doFilter(req, res);
            return;
        }

        HttpSession sesion = request.getSession(false);

        BeanUsuario usuario =
                (sesion != null)
                ? (BeanUsuario) sesion.getAttribute("usuario")
                : null;

        if (usuario == null) {
            response.sendRedirect(contextPath + "/login.jsp");
            return;
        }

        boolean tieneAcceso =
                ControlAcceso.tieneAcceso(ruta, usuario.getIdRol());

        if (!tieneAcceso) {

            sesion.setAttribute("tipoAcceso", "error");
            sesion.setAttribute("mensajeAcceso",
                    "No tienes permisos para acceder a ese módulo. "
                    + "Si crees que es un error, contacta al Administrador.");

            response.sendRedirect(contextPath + "/principal.jsp");
            return;
        }

        chain.doFilter(req, res);
    }

    private boolean esRutaPublica(String ruta) {

        if (RUTAS_PUBLICAS.contains(ruta)) {
            return true;
        }

        for (String prefijo : PREFIJOS_PUBLICOS) {
            if (ruta.startsWith(prefijo)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void destroy() {
    }
}