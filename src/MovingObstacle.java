import java.awt.*;

public class MovingObstacle extends Obstacle implements Runnable {

    private double dx;
    private double dy;
    private double speed;
    private Point destination;

    public MovingObstacle(Point center, Point destination, double speed, LaunchPanel panel) {
        super(center, panel);
        this.destination = destination;
        this.speed = speed;

        double ang = Math.acos((destination.x-center.x)/destination.distance(center));
        double dx = (speed*Math.cos(ang));
        double dy = (speed*Math.sin(ang));
        if (destination.y < center.y){
            dy = -dy;
        }
        setMotion(dx, dy);
    }

    public void setMotion(double x, double y) {
        dx = x;
        dy = y;
    }

    public void move() {
        super.center.x += dx;
        super.center.y += dy;
    }

    @Override
    public void run() {
        while (true) {
            move();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {

            }
        }
    }
}
