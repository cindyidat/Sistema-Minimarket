package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bean.BeanProducto;

public class Sql_Producto {

    Conexion con = new Conexion();

    // LISTAR STOCK BAJO

    public ArrayList<BeanProducto> listarProductosStockBajo() {

        ArrayList<BeanProducto> lista = new ArrayList<BeanProducto>();

        try {

            Connection cn = con.conectar();

            String sql =
                    "SELECT p.*, c.nombreCategoria " +
                    "FROM producto p " +
                    "INNER JOIN categoria c " +
                    "ON p.idCategoria = c.idCategoria " +
                    "WHERE p.stock <= p.stockMinimo";

            PreparedStatement ps = cn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                BeanProducto p = new BeanProducto();

                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombreProducto(rs.getString("nombreProducto"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecioCompra(rs.getDouble("precioCompra"));
                p.setPrecioVenta(rs.getDouble("precioVenta"));
                p.setStock(rs.getInt("stock"));
                p.setStockMinimo(rs.getInt("stockMinimo"));
                p.setIdCategoria(rs.getInt("idCategoria"));
                p.setNombreCategoria(rs.getString("nombreCategoria"));
                p.setIdProveedor(rs.getInt("idProveedor"));
                p.setEstado(rs.getString("estado"));

                lista.add(p);
            }

            rs.close();
            ps.close();
            cn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // LISTAR PRODUCTOS

    public ArrayList<BeanProducto> listarProductos() {

        ArrayList<BeanProducto> lista = new ArrayList<BeanProducto>();

        try {

            Connection cn = con.conectar();

            String sql =
                    "SELECT p.*, c.nombreCategoria " +
                    "FROM producto p " +
                    "INNER JOIN categoria c " +
                    "ON p.idCategoria = c.idCategoria";

            PreparedStatement ps = cn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                BeanProducto p = new BeanProducto();

                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombreProducto(rs.getString("nombreProducto"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecioCompra(rs.getDouble("precioCompra"));
                p.setPrecioVenta(rs.getDouble("precioVenta"));
                p.setStock(rs.getInt("stock"));
                p.setStockMinimo(rs.getInt("stockMinimo"));
                p.setIdCategoria(rs.getInt("idCategoria"));
                p.setNombreCategoria(rs.getString("nombreCategoria"));
                p.setIdProveedor(rs.getInt("idProveedor"));
                p.setEstado(rs.getString("estado"));

                lista.add(p);
            }

            rs.close();
            ps.close();
            cn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public ArrayList<BeanProducto> listarProductosActivos() {

        ArrayList<BeanProducto> lista = new ArrayList<>();

        try {

            Connection cn = con.conectar();

            String sql =
                "SELECT p.*, c.nombreCategoria " +
                "FROM producto p " +
                "INNER JOIN categoria c " +
                "ON p.idCategoria = c.idCategoria " +
                "WHERE p.estado='ACTIVO'";

            PreparedStatement ps = cn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                BeanProducto p = new BeanProducto();

                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombreProducto(rs.getString("nombreProducto"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecioCompra(rs.getDouble("precioCompra"));
                p.setPrecioVenta(rs.getDouble("precioVenta"));
                p.setStock(rs.getInt("stock"));
                p.setStockMinimo(rs.getInt("stockMinimo"));
                p.setIdCategoria(rs.getInt("idCategoria"));
                p.setNombreCategoria(rs.getString("nombreCategoria"));
                p.setIdProveedor(rs.getInt("idProveedor"));
                p.setEstado(rs.getString("estado"));

                lista.add(p);
            }

            rs.close();
            ps.close();
            cn.close();

        } catch(Exception e){
            e.printStackTrace();
        }

        return lista;
    }

    // REGISTRAR

    public void registrarProducto(BeanProducto p) {

        try {

            Connection cn = con.conectar();

            String sql =
                    "INSERT INTO producto(" +
                    "nombreProducto," +
                    "descripcion," +
                    "precioCompra," +
                    "precioVenta," +
                    "stock," +
                    "stockMinimo," +
                    "idCategoria," +
                    "idProveedor," +
                    "estado) VALUES(?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setString(1, p.getNombreProducto());
            ps.setString(2, p.getDescripcion());
            ps.setDouble(3, p.getPrecioCompra());
            ps.setDouble(4, p.getPrecioVenta());
            ps.setInt(5, p.getStock());
            ps.setInt(6, p.getStockMinimo());
            ps.setInt(7, p.getIdCategoria());
            ps.setInt(8, p.getIdProveedor());
            ps.setString(9, p.getEstado());

            ps.executeUpdate();

            ps.close();
            cn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // BUSCAR POR ID

    public BeanProducto buscarProducto(int id) {

        BeanProducto p = new BeanProducto();

        try {

            Connection cn = con.conectar();

            String sql =
                    "SELECT p.*, c.nombreCategoria " +
                    "FROM producto p " +
                    "INNER JOIN categoria c " +
                    "ON p.idCategoria = c.idCategoria " +
                    "WHERE p.idProducto=?";

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombreProducto(rs.getString("nombreProducto"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecioCompra(rs.getDouble("precioCompra"));
                p.setPrecioVenta(rs.getDouble("precioVenta"));
                p.setStock(rs.getInt("stock"));
                p.setStockMinimo(rs.getInt("stockMinimo"));
                p.setIdCategoria(rs.getInt("idCategoria"));
                p.setNombreCategoria(rs.getString("nombreCategoria"));
                p.setIdProveedor(rs.getInt("idProveedor"));
                p.setEstado(rs.getString("estado"));
                p.setTipoIgv(rs.getString("tipoIgv"));
            }

            rs.close();
            ps.close();
            cn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return p;
    }
    
    // ACTUALIZAR

    public void actualizarProducto(
            BeanProducto p) {

        try {

            Connection cn =
                    con.conectar();

            String sql =
            "UPDATE producto SET "
            + "nombreProducto=?,"
            + "descripcion=?,"
            + "precioCompra=?,"
            + "precioVenta=?,"
            + "stock=?,"
            + "stockMinimo=?,"
            + "idCategoria=?,"
            + "idProveedor=?,"
            + "estado=? "
            + "WHERE idProducto=?";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setString(1,
                    p.getNombreProducto());

            ps.setString(2,
                    p.getDescripcion());

            ps.setDouble(3,
                    p.getPrecioCompra());

            ps.setDouble(4,
                    p.getPrecioVenta());

            ps.setInt(5,
                    p.getStock());

            ps.setInt(6,
                    p.getStockMinimo());

            ps.setInt(7,
                    p.getIdCategoria());

            ps.setInt(8,
                    p.getIdProveedor());

            ps.setString(9,
                    p.getEstado());

            ps.setInt(10,
                    p.getIdProducto());

            ps.executeUpdate();

            ps.close();
            cn.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    // ELIMINAR

    public boolean eliminarProducto(
            int id) {

        try {

            Connection cn =
                    con.conectar();

            String sql =
                    "DELETE FROM producto "
                    + "WHERE idProducto=?";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

            ps.close();
            cn.close();

            return true;

        } catch(Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    // ACTUALIZAR STOCK

    public void actualizarStock(
            int idProducto,
            int cantidad) {

        try {

            Connection cn =
                    con.conectar();

            String sql =
            "UPDATE producto "
            + "SET stock = stock - ? "
            + "WHERE idProducto=?";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setInt(1, cantidad);

            ps.setInt(2, idProducto);

            ps.executeUpdate();

            ps.close();
            cn.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
    
 // RESTAR STOCK POR MERMA

    public void restarStockPorMerma(
            int idProducto,
            int cantidad) {

        try {

            Connection cn =
                    con.conectar();

            String sql =
            "UPDATE producto "
            + "SET stock = stock - ? "
            + "WHERE idProducto=?";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setInt(1, cantidad);

            ps.setInt(2, idProducto);

            ps.executeUpdate();

            ps.close();
            cn.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
 // BUSCAR PRODUCTOS POR NOMBRE (para autocompletado)

    public ArrayList<BeanProducto> buscarProductosPorNombre(String texto) {

        ArrayList<BeanProducto> lista = new ArrayList<BeanProducto>();

        try {

            Connection cn = con.conectar();

            String sql =
                    "SELECT p.*, c.nombreCategoria " +
                    "FROM producto p " +
                    "INNER JOIN categoria c " +
                    "ON p.idCategoria = c.idCategoria " +
                    "WHERE p.estado='ACTIVO' " +
                    "AND p.nombreProducto LIKE ? " +
                    "ORDER BY p.nombreProducto ASC " +
                    "LIMIT 10";

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setString(1, "%" + texto + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                BeanProducto p = new BeanProducto();

                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombreProducto(rs.getString("nombreProducto"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecioCompra(rs.getDouble("precioCompra"));
                p.setPrecioVenta(rs.getDouble("precioVenta"));
                p.setStock(rs.getInt("stock"));
                p.setStockMinimo(rs.getInt("stockMinimo"));
                p.setIdCategoria(rs.getInt("idCategoria"));
                p.setNombreCategoria(rs.getString("nombreCategoria"));
                p.setIdProveedor(rs.getInt("idProveedor"));
                p.setEstado(rs.getString("estado"));

                lista.add(p);
            }

            rs.close();
            ps.close();
            cn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public ArrayList<BeanProducto> listarProductosPaginado(int offset, int limite) {

        ArrayList<BeanProducto> lista = new ArrayList<>();

        try {

            Connection cn = con.conectar();

            String sql =
                "SELECT p.*, c.nombreCategoria " +
                "FROM producto p " +
                "INNER JOIN categoria c " +
                "ON p.idCategoria = c.idCategoria " +
                "ORDER BY p.idProducto ASC " +
                "LIMIT ? OFFSET ?";

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, limite);
            ps.setInt(2, offset);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                BeanProducto p = new BeanProducto();
                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombreProducto(rs.getString("nombreProducto"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecioCompra(rs.getDouble("precioCompra"));
                p.setPrecioVenta(rs.getDouble("precioVenta"));
                p.setStock(rs.getInt("stock"));
                p.setStockMinimo(rs.getInt("stockMinimo"));
                p.setIdCategoria(rs.getInt("idCategoria"));
                p.setNombreCategoria(rs.getString("nombreCategoria"));
                p.setIdProveedor(rs.getInt("idProveedor"));
                p.setEstado(rs.getString("estado"));
                lista.add(p);
            }

            rs.close();
            ps.close();
            cn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public int contarProductos() {

        int total = 0;

        try {

            Connection cn = con.conectar();

            String sql = "SELECT COUNT(*) FROM producto";

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