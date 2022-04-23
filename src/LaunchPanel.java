import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class LaunchPanel extends JPanel {

    ArrayList<MovingDot> dots;
    ArrayList<Obstacle> obstacles;
    Dot launchPoint;
    Point s;


    public LaunchPanel() {
        setPreferredSize(new Dimension(500,500));
        dots = new ArrayList<MovingDot>();
        obstacles = new ArrayList<Obstacle>();
        generateObstacles(3);
        s = new Point(250,250);
        launchPoint =new Dot(s);
        launchPoint.setColor(Color.GREEN);

        addMouseListener(new LaunchPanel.MousePlay());
    }

    @Override
    protected synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);
        int h = this.getHeight();
        int w = this.getWidth();
        Point c = new Point(w/2, h-30);
        launchPoint.setCenter(c);
        launchPoint.paint(g);

        for (MovingDot d: dots) {

            for (Obstacle o : obstacles) {
                if (d.getRegion().intersects(o.getRegion())) {
                    o.hitBy(d);
                }
            }

            d.move();
            d.paint(g);
        }

        for (Obstacle o : obstacles) {
            o.paint(g);
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }

    private void generateDot(Point p){
        MovingDot  d = new MovingDot(launchPoint.getCenter(), p, 1);
        //d = new GravityDotDecorator(d);
        d = new BoundedDotDecorator(d, new Point(getWidth(),getHeight()) );
        dots.add(d);
        Thread t = new Thread(d);
        t.start();
    }

    private void generateObstacles(int difficulty) {
        switch (difficulty) {
            case 1 :
                obstacles.add(new MovingObstacle( new Point(100, 100), new Point(100, 999999),0.25, this));
                obstacles.add(new MovingObstacle(new Point(250, 100), new Point(250, 999999), 0.25, this));
                obstacles.add(new MovingObstacle(new Point(400, 100), new Point(400, 999999), 0.25, this));
                break;

            case 2 :
                obstacles.add(new MovingObstacle(new Point(100, 100), new Point(100, 999999), 0.3, this));
                obstacles.add(new MovingObstacle(new Point(250, 100), new Point(250, 999999), 0.3, this));
                obstacles.add(new MovingObstacle(new Point(400, 100), new Point(400, 999999), 0.3, this));
                obstacles.add(new MovingObstacle(new Point(175, 175), new Point(175, 999999), 0.3, this));
                obstacles.add(new MovingObstacle(new Point(325, 175), new Point(325, 999999), 0.3, this));
                break;

            case 3 :
                obstacles.add(new MovingObstacle(new Point(100, 100), new Point(100, 999999), 0.35, this));
                obstacles.add(new MovingObstacle(new Point(250, 100), new Point(250, 999999), 0.35, this));
                obstacles.add(new MovingObstacle(new Point(400, 100), new Point(400, 999999), 0.35, this));
                obstacles.add(new MovingObstacle(new Point(175, 175), new Point(175, 999999), 0.35, this));
                obstacles.add(new MovingObstacle(new Point(325, 175), new Point(325, 999999), 0.35, this));
                obstacles.add(new MovingObstacle(new Point(100, 250), new Point(100, 999999), 0.35, this));
                obstacles.add(new MovingObstacle(new Point(250, 250), new Point(250, 999999), 0.35, this));
                obstacles.add(new MovingObstacle(new Point(400, 250), new Point(400, 999999), 0.35, this));
                break;
        }
    }

    public void removeObstacle(Obstacle o) {
        obstacles.remove(o);
    }

    private class MousePlay implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            generateDot(e.getPoint());
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}

