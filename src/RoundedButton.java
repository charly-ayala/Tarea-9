import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedButton extends JButton {
    private Color backgroundColor;
    private Color hoverColor;
    private int radius = 20;
    private float scale = 1.0f;
    private Timer scaleTimer;

    public RoundedButton(String text, Color bgColor, Color hoverColor) {
        super(text);
        this.backgroundColor = bgColor;
        this.hoverColor = hoverColor;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startScaleAnimation(1.1f);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startScaleAnimation(1.0f);
            }
        });
    }

    private void startScaleAnimation(float targetScale) {
        if (scaleTimer != null && scaleTimer.isRunning()) {
            scaleTimer.stop();
        }
        scaleTimer = new Timer(20, null);
        final float[] currentScale = {scale};
        scaleTimer.addActionListener(e -> {
            currentScale[0] += (targetScale - currentScale[0]) * 0.2f;
            if (Math.abs(currentScale[0] - targetScale) < 0.01f) {
                currentScale[0] = targetScale;
                scaleTimer.stop();
            }
            scale = currentScale[0];
            repaint();
        });
        scaleTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int scaledWidth = (int)(width * scale);
        int scaledHeight = (int)(height * scale);
        int x = (width - scaledWidth) / 2;
        int y = (height - scaledHeight) / 2;

        if (getModel().isRollover()) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(backgroundColor);
        }

        g2.fill(new RoundRectangle2D.Double(x, y, scaledWidth - 1, scaledHeight - 1, radius, radius));
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Sin borde
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
            setForeground(new Color(255, 255, 255, (int)(255 * alpha[0])));
            setBackground(new Color(255, 87, 34, (int)(255 * alpha[0])));
            repaint();
        });
        timer.start();
    }
}
