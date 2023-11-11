import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Mainprograma extends JFrame implements ActionListener {
    JButton opcionesClienteButton;
    JButton ventaBoton;
    JButton inventarioButton;
    JTextField cantidadVendidaField;
    Connection connection;

    public Mainprograma() {
        try {
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/fabrica", "root", "1234");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        
        try {
            BufferedImage image = ImageIO.read(new File("C:\\Users\\Jeann\\Downloads\\OIG.Tsbzx.jpg")); 
            ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(500, 500, Image.SCALE_SMOOTH));
            JLabel label = new JLabel(imageIcon);
            label.setBounds(0, 0, 500, 500);
            add(label);
        } catch (IOException e) {
            e.printStackTrace();
        }
       

        ventaBoton = new JButton("venta");
        ventaBoton.addActionListener(this);

        inventarioButton = new JButton("Inventario");
        inventarioButton.addActionListener(this);

        opcionesClienteButton = new JButton("Opciones cliente");
        opcionesClienteButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        opcionesClienteButton.setSize(150, 50);
        opcionesClienteButton.setLocation(160, 150);
        panel.add(opcionesClienteButton);

        ventaBoton.setSize(150, 50);
        ventaBoton.setLocation(160, 200);
        panel.add(ventaBoton);

        inventarioButton.setSize(150, 50);
        inventarioButton.setLocation(160, 250);
        panel.add(inventarioButton);

        

        add(panel);

        setTitle("Programa Principal");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent accion) {
        Object accionRealizada = accion.getSource();
        if (accionRealizada == opcionesClienteButton) {
            new GestorDeClientesGUI();
        } else if (accionRealizada == ventaBoton) {
            new Venta(connection);
        } else if (accionRealizada == inventarioButton) {
            new Inventario(connection);
        } 
    }

    public static void main(String[] args) {
        new Mainprograma();
    }
}
