package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Sql_Dashboard {

    Conexion cn = new Conexion();

    public int totalProductos() {

        int total = 0;

        try {

            Connection con = cn.conectar();

            String sql =
            "SELECT COUNT(*) FROM producto";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ResultSet rs =
            ps.executeQuery();

            if(rs.next()) {

                total = rs.getInt(1);

            }

            rs.close();
            ps.close();
            con.close();

        } catch(Exception e) {

            e.printStackTrace();

        }

        return total;
    }

    public int totalProveedores() {

        int total = 0;

        try {

            Connection con = cn.conectar();

            String sql =
            "SELECT COUNT(*) FROM proveedor";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ResultSet rs =
            ps.executeQuery();

            if(rs.next()) {

                total = rs.getInt(1);

            }

            rs.close();
            ps.close();
            con.close();

        } catch(Exception e) {

            e.printStackTrace();

        }

        return total;
    }

    public int totalUsuarios() {

        int total = 0;

        try {

            Connection con = cn.conectar();

            String sql =
            "SELECT COUNT(*) FROM usuario";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ResultSet rs =
            ps.executeQuery();

            if(rs.next()) {

                total = rs.getInt(1);

            }

            rs.close();
            ps.close();
            con.close();

        } catch(Exception e) {

            e.printStackTrace();

        }

        return total;
    }

    public int totalVentas() {

        int total = 0;

        try {

            Connection con = cn.conectar();

            String sql =
            "SELECT COUNT(*) FROM venta";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ResultSet rs =
            ps.executeQuery();

            if(rs.next()) {

                total = rs.getInt(1);

            }

            rs.close();
            ps.close();
            con.close();

        } catch(Exception e) {

            e.printStackTrace();

        }

        return total;
    }

    public double montoVendido() {

        double total = 0;

        try {

            Connection con = cn.conectar();

            String sql =
            "SELECT IFNULL(SUM(total),0) FROM venta";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ResultSet rs =
            ps.executeQuery();

            if(rs.next()) {

                total = rs.getDouble(1);

            }

            rs.close();
            ps.close();
            con.close();

        } catch(Exception e) {

            e.printStackTrace();

        }

        return total;
    }

    public int stockBajo() {

        int total = 0;

        try {

            Connection con = cn.conectar();

            String sql =
            "SELECT COUNT(*) " +
            "FROM producto " +
            "WHERE stock <= stockMinimo";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ResultSet rs =
            ps.executeQuery();

            if(rs.next()) {

                total = rs.getInt(1);

            }

            rs.close();
            ps.close();
            con.close();

        } catch(Exception e) {

            e.printStackTrace();

        }

        return total;
    }

    // ==========================
    // PRODUCTOS POR VENCER
    // ==========================

    public int productosPorVencer() {

        int total = 0;

        try {

            Connection con = cn.conectar();

            String sql =
            "SELECT COUNT(*) " +
            "FROM lote " +
            "WHERE fechaVencimiento " +
            "BETWEEN CURDATE() " +
            "AND DATE_ADD(CURDATE(), INTERVAL 7 DAY)";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ResultSet rs =
            ps.executeQuery();

            if(rs.next()) {

                total = rs.getInt(1);

            }

            rs.close();
            ps.close();
            con.close();

        } catch(Exception e) {

            e.printStackTrace();

        }

        return total;
    }

    // ==========================
    // PRODUCTOS VENCIDOS
    // ==========================

    public int productosVencidos() {

        int total = 0;

        try {

            Connection con = cn.conectar();

            String sql =
            "SELECT COUNT(*) " +
            "FROM lote " +
            "WHERE fechaVencimiento < CURDATE()";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ResultSet rs =
            ps.executeQuery();

            if(rs.next()) {

                total = rs.getInt(1);

            }

            rs.close();
            ps.close();
            con.close();

        } catch(Exception e) {

            e.printStackTrace();

        }

        return total;
    }

    // ==========================
    // TOTAL DE NOTIFICACIONES
    // ==========================

    public int totalNotificaciones() {

        return stockBajo()
                + productosPorVencer()
                + productosVencidos();
    }

}