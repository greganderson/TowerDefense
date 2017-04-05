package game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.exit;

/**
 * Created by Greg on 4/5/2017.
 */
public class ResourceLoader {

    private Map<String, BufferedImage> images;
    private Map<String, Path> paths;

    private static ResourceLoader instance = null;

    private ResourceLoader() {
        images = new HashMap<>();
        paths = new HashMap<>();
    }

    public static ResourceLoader getLoader() {
        if (instance == null)
            instance = new ResourceLoader();
        return instance;
    }

    public BufferedImage getImage(String filename) {
        if (images.containsKey(filename))
            return images.get(filename);

        ClassLoader myLoader = this.getClass().getClassLoader();
        InputStream imageStream = myLoader.getResourceAsStream(filename);

        BufferedImage image;
        try {
            image = javax.imageio.ImageIO.read(imageStream);  // A handy helper method
            images.put(filename, image);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Unable to load image: " + filename);
            exit(0);
        }

        return null;
    }

    public Path getPath(String filename) {
        if (paths.containsKey(filename))
            return paths.get(filename);

        ClassLoader myLoader = this.getClass().getClassLoader();
        InputStream pointStream = myLoader.getResourceAsStream(filename);
        Scanner in = new Scanner(pointStream);  // Scan from the text file.

        Path path = new Path(in);
        paths.put(filename, path);
        return path;
    }
}
