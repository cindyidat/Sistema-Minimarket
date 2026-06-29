package bean;

public class BeanRol {

    private int idRol;
    private String nombreRol;

    public BeanRol() {
    }

    public BeanRol(int idRol, String nombreRol) {

        this.idRol = idRol;
        this.nombreRol = nombreRol;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

}