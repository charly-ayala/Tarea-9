import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaConcatenar extends JFrame {
    private RoundedTextField texto1, texto2;
    private RoundedButton concatButton, salirButton;
    private JPanel loadingPanel, mainPanel, cardPanel;
    private CardLayout cardLayout;
    private LoadingSpinner loadingSpinner;

    public VentanaConcatenar() {
        setTitle("Concatenar Textos");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        loadingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(94, 114, 235), 0, getHeight(), new Color(55, 159, 243));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        loadingPanel.setLayout(null);

        JLabel loadingLabel = new JLabel("Cargando...", SwingConstants.CENTER);
        loadingLabel.setBounds(40, 150, 220, 30);
        loadingLabel.setForeground(Color.WHITE);
        loadingLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        loadingSpinner = new LoadingSpinner();
        loadingSpinner.setBounds(125, 100, 50, 50);

        loadingPanel.add(loadingSpinner);
        loadingPanel.add(loadingLabel);

        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(94, 114, 235), 0, getHeight(), new Color(55, 159, 243));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(null);

        JLabel label1 = new JLabel("Texto 1:");
        label1.setBounds(40, 50, 100, 30);
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("Segoe UI", Font.BOLD, 14));

        texto1 = new RoundedTextField(10);
        texto1.setBounds(40, 80, 220, 45);
        texto1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        texto1.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(10, new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 5, 12, 5)
        ));
        texto1.setBackground(new Color(255, 255, 255, 230));
        texto1.setForeground(Color.BLACK);

        JLabel label2 = new JLabel("Texto 2:");
        label2.setBounds(40, 130, 100, 30);
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("Segoe UI", Font.BOLD, 14));

        texto2 = new RoundedTextField(10);
        texto2.setBounds(40, 160, 220, 45);
        texto2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        texto2.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(10, new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 5, 12, 5)
        ));
        texto2.setBackground(new Color(255, 255, 255, 230));
        texto2.setForeground(Color.BLACK);

        concatButton = new RoundedButton("Concatenar", new Color(255, 87, 34), new Color(255, 120, 80));
        concatButton.setBounds(40, 250, 220, 40);
        concatButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        concatButton.setForeground(Color.WHITE);
        concatButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        concatButton.addActionListener(e -> {
            String mensaje = texto1.getText() + " " + texto2.getText();
            MensajeDialog dialog = new MensajeDialog(VentanaConcatenar.this, mensaje);
            dialog.setVisible(true);
        });

        salirButton = new RoundedButton("Salir", new Color(220, 53, 69), new Color(255, 80, 100));
        salirButton.setBounds(40, 300, 220, 40);
        salirButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        salirButton.setForeground(Color.WHITE);
        salirButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        salirButton.addActionListener(e -> System.exit(0));

        mainPanel.add(label1);
        mainPanel.add(texto1);
        mainPanel.add(label2);
        mainPanel.add(texto2);
        mainPanel.add(concatButton);
        mainPanel.add(salirButton);

        cardPanel.add(loadingPanel, "loading");
        cardPanel.add(mainPanel, "main");
        setContentPane(cardPanel);

        loadingSpinner.animateFadeIn();
        loadingLabel.setForeground(new Color(255, 255, 255, 0));
        Timer fadeInLabel = new Timer(50, null);
        final float[] labelAlpha = {0f};
        fadeInLabel.addActionListener(e -> {
            labelAlpha[0] += 0.1f;
            if (labelAlpha[0] >= 1f) {
                labelAlpha[0] = 1f;
                fadeInLabel.stop();
            }
            loadingLabel.setForeground(new Color(255, 255, 255, (int)(255 * labelAlpha[0])));
        });
        fadeInLabel.start();

        Timer transitionTimer = new Timer(900, e -> {
            loadingSpinner.stopAnimation();
            Timer fadeOut = new Timer(50, null);
            final float[] alpha = {1f};
            fadeOut.addActionListener(e1 -> {
                alpha[0] -= 0.1f;
                if (alpha[0] <= 0f) {
                    alpha[0] = 0f;
                    fadeOut.stop();
                    cardLayout.show(cardPanel, "main");
                    texto1.animateFadeIn();
                    texto2.animateFadeIn();
                    concatButton.animateFadeIn();
                    salirButton.animateFadeIn();
                    Timer fadeInMain = new Timer(50, null);
                    final float[] mainAlpha = {0f};
                    fadeInMain.addActionListener(e2 -> {
                        mainAlpha[0] += 0.1f;
                        if (mainAlpha[0] >= 1f) {
                            mainAlpha[0] = 1f;
                            fadeInMain.stop();
                        }
                        mainPanel.setBackground(new Color(255, 255, 255, (int)(255 * mainAlpha[0])));
                        mainPanel.repaint();
                    });
                    fadeInMain.start();
                }
                loadingPanel.setBackground(new Color(255, 255, 255, (int)(255 * alpha[0])));
                loadingPanel.repaint();
            });
            fadeOut.start();
        });
        transitionTimer.setRepeats(false);
        transitionTimer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaConcatenar().setVisible(true);
        });
    }
}
