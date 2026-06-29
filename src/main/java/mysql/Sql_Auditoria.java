package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bean.BeanAuditoria;

public class Sql_Auditoria {

    Conexion con = new Conexion();

    public void registrar(String usuario, String accion) {
        try {
            Connection cn = con.conectar();
            String sql = "INSERT INTO auditoria (usuario, accion, fecha) "
                       + "VALUES (?, ?, NOW())";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, accion);
            ps.executeUpdate();
            ps.close();
            cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<BeanAuditoria> listarAuditoria() {
        ArrayList<BeanAuditoria> lista = new ArrayList<>();
        try {
            Connection cn = con.conectar();
            String sql = "SELECT * FROM auditoria ORDER BY fecha DESC";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BeanAuditoria a = new BeanAuditoria();
                a.setIdAuditoria(rs.getInt("idAuditoria"));
                a.setUsuario(rs.getString("usuario"));
                a.setAccion(rs.getString("accion"));
                a.setFecha(rs.getString("fecha"));
                lista.add(a);
            }
            rs.close();
            ps.close();
            cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    public int contarAuditoria() {
        int total = 0;
        try {
            Connection cn = con.conectar();
            String sql = "SELECT COUNT(*) FROM auditoria";
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

    public ArrayList<BeanAuditoria> listarAuditoriaPaginado(int offset, int filas) {
        ArrayList<BeanAuditoria> lista = new ArrayList<>();
        try {
            Connection cn = con.conectar();
            String sql = "SELECT * FROM auditoria ORDER BY fecha DESC LIMIT ? OFFSET ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, filas);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BeanAuditoria a = new BeanAuditoria();
                a.setIdAuditoria(rs.getInt("idAuditoria"));
                a.setUsuario(rs.getString("usuario"));
                a.setAccion(rs.getString("accion"));
                a.setFecha(rs.getString("fecha"));
                lista.add(a);
            }
            rs.close();
            ps.close();
            cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}