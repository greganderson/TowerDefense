/**
 *
 */
package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * A path test panel is a GUI panel that displays a tower
 * defense path on the screen, and animates a small object
 * moving along the path.
 *
 * @author Peter Jensen and <<Your name here>>
 * @version March 21, 2017  (Update this)
 */
public class TowerDefense extends JPanel implements ActionListener, Runnable
{
    // This avoids an obnoxious warning, but it is totally unused by us.
    //   It would only be relevant if we were using object serialization.

    private static final long serialVersionUID = -2375037100177994040L;

    // Fields (object variables)

    private BufferedImage backdrop;        // the background image (of a garden)
    private Path          gardenPath;      // because my path is in a garden.  :)
    private double        circleLocation;  // the circle's position on the path, from 0% to 100%
    private static TowerDefense application;
    private Timer         animationTimer;

    // Methods

    /**
     * The application entry point.
     *
     * @param args unused
     */
    public static void main (String[] args)
    {
        // Main runs in the 'main' execution thread, and the GUI
        //   needs to be built by the GUI execution thread.
        //   Ask the GUI thread to run our 'run' method (at some
        //   later time).

        application = new TowerDefense();
        SwingUtilities.invokeLater(application);

        // Done.  Let the main thread of execution finish.  All the
        //   remaining work will be done by the GUI thread.
    }

    /**
     * Builds the GUI for this application.  This method must
     * only be called/executed by the GUI thread.
     */
    public void run ()
    {
        ResourceLoader loader = ResourceLoader.getLoader();
        application.backdrop = loader.getImage("resources/path_2.jpg");

        application.gardenPath = loader.getPath("resources/path_2.txt");

        // Assume the circle has traveled 0% along the path.

        application.circleLocation = 0;

        // Set the size of this panel to match the size of the image.

        Dimension panelSize = new Dimension(application.backdrop.getWidth(), application.backdrop.getHeight());

        this.setMinimumSize(panelSize);
        this.setPreferredSize(panelSize);
        this.setMaximumSize(panelSize);

        // Create a timer (for animation), have it call our actionPerformed
        //   method 60 times a second.

        animationTimer = new Timer (16, this);
        animationTimer.start();

        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        f.setContentPane(application);

        f.pack();
        f.setLocationRelativeTo(null);  // Centers window
        f.setVisible(true);
    }


    /**
     * Draws the image, path, and the animating ball.
     *
     * (The background is not cleared, it is assumed the backdrop
     * fills the panel.)
     *
     * @param g the graphics object for drawing on this panel
     */
    public void paint (Graphics g)
    {
        // Draw the background.

        g.drawImage(application.backdrop,  0, 0, null);

        // Have the path object draw itself.

        application.gardenPath.drawPath(g);

        // Draw the circle, centered on the pont.  (Must get it's location first.)

        Point circleScreenPoint = gardenPath.getPathPosition(application.circleLocation);

        g.setColor(Color.BLUE);
        g.fillOval(circleScreenPoint.x-10, circleScreenPoint.y-10, 20, 20);
    }

    /**
     * The actionPerformed method is called (from the GUI event loop)
     * whenever an action event occurs that this object is lisening to.
     *
     * For our test panel, we assume that the Timer has expired, so
     * we advance our small sphere along the path.
     *
     * @param e the event object
     */
    public void actionPerformed (ActionEvent e)
    {
        // Advance the circle 0.1% (one thousandth the distance)
        //   along the path.

        application.circleLocation += 0.001;

        application.circleLocation %= 1;
        // Redraw the screen.

        repaint ();
    }
}