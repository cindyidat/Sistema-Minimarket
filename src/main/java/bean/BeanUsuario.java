package bean;

public class BeanUsuario {

    private int idUsuario;
    private String nombre;
    private String dni;
    private String usuario;
    private String clave;
    private int idRol;
    private String nombreRol;
    private String telefono;
    private String correo;
    private String estado;
    private String pin;

    public BeanUsuario() {
    }

    public BeanUsuario(int idUsuario, String nombre, String dni, String usuario,
            String clave, int idRol, String telefono, String correo, String estado) {

        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.dni = dni;
        this.usuario = usuario;
        this.clave = clave;
        this.idRol = idRol;
        this.telefono = telefono;
        this.correo = correo;
        this.estado = estado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

}