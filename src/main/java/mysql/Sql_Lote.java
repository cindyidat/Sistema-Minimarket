package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bean.BeanLote;

public class Sql_Lote {

    Conexion con = new Conexion();

    // LISTAR

    public ArrayList<BeanLote> listarLotes() {

        ArrayList<BeanLote> lista =
                new ArrayList<BeanLote>();

        try {

            Connection cn =
                    con.conectar();

            String sql =
            "SELECT l.*, p.nombreProducto "
            + "FROM lote l "
            + "INNER JOIN producto p "
            + "ON l.idProducto = p.idProducto "
            + "ORDER BY l.fechaVencimiento ASC";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()) {

                BeanLote l =
                        new BeanLote();

                l.setIdLote(
                        rs.getInt("idLote"));

                l.setIdProducto(
                        rs.getInt("idProducto"));

                l.setNombreProducto(
                        rs.getString("nombreProducto"));

                l.setCantidad(
                        rs.getInt("cantidad"));

                l.setFechaIngreso(
                        rs.getString("fechaIngreso"));

                l.setFechaVencimiento(
                        rs.getString("fechaVencimiento"));

                lista.add(l);
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return lista;
    }

    // REGISTRAR

    public void registrarLote(BeanLote l) {
        try {
            Connection cn = con.conectar();

            // 1. Insertar el lote
            String sqlLote =
            "INSERT INTO lote("
            + "idProducto,"
            + "cantidad,"
            + "fechaIngreso,"
            + "fechaVencimiento)"
            + " VALUES(?,?,?,?)";

            PreparedStatement ps = cn.prepareStatement(sqlLote);
            ps.setInt(1, l.getIdProducto());
            ps.setInt(2, l.getCantidad());
            ps.setString(3, l.getFechaIngreso());
            ps.setString(4, l.getFechaVencimiento());
            ps.executeUpdate();
            ps.close();

            // 2. Actualizar stock del producto ← ESTO FALTABA
            String sqlStock =
            "UPDATE producto SET stock = stock + ? "
            + "WHERE idProducto = ?";

            PreparedStatement psStock = cn.prepareStatement(sqlStock);
            psStock.setInt(1, l.getCantidad());
            psStock.setInt(2, l.getIdProducto());
            psStock.executeUpdate();
            psStock.close();

            cn.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // PRODUCTOS PROXIMOS A VENCER

    public ArrayList<BeanLote> productosPorVencer() {

        ArrayList<BeanLote> lista =
                new ArrayList<BeanLote>();

        try {

            Connection cn =
                    con.conectar();

            String sql =
            "SELECT l.*, p.nombreProducto "
            + "FROM lote l "
            + "INNER JOIN producto p "
            + "ON l.idProducto = p.idProducto "
            + "WHERE l.fechaVencimiento "
            + "BETWEEN CURDATE() "
            + "AND DATE_ADD(CURDATE(), INTERVAL 7 DAY) "
            + "ORDER BY l.fechaVencimiento ASC";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()) {

                BeanLote l =
                        new BeanLote();

                l.setIdLote(
                        rs.getInt("idLote"));

                l.setIdProducto(
                        rs.getInt("idProducto"));

                l.setNombreProducto(
                        rs.getString("nombreProducto"));

                l.setCantidad(
                        rs.getInt("cantidad"));

                l.setFechaIngreso(
                        rs.getString("fechaIngreso"));

                l.setFechaVencimiento(
                        rs.getString("fechaVencimiento"));

                lista.add(l);
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return lista;
    }
 // DESCONTAR STOCK POR LOTES (FEFO: primero el que vence antes)

    public void descontarStockFEFO(int idProducto, int cantidadVendida) {

        try {

            Connection cn = con.conectar();

            // 1. Traer los lotes de este producto con cantidad > 0,
            //    ordenados por fecha de vencimiento ascendente (FEFO)
            String sqlSelect =
                    "SELECT idLote, cantidad " +
                    "FROM lote " +
                    "WHERE idProducto = ? " +
                    "AND cantidad > 0 " +
                    "ORDER BY fechaVencimiento ASC";

            PreparedStatement psSelect = cn.prepareStatement(sqlSelect);
            psSelect.setInt(1, idProducto);

            ResultSet rs = psSelect.executeQuery();

            int pendiente = cantidadVendida;

            // 2. Recorrer lote por lote, descontando hasta cubrir
            //    la cantidad vendida
            while (rs.next() && pendiente > 0) {

                int idLote = rs.getInt("idLote");
                int cantidadLote = rs.getInt("cantidad");

                int aDescontar = Math.min(cantidadLote, pendiente);

                String sqlUpdate =
                        "UPDATE lote SET cantidad = cantidad - ? " +
                        "WHERE idLote = ?";

                PreparedStatement psUpdate = cn.prepareStatement(sqlUpdate);
                psUpdate.setInt(1, aDescontar);
                psUpdate.setInt(2, idLote);
                psUpdate.executeUpdate();
                psUpdate.close();

                pendiente -= aDescontar;
            }

            rs.close();
            psSelect.close();
            cn.close();

            // Si "pendiente" queda mayor a 0 significa que se vendio
            // mas de lo que hay registrado en lotes (inconsistencia
            // entre stock total y suma de lotes). No se lanza error
            // para no bloquear la venta, pero queda como aviso en consola.
            if (pendiente > 0) {
                System.out.println(
                    "ADVERTENCIA: No hay suficientes lotes registrados para "
                    + "el producto " + idProducto + ". Faltaron "
                    + pendiente + " unidades por descontar de lotes.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<BeanLote> listarLotesPaginado(int offset, int limite) {

        ArrayList<BeanLote> lista = new ArrayList<>();

        try {

            Connection cn = con.conectar();

            String sql =
                "SELECT l.*, p.nombreProducto " +
                "FROM lote l " +
                "INNER JOIN producto p " +
                "ON l.idProducto = p.idProducto " +
                "ORDER BY l.fechaVencimiento ASC " +
                "LIMIT ? OFFSET ?";

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, limite);
            ps.setInt(2, offset);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                BeanLote l = new BeanLote();
                l.setIdLote(rs.getInt("idLote"));
                l.setIdProducto(rs.getInt("idProducto"));
                l.setNombreProducto(rs.getString("nombreProducto"));
                l.setCantidad(rs.getInt("cantidad"));
                l.setFechaIngreso(rs.getString("fechaIngreso"));
                l.setFechaVencimiento(rs.getString("fechaVencimiento"));
                lista.add(l);
            }

            rs.close();
            ps.close();
            cn.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public int contarLotes() {

        int total = 0;

        try {

            Connection cn = con.conectar();

            String sql = "SELECT COUNT(*) FROM lote";

            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                total = rs.getInt(1);
            }

            rs.close();
            ps.close();
            cn.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return total;
    }
}