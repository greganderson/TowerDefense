package game;

import java.awt.*;

/**
 * Created by Greg on 4/5/2017.
 */
public class GameState {

    private Path path;
    private double percentageTraveled;

    public GameState(Path path) {
        this.path = path;
    }

    public void update() {
        percentageTraveled += 0.001;
        percentageTraveled %= 1;
    }

    public void draw(Graphics g) {
        Point p = path.getPathPosition(percentageTraveled);
        g.setColor(Color.YELLOW);
        g.fillOval(p.x-10, p.y-10, 20, 20);
    }
}
