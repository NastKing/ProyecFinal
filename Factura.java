public class Factura {
    private int numeroSerie;
    private String nombreCliente;
    private int cantidadVendida;
    private double precio;

    public Factura(int numeroSerie, String nombreCliente, int cantidadVendida, double precio) {
        this.numeroSerie = numeroSerie;
        this.nombreCliente = nombreCliente;
        this.cantidadVendida = cantidadVendida;
        this.precio = precio;
    }

    public int getNumeroSerie() {
        return numeroSerie;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public double getPrecio() {
        return precio;
    }

    public void setNumeroSerie(int numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double calcularImporteTotal() {
        return cantidadVendida * precio;
    }

    @Override
    public String toString() {
        return "Factura [numeroSerie=" + numeroSerie + ", nombreCliente=" + nombreCliente + ", cantidadVendida=" + cantidadVendida + ", precio=" + precio + ", importeTotal=" + calcularImporteTotal() + "]";
    }
}