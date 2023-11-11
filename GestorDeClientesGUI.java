import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;;

public class GestorDeClientesGUI  extends JFrame implements ActionListener {
    JButton ingresarBoton, actualizarBoton, eliminarBoton, consultarBoton;
    JButton regresarBoton;
    JTextField nombreField, apellidoField, nitField, idField;
    Connection connection;
    
    public GestorDeClientesGUI() {
        try {
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/fabrica", "root", "1234");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al conectar");
        }
        

        ingresarBoton = new JButton("Ingresar Cliente");
        actualizarBoton = new JButton("Actualizar Cliente");
        eliminarBoton = new JButton("Eliminar Cliente");
        consultarBoton = new JButton("Consultar Cliente");
        regresarBoton = new JButton("Regresar a Menu Principal");
        nombreField = new JTextField();
        apellidoField = new JTextField();
        nitField = new JTextField();
        idField = new JTextField();

        JPanel box = new JPanel(null);

        int buttonWidth = 150;
        int buttonHeight = 50;

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setBounds(50, 100, 100, 30);
        box.add(nombreLabel);

        JLabel apellidoLabel = new JLabel("Apellido:");
        apellidoLabel.setBounds(50, 150, 100, 30);
        box.add(apellidoLabel);

        JLabel nitLabel = new JLabel("NIT:");
        nitLabel.setBounds(50, 200, 100, 30);
        box.add(nitLabel);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(50, 250, 100, 30);
        box.add(idLabel);
            
        ingresarBoton.setSize(buttonWidth, buttonHeight);
        ingresarBoton.setLocation(50, 300);
        ingresarBoton.addActionListener(this);
        box.add(ingresarBoton);

        actualizarBoton.setSize(buttonWidth, buttonHeight);
        actualizarBoton.setLocation(250, 300);
        actualizarBoton.addActionListener(this);
        box.add(actualizarBoton);

        eliminarBoton.setSize(buttonWidth, buttonHeight);
        eliminarBoton.setLocation(50, 356);
        eliminarBoton.addActionListener(this);
        box.add(eliminarBoton);

        consultarBoton.setSize(buttonWidth, buttonHeight);
        consultarBoton.setLocation(250, 356);
        consultarBoton.addActionListener(this);
        box.add(consultarBoton);

        regresarBoton.setSize(buttonWidth, buttonHeight);
        regresarBoton.setLocation(300, 500);
        regresarBoton.addActionListener(this);
        box.add(regresarBoton);

        
        nombreField.setBounds(150, 100, 200, 30);
        box.add(nombreField);

        apellidoField.setBounds(150, 150, 200, 30);
        box.add(apellidoField);

        nitField.setBounds(150, 200, 200, 30);
        box.add(nitField);

        idField.setBounds(150, 250, 200, 30);
        box.add(idField);
        

        add(box);
      
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent accion) {
        Object accionRealizada = accion.getSource();
        if (accionRealizada == ingresarBoton) {
            try {
                String nombre = nombreField.getText();
                String apellido = apellidoField.getText();
                int nit = Integer.parseInt(nitField.getText());
                int id = Integer.parseInt(idField.getText());
                Cliente nuevoCliente = new Cliente(nombre, apellido, nit, id);
                

                String query = "INSERT INTO clientes (nombre,apellido,nit,id) VALUES(?,?,?,?)";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1, nuevoCliente.getNombre());
                pst.setString(2, nuevoCliente.getApellido());
                pst.setInt(3, nuevoCliente.getNit());
                pst.setInt(4, nuevoCliente.getId());
                pst.executeUpdate();
            } catch (SQLException | NumberFormatException ex) {
                ex.printStackTrace();
                System.out.println("Error al ingresar datos");
            }
        } else if (accionRealizada == actualizarBoton) {
            try {
                int id = Integer.parseInt(idField.getText());
                String nombre = nombreField.getText();
                String apellido = apellidoField.getText();
                int nit = Integer.parseInt(nitField.getText());
                String query = "UPDATE clientes SET nombre=?, apellido=?, nit=? WHERE id=?";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1, nombre);
                pst.setString(2, apellido);
                pst.setInt(3, nit);
                pst.setInt(4, id);
                pst.executeUpdate();
            } catch (SQLException | NumberFormatException ex) {
                ex.printStackTrace();
                System.out.println("Error al actualizar");
            }
        } else if (accionRealizada == eliminarBoton) {
            try {
                int id = Integer.parseInt(idField.getText());
                String query = "DELETE FROM clientes WHERE id=?";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setInt(1, id);
                pst.executeUpdate();
            } catch (SQLException | NumberFormatException ex) {
                ex.printStackTrace();
                System.out.println("Error al eliminar");
            }
        } else if (accionRealizada == consultarBoton) {
              try {
                    int id = Integer.parseInt(idField.getText());
                    String query = "SELECT * FROM clientes WHERE id=?";
                    PreparedStatement pst = connection.prepareStatement(query);
                    pst.setInt(1, id);
                    ResultSet resultSet = pst.executeQuery();
                    
                    
                    JTextArea resultArea = new JTextArea(10, 30);
                    while (resultSet.next()) {
                        resultArea.append("ID: " + resultSet.getInt("id") + "\n");
                        resultArea.append("Nombre: " + resultSet.getString("nombre") + "\n");
                        resultArea.append("Apellido: " + resultSet.getString("apellido") + "\n");
                        resultArea.append("NIT: " + resultSet.getInt("nit") + "\n");
                    }
                    resultArea.setEditable(false);
                    JOptionPane.showMessageDialog(this, new JScrollPane(resultArea), "Resultado de la consulta", JOptionPane.INFORMATION_MESSAGE);
                        } catch (SQLException | NumberFormatException ex) {
                    ex.printStackTrace();
                    System.out.println("Error al consultar");
                    }
                } else if (accionRealizada== regresarBoton){
                        this.dispose();
                        new Mainprograma();
                    }
                }

    public static void main(String[] args) {
        new GestorDeClientesGUI();
    }
}