package bean;

public class BeanProveedor {

    private int idProveedor;
    private String razonSocial;
    private String ruc;
    private String direccion;
    private String telefono;

    public BeanProveedor() {
    }

    public BeanProveedor(int idProveedor, String razonSocial,
            String ruc, String direccion,
            String telefono) {

        this.idProveedor = idProveedor;
        this.razonSocial = razonSocial;
        this.ruc = ruc;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}