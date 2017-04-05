package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * A path test panel is a GUI panel that displays a tower
 * defense path on the screen, and animates a small object
 * moving along the path.
 * 
 * This class won't be part of the final project - we're just
 * using it for testing.
 * 
 * @author Peter Jensen and <<Your name here>>
 * @version March 22, 2017  (Update this)
 */
public class PathTestPanel extends JPanel implements ActionListener
{
    private PathTestApplication listener;
    // This constant avoids an obnoxious warning, but it is totally unused by us.
    //   It would only be relevant if we were using object serialization.
    
    private static final long serialVersionUID = 42L;
    
    // Fields (object variables) 
    
    private BufferedImage backdrop; // the background image
    
        // Students will add a few more fields (object variables) to keep
        //   track of their path object, the circle position (as a percentage),
        //   and possibly a Timer object.
    private Path path;
    private double percentageTraveled;

    // Methods

    public PathTestPanel ()
    {
        try
        {
            // InputStream objects are an alternative to File objects.
            //   I use them to make it easier to locate resources - resources
            //   can be in a directory, .jar, on the web, etc..  
            //
            // In the code below, I figure out
            //   where my class was loaded from, and get the 'resource' from the 
            //   adjoining resource directory.  Java will return an 'InputStream'
            //   that you can use to read or scan through the resource.
            
            // Get the object that loaded this class, because it keeps track
            //   of -where- things are loaded from for us.  (A very advanced
            //   technique, please use this code.)
            
            ClassLoader myLoader = this.getClass().getClassLoader();
            
            // Load the background image
            
            InputStream imageStream = myLoader.getResourceAsStream("resources/path_2.jpg");
            backdrop = javax.imageio.ImageIO.read(imageStream);  // A handy helper method
            
            // Create a scanner that points to our text file (with the path points in it)
            
            // Students may uncomment this code:
             InputStream pointStream = myLoader.getResourceAsStream("resources/path_2.txt");
             Scanner in = new Scanner (pointStream);  // Scan from the text file.
            
            // Build the path object (using the scanner).
            // Students will do this.
            path = new Path(in);

            // Assume the circle has traveled 0% along the path.
            // Students will do this.
            percentageTraveled = 0.0;

            // Set the size of this panel to match the size of the image.
            
            Dimension panelSize = new Dimension(backdrop.getWidth(), backdrop.getHeight());
            
            this.setMinimumSize(panelSize);
            this.setPreferredSize(panelSize);
            this.setMaximumSize(panelSize);

            // Create a timer (for animation), have it call our actionPerformed
            //   method 60 times a second.
            // Students will do this.
            Timer t = new Timer(160, this);
            t.start();

            // Close the scanner.  (A good idea when working with InputStreams.
            // Students may uncomment this code:
            in.close ();
        }
        catch (IOException e)
        {
            // On error, just print a message and exit.  
            //   (You should make sure the files are in the correct place.)
            
            System.err.println ("Could not load one of the files.");
            System.exit(0);  // Bail out.
        }        
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
        
        g.drawImage(backdrop,  0, 0, null);        
        
        // Have the path object draw itself.
        // Students will do this.

        // Test path

        //path.drawPath(g);

        // Test path percentage

        //Point p = path.getPathPosition(0.8);
        //g.setColor(Color.GREEN);
        //g.fillOval(p.x-10, p.y-10, 20, 20);
        
        // Draw the circle, centered on its location.  (Must get it's location first.)
        // Students will do this.
        g.setColor(Color.BLUE);
        Point p = path.getPathPosition(percentageTraveled);
        g.fillOval(p.x, p.y, 10, 10);
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
        //   along the path, and redraw the screen.
        // Students will do this.
        percentageTraveled += 0.001;
        repaint();
    }
}
