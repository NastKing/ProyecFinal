import javax.swing.*;
import java.awt.*;

public class FacturaGUI extends JFrame {
    public FacturaGUI(int numeroSerie, int cantidad, String cliente, double precio) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JLabel numeroSerieLabel = new JLabel("Número de Serie:" + numeroSerie);
        JLabel cantidadLabel = new JLabel("Cantidad: " + cantidad);
        JLabel clienteLabel = new JLabel("Cliente: " + cliente);
        JLabel precioLabel = new JLabel("Precio: " + precio);

        panel.add(numeroSerieLabel);
        panel.add(cantidadLabel);
        panel.add(clienteLabel);
        panel.add(precioLabel);

        add(panel);

        setTitle("Factura");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
