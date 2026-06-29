package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;

import bean.BeanDetalleVenta;

public class Sql_DetalleVenta {

    Conexion con = new Conexion();

    public void registrarDetalle(
            BeanDetalleVenta d) {

        try {

            Connection cn =
                    con.conectar();

            String sql =
            "INSERT INTO detalle_venta("
            + "idVenta,"
            + "idProducto,"
            + "cantidad,"
            + "precio,"
            + "subtotal)"
            + " VALUES(?,?,?,?,?)";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setInt(
                    1,
                    d.getIdVenta());

            ps.setInt(
                    2,
                    d.getIdProducto());

            ps.setInt(
                    3,
                    d.getCantidad());

            ps.setDouble(
                    4,
                    d.getPrecio());

            ps.setDouble(
                    5,
                    d.getSubtotal());

            ps.executeUpdate();

            ps.close();
            cn.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
}