package tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean collision = false ;
    public Color color;
    public Point position; // Add the position property
    public Tile() {
        this.image = null;
        this.collision = false;
        this.position = new Point(); // Initialize the position
    }
}