import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;

public class LoadingSpinner extends JComponent {
    private double angle = 0;
    private Timer animationTimer;
    private float alpha = 0f;

    public LoadingSpinner() {
        setPreferredSize(new Dimension(50, 50));
        animationTimer = new Timer(50, e -> {
            angle += 10;
            if (angle >= 360) angle -= 360;
            repaint();
        });
        animationTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(255, 87, 34, (int)(255 * alpha)));
        g2.setStroke(new BasicStroke(5));
        g2.draw(new Arc2D.Double(10, 10, 30, 30, angle, 120, Arc2D.OPEN));
        g2.dispose();
    }

    public void animateFadeIn() {
        Timer timer = new Timer(50, null);
        timer.addActionListener(e -> {
            alpha += 0.1f;
            if (alpha >= 1f) {
                alpha = 1f;
                timer.stop();
            }
            repaint();
        });
        timer.start();
    }

    public void stopAnimation() {
        animationTimer.stop();
    }
}
