package game;

import java.awt.*;
import java.util.Scanner;

/**
 * Created by Kylie on 4/5/2017.
 */
public class Path {

    private Point[] path;
    private double pathLength;

    /**
     * This constructor does the following:
     * - It creates a new array (or ArrayList) to hold the path coordinates,
     * and stores it in the path variable.
     * - It reads a number of coordinates, n, from the scanner.
     * - It loops n times, each time scanning a coordinate x,y pair, creating an
     * object to represent the coordinate, and storing the coordinate object in the path.
     *
     * @param s a Scanner set up by the caller to provide a list of coordinates
     */
    public Path(Scanner s) {
        path = new Point[s.nextInt()];
        for (int i = 0; i < path.length; i++)
            path[i] = new Point(s.nextInt(), s.nextInt());
        pathLength = 0.0;
        for (int i = 0; i < path.length - 1; i++)
            pathLength += path[i].distance(path[i + 1]);
        pathLength += path[path.length - 2].distance(path[path.length - 1]);
    }

    /**
     * Returns the total length of the path. Since the path
     * is specified using screen coordinates, the length is
     * in pixel units (by default).
     *
     * @return the length of the path
     */
    public double getPathLength() {
        return pathLength;
    }

    /**
     * Given a percentage between 0% and 100%, this method calculates
     * the location along the path that is exactly this percentage
     * along the path. The location is returned in a Point object
     * (int x and y), and the location is a screen coordinate.
     * <p>
     * If the percentage is less than 0%, the starting position is
     * returned. If the percentage is greater than 100%, the final
     * position is returned.
     * <p>
     * If students don't want to use Point objects, they may
     * write or use another object to represent coordinates.
     * <p>
     * Caution: Students should never directly return a Point object
     * from a path list. It could be changed by the outside caller.
     * Instead, always create and return new point objects as
     * the result from this method.
     *
     * @param percentTraveled a distance along the path
     * @return the screen coordinate of this position along the path
     */
    public Point getPathPosition(double percentTraveled) {
        if (percentTraveled <= 0)
            return new Point(path[0]);
        if (percentTraveled >= 1.0)
            return new Point(path[path.length - 1]);

        double distanceTraveled = getPathLength() * percentTraveled;

        int i = 0;
        double currentDistance = 0.0;
        while (distanceTraveled > currentDistance && i < path.length - 1) {
            currentDistance += path[i].distance(path[i + 1]);
            i++;
        }

        double percentAlongSegment = 1.0 - (currentDistance - distanceTraveled) / (path[i].distance(path[i - 1]));
        double dx = (path[i].x - path[i - 1].x) * percentAlongSegment;
        double dy = (path[i].y - path[i - 1].y) * percentAlongSegment;

        int newX = (int) (path[i - 1].x + dx);
        int newY = (int) (path[i - 1].y + dy);
        return new Point(newX, newY);
    }


    /**
     * Draws the current path to the screen (to wherever the graphics object points)
     * using a highly-visible color.
     *
     * @param g a graphics object
     */
    public void drawPath(Graphics g) {
        g.setColor(Color.BLUE);
        for (int i = 0; i < path.length; i++)
            g.fillOval(path[i].x - 10, path[i].y - 10, 20, 20);
    }
}
