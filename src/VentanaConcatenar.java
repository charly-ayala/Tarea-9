import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaConcatenar extends JFrame {
    private JTextField campo1;
    private JTextField campo2;
    private JButton boton;

    public VentanaConcatenar() {
        setTitle("Concatenador");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        campo1 = new JTextField(20);
        campo2 = new JTextField(20);
        boton = new JButton("Concatenar");

        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto1 = campo1.getText();
                String texto2 = campo2.getText();
                String resultado = texto1 + " " + texto2;

                // Mostrar en ventana un mensaje emergente
                JOptionPane.showMessageDialog(null, "Concatenado: " + resultado);
            }
        });

        add(campo1);
        add(campo2);
        add(boton);

        setVisible(true);
    }

    public static void main(String[] args) {
        new VentanaConcatenar();
    }
}
