
public class Cliente {
    private String nombre;
    private String apellido;
    private int nit;
    private int id;

    public Cliente(String nombre, String apellido, int nit, int id) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nit = nit;
        this.id = id;
    }

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getNit() {
        return nit;
    }

    public void setNit(int nit) {
        this.nit = nit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
