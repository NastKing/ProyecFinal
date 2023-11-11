import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Venta extends JFrame {
    JButton venderButton;
    JButton imprimirFacturaButton;
    JTextField numeroSerieField, cantidadField, clienteField, apellidoField, precioField, nombre_producto;
    Connection connection;

    public Venta(Connection connection) {
        this.connection = connection;

        venderButton = new JButton("Realizar Venta");
        venderButton.addActionListener(e -> guardarVenta());

        imprimirFacturaButton = new JButton("Imprimir Factura");
        imprimirFacturaButton.addActionListener(e -> imprimirFactura());

        JPanel box = new JPanel(null);

        int buttonWidth = 150;
        int buttonHeight = 50;

        JLabel numeroSerieLabel = new JLabel("Número de Serie:");
        numeroSerieLabel.setBounds(50, 50, 120, 30);
        box.add(numeroSerieLabel);

        numeroSerieField = new JTextField();
        numeroSerieField.setBounds(200, 50, 200, 30);
        box.add(numeroSerieField);

        nombre_producto = new JTextField();
        nombre_producto.setBounds(200, 250, 200, 30);
        box.add(nombre_producto);

        JLabel cantidadLabel = new JLabel("Cantidad:");
        cantidadLabel.setBounds(50, 100, 120, 30);
        box.add(cantidadLabel);

        JLabel nombre_producto = new JLabel("Nombre Producto:");
        nombre_producto.setBounds(50, 250, 320, 30);
        box.add(nombre_producto);

        cantidadField = new JTextField();
        cantidadField.setBounds(200, 100, 200, 30);
        box.add(cantidadField);

        JLabel clienteLabel = new JLabel("Nombre Cliente:");
        clienteLabel.setBounds(50, 150, 120, 30);
        box.add(clienteLabel);

        clienteField = new JTextField();
        clienteField.setBounds(200, 150, 200, 30);
        box.add(clienteField);

        JLabel apellidoLabel = new JLabel("Apellido Cliente:");
        apellidoLabel.setBounds(50, 200, 120, 30);
        box.add(apellidoLabel);

        apellidoField = new JTextField();
        apellidoField.setBounds(200, 200, 200, 30);
        box.add(apellidoField);

        imprimirFacturaButton.setSize(buttonWidth, buttonHeight);
        imprimirFacturaButton.setLocation(150, 370);
        box.add(imprimirFacturaButton);

        venderButton.setSize(buttonWidth, buttonHeight);
        venderButton.setLocation(150, 320);
        box.add(venderButton);

        add(box);

        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void guardarVenta() {
        try {
            int numeroSerie = Integer.parseInt(numeroSerieField.getText());
            int cantidad = Integer.parseInt(cantidadField.getText());
            String nombreCliente = clienteField.getText();
            String apellidoCliente = apellidoField.getText();
            double precio = 0.0;
            String nombreProducto = nombre_producto.getText();
            FacturaGUI factura = new FacturaGUI(numeroSerie, cantidad, nombreCliente + " " + apellidoCliente, precio);

                String queryInventar = "SELECT precio_unitario FROM inventario WHERE nombre_producto = ?";
                PreparedStatement pstInventar = connection.prepareStatement(queryInventar);
                pstInventar.setString(1, nombreProducto);
                ResultSet PUInventario = pstInventar.executeQuery();
                if (PUInventario.next()) {
                    precio = PUInventario.getDouble("precio_unitario");
                }

            String queryCliente = "SELECT * FROM clientes WHERE nombre = ? AND apellido = ?";
            PreparedStatement pstCliente = connection.prepareStatement(queryCliente);
            pstCliente.setString(1, nombreCliente);
            pstCliente.setString(2, apellidoCliente);
            ResultSet rsCliente = pstCliente.executeQuery();

            if (rsCliente.next()) {
                String query = "INSERT INTO venta (numero_serie, cantidad, cliente, precio) VALUES (?, ?, ?, ?)";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setInt(1, numeroSerie);
                pst.setInt(2, cantidad);
                pst.setString(3, nombreCliente + " " + apellidoCliente);
                pst.setDouble(4, precio);
                pst.executeUpdate();

                
                
                String queryInventario = "SELECT cantidad FROM inventario WHERE nombre_producto = ?";
                PreparedStatement pstInventario = connection.prepareStatement(queryInventario);
                pstInventario.setString(1, nombreProducto);
                ResultSet rsInventario = pstInventario.executeQuery();
                if (rsInventario.next()) {
                    int cantidadActual = rsInventario.getInt("cantidad");
                    int nuevaCantidad = cantidadActual - cantidad;
                    System.out.println(nuevaCantidad);
                   
                    String queryUpdateInventario = "UPDATE inventario SET cantidad = ? WHERE nombre_producto = ?";
                    PreparedStatement pstUpdateInventario = connection.prepareStatement(queryUpdateInventario);
                    pstUpdateInventario.setInt(1, nuevaCantidad);
                    pstUpdateInventario.setString(2, nombreProducto);
                    pstUpdateInventario.executeUpdate();
                }

                
                System.out.println("Venta registrada exitosamente.");
            } else {
                
                System.out.println("El cliente no está registrado en la base de datos.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error al guardar la venta");
        }
    }

    private void imprimirFactura() {
        try {
            String nombreCliente = clienteField.getText();
            String apellidoCliente = apellidoField.getText();
            String clienteCompleto = nombreCliente + " " + apellidoCliente;
            
            String query = "SELECT * FROM venta WHERE cliente=?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, clienteCompleto); 
            ResultSet rs = pst.executeQuery();
    
            
            while (rs.next()) {
                int numeroSerie = rs.getInt("numero_serie");
                int cantidad = rs.getInt("cantidad");
                String cliente = rs.getString("cliente");
                double precio = rs.getDouble("precio");
    
                
                FacturaGUI factura = new FacturaGUI(numeroSerie, cantidad, cliente, precio);
            }
            connection.close(); 
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener datos de la base de datos");
        }
    }
}
