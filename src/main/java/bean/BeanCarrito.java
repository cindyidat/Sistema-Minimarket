package bean;

public class BeanCarrito {

    private int idProducto;
    private String nombreProducto;
    private int cantidad;
    private double precio;
    private double subtotal;
    private String tipoIgv;

    public BeanCarrito() {
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    public String getTipoIgv() { return tipoIgv; }
    public void setTipoIgv(String tipoIgv) { this.tipoIgv = tipoIgv; }
}