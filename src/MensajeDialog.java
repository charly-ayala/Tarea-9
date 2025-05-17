import javax.swing.*;
import java.awt.*;

public class MensajeDialog extends JDialog {
    public MensajeDialog(JFrame parent, String mensaje) {
        super(parent, "Mensaje Concatenado", true);
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // cierre normal
        setLayout(new BorderLayout());

        // Fondo degradado en panel principal (área de contenido)
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(94, 114, 235), 0, getHeight(), new Color(55, 159, 243));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());  // rellena TODO el panel
            }
        };
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel mensajeLabel = new JLabel("<html><center>" + mensaje + "</center></html>", SwingConstants.CENTER);
        mensajeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mensajeLabel.setForeground(Color.WHITE);

        RoundedButton aceptarBtn = new RoundedButton("Aceptar", new Color(255, 87, 34), new Color(255, 120, 80));
        aceptarBtn.setPreferredSize(new Dimension(100, 35));
        aceptarBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        aceptarBtn.setForeground(Color.WHITE);
        aceptarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        aceptarBtn.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(aceptarBtn);

        contentPanel.add(mensajeLabel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPanel);
    }
}
