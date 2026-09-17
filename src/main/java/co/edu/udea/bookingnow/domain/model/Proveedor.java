package co.edu.udea.bookingnow.domain.model;

public class Proveedor {
    private Long idProveedor;
    private String correo;
    private String nombreUsuario;
    private String razonSocial;
    private String nit;
    private String contrasenaHash;

    public Proveedor() {}

    public Proveedor(Long idProveedor, String correo, String nombreUsuario, String razonSocial, String nit, String contrasenaHash) {
        this.idProveedor = idProveedor;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.razonSocial = razonSocial;
        this.nit = nit;
        this.contrasenaHash = contrasenaHash;
    }

    public Long getIdProveedor() { return idProveedor; }
    public void setIdProveedor(Long idProveedor) { this.idProveedor = idProveedor; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public String getContrasenaHash() { return contrasenaHash; }
    public void setContrasenaHash(String contrasenaHash) { this.contrasenaHash = contrasenaHash; }
}
