package bean;

public class BeanVenta {

    private int idVenta;
    private String fechaVenta;
    private double total;
    private int idUsuario;
    private double igv;
    private String metodoPago;
    private double vuelto;

    public BeanVenta() {
    }

    public BeanVenta(int idVenta,
            String fechaVenta,
            double total,
            int idUsuario) {

        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.total = total;
        this.idUsuario = idUsuario;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public double getIgv() { return igv; }
    public void setIgv(double igv) { this.igv = igv; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public double getVuelto() { return vuelto; }
    public void setVuelto(double vuelto) { this.vuelto = vuelto; }

}