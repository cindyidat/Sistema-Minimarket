package bean;

public class BeanMerma {

    private int idMerma;
    private int idProducto;
    private int cantidad;
    private String motivo;
    private String fecha;

    public BeanMerma() {
    }

    public int getIdMerma() {
        return idMerma;
    }

    public void setIdMerma(int idMerma) {
        this.idMerma = idMerma;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}