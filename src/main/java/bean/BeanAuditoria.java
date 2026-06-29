package bean;

public class BeanAuditoria {
    private int idAuditoria;
    private String usuario;
    private String accion;
    private String fecha;

    public int getIdAuditoria() { return idAuditoria; }
    public void setIdAuditoria(int idAuditoria) { this.idAuditoria = idAuditoria; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}