package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bean.BeanMerma;

public class Sql_Merma {

    Conexion con = new Conexion();

    // LISTAR

    public ArrayList<BeanMerma> listarMermas() {

        ArrayList<BeanMerma> lista =
                new ArrayList<BeanMerma>();

        try {

            Connection cn =
                    con.conectar();

            String sql =
                    "SELECT * FROM merma";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()) {

                BeanMerma m =
                        new BeanMerma();

                m.setIdMerma(
                        rs.getInt("idMerma"));

                m.setIdProducto(
                        rs.getInt("idProducto"));

                m.setCantidad(
                        rs.getInt("cantidad"));

                m.setMotivo(
                        rs.getString("motivo"));

                m.setFecha(
                        rs.getString("fecha"));

                lista.add(m);
            }

            rs.close();
            ps.close();
            cn.close();

        } catch(Exception e) {

            e.printStackTrace();
        }

        return lista;
    }

    // REGISTRAR

    public void registrarMerma(BeanMerma m) {

        try {

            Connection cn =
                    con.conectar();

            String sql =
            "INSERT INTO merma("
            + "idProducto,"
            + "cantidad,"
            + "motivo,"
            + "fecha)"
            + " VALUES(?,?,?,?)";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setInt(1,
                    m.getIdProducto());

            ps.setInt(2,
                    m.getCantidad());

            ps.setString(3,
                    m.getMotivo());

            ps.setString(4,
                    m.getFecha());

            ps.executeUpdate();

            ps.close();
            cn.close();

            // DESCONTAR STOCK DEL PRODUCTO
            Sql_Producto sqlProducto =
                    new Sql_Producto();

            sqlProducto.restarStockPorMerma(
                    m.getIdProducto(),
                    m.getCantidad());

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
}