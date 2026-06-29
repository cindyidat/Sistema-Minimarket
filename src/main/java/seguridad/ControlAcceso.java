package seguridad;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Configuración central de permisos por rol.
 *
 * idRol (tabla rol):
 *   1 = Administrador
 *   2 = Jefe Inventario
 *   3 = Cajero
 *   4 = Almacenero
 *
 * Cada recurso (servlet o jsp) tiene la lista de roles que pueden accederlo.
 * Si una ruta no está registrada aquí, se asume que requiere sólo sesión
 * activa (cualquier rol autenticado puede entrar) — ver isRutaPublica()
 * y isRutaSoloSesion() en FiltroControlAcceso.
 */
public class ControlAcceso {

    public static final int ADMINISTRADOR    = 1;
    public static final int JEFE_INVENTARIO  = 2;
    public static final int CAJERO           = 3;
    public static final int ALMACENERO       = 4;

    private static final Map<String, Set<Integer>> PERMISOS = new HashMap<>();

    static {

        // ── VENTAS ───────────────────────────────────────────
        permitir("/ventaDetalle.jsp",          ADMINISTRADOR, JEFE_INVENTARIO, CAJERO);
        permitir("/ServletAgregarDetalle",     ADMINISTRADOR, JEFE_INVENTARIO, CAJERO);
        permitir("/ServletBuscarProducto",     ADMINISTRADOR, JEFE_INVENTARIO, CAJERO, ALMACENERO);
        permitir("/ServletConfirmarVenta",     ADMINISTRADOR, JEFE_INVENTARIO, CAJERO);
        permitir("/ServletVentaRegistrar",     ADMINISTRADOR, JEFE_INVENTARIO, CAJERO);

        // ── REPORTES DE VENTAS ───────────────────────────────
        permitir("/reporteVentas.jsp",         ADMINISTRADOR, JEFE_INVENTARIO);
        permitir("/ServletValidarPin", ADMINISTRADOR, JEFE_INVENTARIO, CAJERO, ALMACENERO);

        // ── PRODUCTOS ────────────────────────────────────────
        permitir("/bandejaProducto.jsp",       ADMINISTRADOR, JEFE_INVENTARIO, ALMACENERO);
        permitir("/regProducto.jsp",           ADMINISTRADOR, JEFE_INVENTARIO);
        permitir("/actProducto.jsp",           ADMINISTRADOR, JEFE_INVENTARIO);
        permitir("/ServletProductoAgregar",    ADMINISTRADOR, JEFE_INVENTARIO);
        permitir("/ServletProductoActualizar", ADMINISTRADOR, JEFE_INVENTARIO);
        permitir("/ServletProductoEliminar",   ADMINISTRADOR, JEFE_INVENTARIO, CAJERO, ALMACENERO);

        // ── PROVEEDORES ──────────────────────────────────────
        permitir("/bandejaProveedor.jsp",      ADMINISTRADOR, JEFE_INVENTARIO);
        permitir("/regProveedor.jsp",          ADMINISTRADOR, JEFE_INVENTARIO);
        permitir("/actProveedor.jsp",          ADMINISTRADOR, JEFE_INVENTARIO);
        permitir("/ServletProveedorAgregar",   ADMINISTRADOR, JEFE_INVENTARIO);
        permitir("/ServletProveedorActualizar",ADMINISTRADOR, JEFE_INVENTARIO);
        permitir("/ServletProveedorEliminar",  ADMINISTRADOR, JEFE_INVENTARIO);

        // ── INVENTARIO / LOTES ───────────────────────────────
        permitir("/reporteInventario.jsp",     ADMINISTRADOR, JEFE_INVENTARIO);
        permitir("/productosStockBajo.jsp",    ADMINISTRADOR, JEFE_INVENTARIO, ALMACENERO, CAJERO);
        permitir("/bandejaLote.jsp",           ADMINISTRADOR, JEFE_INVENTARIO, ALMACENERO);
        permitir("/registrarLote.jsp",         ADMINISTRADOR, JEFE_INVENTARIO, ALMACENERO);
        permitir("/ServletLote",               ADMINISTRADOR, JEFE_INVENTARIO, ALMACENERO);
        permitir("/bandejaVencimientos.jsp",   ADMINISTRADOR, JEFE_INVENTARIO, ALMACENERO, CAJERO);

        // ── MERMAS ───────────────────────────────────────────
        permitir("/bandejaMerma.jsp",          ADMINISTRADOR, JEFE_INVENTARIO, ALMACENERO, CAJERO);
        permitir("/registrarMerma.jsp",        ADMINISTRADOR, JEFE_INVENTARIO, ALMACENERO, CAJERO);
        permitir("/ServletMerma",              ADMINISTRADOR, JEFE_INVENTARIO, ALMACENERO, CAJERO);

        // ── USUARIOS / TRABAJADORES (solo Administrador) ────
        permitir("/bandejaUsuario.jsp",        ADMINISTRADOR);
        permitir("/regUsuario.jsp",            ADMINISTRADOR);
        permitir("/actUsuario.jsp",            ADMINISTRADOR);
        permitir("/contratoUsuario.jsp",       ADMINISTRADOR);
        permitir("/ServletUsuarioAgregar",     ADMINISTRADOR);
        permitir("/ServletUsuarioActualizar",  ADMINISTRADOR);
        permitir("/ServletUsuarioEliminar",    ADMINISTRADOR);
        permitir("/ServletContratoRegistrar",  ADMINISTRADOR);
        //audfitoria
        permitir("/bandejaAuditoria.jsp", ADMINISTRADOR);
    }

    private static void permitir(String ruta, Integer... roles) {
        PERMISOS.put(ruta, new HashSet<>(java.util.Arrays.asList(roles)));
    }

    /**
     * Devuelve true si el rol indicado puede acceder a la ruta dada.
     * Si la ruta no está registrada, se permite a cualquier usuario
     * autenticado (comportamiento por defecto = solo exige sesión).
     */
    public static boolean tieneAcceso(String ruta, int idRol) {

        Set<Integer> rolesPermitidos = PERMISOS.get(ruta);

        if (rolesPermitidos == null) {
            // Ruta no controlada explícitamente: solo exige sesión activa.
            return true;
        }

        return rolesPermitidos.contains(idRol);
    }

    /**
     * True si la ruta tiene una regla de permisos definida explícitamente.
     * Útil para distinguir "no encontrado en el mapa" de "rol sin permiso".
     */
    public static boolean rutaControlada(String ruta) {
        return PERMISOS.containsKey(ruta);
    }
}