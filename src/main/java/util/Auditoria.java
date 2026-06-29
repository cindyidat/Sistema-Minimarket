package util;

import bean.BeanUsuario;
import jakarta.servlet.http.HttpSession;
import mysql.Sql_Auditoria;

public class Auditoria {

    public static void registrar(HttpSession sesion, String accion) {
        BeanUsuario usuario = (BeanUsuario) sesion.getAttribute("usuario");
        if (usuario != null) {
            new Sql_Auditoria().registrar(usuario.getUsuario(), accion);
        }
    }
}