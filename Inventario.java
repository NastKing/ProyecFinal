import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Inventario extends JFrame {
    JButton verInventarioButton;
    JButton agregarProductoButton;
    Connection connection;

    public Inventario(Connection connection) {
        this.connection = connection;

        verInventarioButton = new JButton("Ver Inventario");
        verInventarioButton.addActionListener(e -> verInventario());

        agregarProductoButton = new JButton("Agregar Producto");
        agregarProductoButton.addActionListener(e -> agregarProducto());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.add(verInventarioButton);
        panel.add(agregarProductoButton);

        add(panel);

        setTitle("Inventario");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }


    private void verInventario() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM inventario");
            JTextArea rsarea = new JTextArea(10, 30);
            StringBuilder results = new StringBuilder();
            while (rs.next()) {
                results.append("Id: ").append(rs.getInt("id")).append(", \nNombre Producto: ").append(rs.getString("nombre_producto"))
                        .append(", \nCantidad: ").append(rs.getInt("cantidad")).append(", \nPrecio Unitario: ").append(rs.getDouble("precio_unitario"))
                        .append("\n");
            }
            rsarea.setText(results.toString());
            rsarea.setEditable(false);
            JOptionPane.showMessageDialog(this, new JScrollPane(rsarea), "Inventario", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al consultar el inventario");
        }
    }

private void agregarProducto() {
    try {
        String nombreProducto = JOptionPane.showInputDialog("Ingrese el nombre del producto:");
        int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad:"));
        double precioUnitario = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio unitario:"));

        String query = "INSERT INTO inventario (nombre_producto, cantidad, precio_unitario) VALUES (?, ?, ?)";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, nombreProducto);
        pst.setInt(2, cantidad);
        pst.setDouble(3, precioUnitario);
        pst.executeUpdate();

        System.out.println("Producto agregado al inventario.");
    } catch (Exception ex) {
        ex.printStackTrace();
        System.out.println("Error al agregar el producto al inventario");
    }
}
}
