package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TileManager {
    GamePanel gp ;
    Tile[] tile;
    public TileManager(GamePanel gp){
        this.gp = gp ;

        tile = new Tile[10] ;
        getTileImage() ;
    }
    public void getTileImage() {
        tile[0] = new Tile();
        tile[0].image = createColoredTile(Color.gray);

        tile[1] = new Tile();
        tile[1].image = createColoredTile(Color.WHITE);
    }

    private BufferedImage createColoredTile(Color color) {
        BufferedImage image = new BufferedImage(gp.tileSize, gp.tileSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, gp.tileSize, gp.tileSize);
        g2d.dispose();
        return image;
    }
    public void gamepanelDraw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        boolean isBlack = true; // Flag to alternate between black and white tiles

        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
            // Draw black or white tile based on the flag
            if (isBlack) {
                g2.drawImage(tile[0].image, x, y, gp.tileSize, gp.tileSize, null);
            } else {
                g2.drawImage(tile[1].image, x, y, gp.tileSize, gp.tileSize, null);
            }

            isBlack = !isBlack; // Toggle the flag for the next tile

            col++;
            x += gp.tileSize;

            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
                isBlack = !isBlack; // Start each row with the opposite color of the previous row
            }
        }
    }
}