package co.edu.udea.bookingnow.domain.model;

public class Cliente {
    private Long idCliente;
    private String correo;
    private String nombreUsuario;
    private String contrasenaHash;

    private co.edu.udea.bookingnow.domain.model.EstadoCuenta estado = co.edu.udea.bookingnow.domain.model.EstadoCuenta.ACTIVA;

    public Cliente() {}

    public Cliente(Long idCliente, String correo, String nombreUsuario, String contrasenaHash) {
        this.idCliente = idCliente;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.contrasenaHash = contrasenaHash;
    }

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContrasenaHash() { return contrasenaHash; }
    public void setContrasenaHash(String contrasenaHash) { this.contrasenaHash = contrasenaHash; }
    public co.edu.udea.bookingnow.domain.model.EstadoCuenta getEstado() { return estado; }
    public void setEstado(co.edu.udea.bookingnow.domain.model.EstadoCuenta estado) { this.estado = estado; }
}
