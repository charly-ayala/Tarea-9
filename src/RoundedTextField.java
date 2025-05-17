import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedTextField extends JTextField {
    private int radius;
    private String hint = "Escribe aquí...";
    private boolean showingHint = true;
    private Color borderColor = new Color(200, 200, 200);
    private Color focusBorderColor = new Color(100, 150, 255);

    public RoundedTextField(int radius) {
        this.radius = radius;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(8, 5, 12, 5)); // Padding horizontal reducido a 5
        setText(hint);
        setForeground(Color.GRAY);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (showingHint) {
                    showingHint = false;
                    setText("");
                    setForeground(Color.BLACK);
                }
                updateBorderColor(focusBorderColor);
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    showingHint = true;
                    setText(hint);
                    setForeground(Color.GRAY);
                } else {
                    setForeground(Color.BLACK);
                }
                updateBorderColor(new Color(200, 200, 200));
                repaint();
            }

            private void updateBorderColor(Color color) {
                borderColor = color;
                setBorder(BorderFactory.createCompoundBorder(
                        new RoundedBorder(radius, borderColor),
                        BorderFactory.createEmptyBorder(8, 5, 12, 5)
                ));
            }
        });

        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                if (!showingHint) {
                    setForeground(Color.BLACK);
                    repaint();
                }
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                if (!showingHint) {
                    setForeground(Color.BLACK);
                    repaint();
                }
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                if (!showingHint) {
                    setForeground(Color.BLACK);
                    repaint();
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!showingHint) {
                    setForeground(Color.BLACK);
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

        g2.setClip(new RoundRectangle2D.Double(1, 1, getWidth() - 2, getHeight() - 2, radius, radius)); // Clipping reducido
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // El borde se maneja por RoundedBorder
    }

    public void animateFadeIn() {
        Timer timer = new Timer(50, null);
        final float[] alpha = {0f};
        timer.addActionListener(e -> {
            alpha[0] += 0.1f;
            if (alpha[0] >= 1f) {
                alpha[0] = 1f;
                timer.stop();
            }
            setBackground(new Color(255, 255, 255, (int)(230 * alpha[0])));
            repaint();
        });
        timer.start();
    }
}