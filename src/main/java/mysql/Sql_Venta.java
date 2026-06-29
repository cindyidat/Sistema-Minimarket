package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bean.BeanVenta;

public class Sql_Venta {

    Conexion con = new Conexion();

    public int registrarVentaRetornandoId(BeanVenta v) {

        int idVenta = 0;

        try {

            Connection cn = con.conectar();

            String sql =
                "INSERT INTO venta("
                + "fechaVenta,"
                + "total,"
                + "igv,"
                + "metodoPago,"
                + "vuelto,"
                + "idUsuario)"
                + " VALUES(?,?,?,?,?,?)";

            PreparedStatement ps =
                    cn.prepareStatement(
                    sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, v.getFechaVenta());
            ps.setDouble(2, v.getTotal());
            ps.setDouble(3, v.getIgv());
            ps.setString(4, v.getMetodoPago());
            ps.setDouble(5, v.getVuelto());
            ps.setInt(6, v.getIdUsuario());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                idVenta = rs.getInt(1);
            }

            rs.close();
            ps.close();
            cn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return idVenta;
    }

    public ArrayList<BeanVenta> listarVentas() {

        ArrayList<BeanVenta> lista = new ArrayList<>();

        try {

            Connection cn = con.conectar();

            String sql =
                "SELECT * FROM venta "
                + "ORDER BY idVenta DESC";

            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                BeanVenta v = new BeanVenta();
                v.setIdVenta(rs.getInt("idVenta"));
                v.setFechaVenta(rs.getString("fechaVenta"));
                v.setTotal(rs.getDouble("total"));
                v.setIgv(rs.getDouble("igv"));
                v.setMetodoPago(rs.getString("metodoPago"));
                v.setVuelto(rs.getDouble("vuelto"));
                v.setIdUsuario(rs.getInt("idUsuario"));
                lista.add(v);
            }

            rs.close();
            ps.close();
            cn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public ArrayList<BeanVenta> listarVentasPaginado(int offset, int limite) {

        ArrayList<BeanVenta> lista = new ArrayList<>();

        try {

            Connection cn = con.conectar();

            String sql =
                "SELECT * FROM venta "
                + "ORDER BY idVenta DESC "
                + "LIMIT ? OFFSET ?";

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, limite);
            ps.setInt(2, offset);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                BeanVenta v = new BeanVenta();
                v.setIdVenta(rs.getInt("idVenta"));
                v.setFechaVenta(rs.getString("fechaVenta"));
                v.setTotal(rs.getDouble("total"));
                v.setIgv(rs.getDouble("igv"));
                v.setMetodoPago(rs.getString("metodoPago"));
                v.setVuelto(rs.getDouble("vuelto"));
                v.setIdUsuario(rs.getInt("idUsuario"));
                lista.add(v);
            }

            rs.close();
            ps.close();
            cn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public int contarVentas() {

        int total = 0;

        try {

            Connection cn = con.conectar();
            String sql = "SELECT COUNT(*) FROM venta";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt(1);
            }

            rs.close();
            ps.close();
            cn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }
}