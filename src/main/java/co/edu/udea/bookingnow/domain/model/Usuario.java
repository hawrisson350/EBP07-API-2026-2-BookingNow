package co.edu.udea.bookingnow.domain.model;

public class Usuario {
    private Long idUsuario;
    private String correo;
    private String nombreUsuario;
    private String contrasenaHash;
    private RolUsuario rol;
    private EstadoCuenta estado;

    public Usuario(Long idUsuario, String correo, String nombreUsuario, String contrasenaHash, RolUsuario rol, EstadoCuenta estado) {
        this.idUsuario = idUsuario;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.contrasenaHash = contrasenaHash;
        this.rol = rol;
        this.estado = estado;
    }
    public Long getIdUsuario() { return idUsuario; }
    public String getCorreo() { return correo; }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getContrasenaHash() { return contrasenaHash; }
    public RolUsuario getRol() { return rol; }
    public EstadoCuenta getEstado() { return estado; }
}
