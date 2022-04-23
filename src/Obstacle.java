import com.sun.jdi.connect.LaunchingConnector;

import javax.swing.*;
import java.awt.*;

public class Obstacle {

    protected Point center;
    protected Rectangle region;
    protected int size;
    protected int health;
    protected Color color;
    protected LaunchPanel panel;

    public Obstacle(Point center, LaunchPanel panel) {
        this.center = center;
        size = 50;
        health = 5;
        region = new Rectangle(center.x-size/2,center.y-size/2, size, size);
        color = color.RED;
        this.panel = panel;
    }

    public void paint(Graphics g){
        g.setColor(color);
        g.fillRect(region.x,region.y, region.width,region.height);
    }

    public Rectangle getRegion() {
        return region;
    }

    public synchronized void hitBy(MovingDot d) {
        if ((d.getTop() > top()) && (d.getBottom() < bottom())) {
            d.setMotion(-d.getDx(), d.getDy());
            decrementHealth();
            System.out.println("Ouch1");
        } else {
            if ((d.getLeft() > left()) && (d.getRight() < right())) {
                d.setMotion(d.getDx(), -d.getDy());
                decrementHealth();
                System.out.println("Ouch2");
            }
            else{
                d.setMotion(-d.getDx(), d.getDy());
                d.setMotion(d.getDx(), -d.getDy());
                decrementHealth();
                System.out.println("Ouch3");
            }
        }

    }

    private synchronized void decrementHealth() {
        health--;
        if (health < 1) {
            panel.removeObstacle(this);
        }
    }

    public int top(){
        return region.y;
    }
    public int bottom(){
        return region.y+region.height;
    }
    public int left(){
        return region.x;
    }
    public int right(){
        return region.x +region.width;
    }
}
