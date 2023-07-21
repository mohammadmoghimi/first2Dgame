package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp ;
    public Tile[] tile;
    public int[][] mapTileNum;
    public TileManager(GamePanel gp) {
        this.gp = gp ;

        tile = new Tile[10] ;
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow] ;
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
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];
            boolean isEvenTile = (worldCol + worldRow) % 2 == 0;

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (screenX + gp.tileSize > 0 && screenX < gp.screenWidth &&
                    screenY + gp.tileSize > 0 && screenY < gp.screenHeight) {

                BufferedImage tileImage;
                if (isEvenTile) {
                    tileImage = tile[0].image; // Use the white tile
                } else {
                    tileImage = tile[1].image; // Use the black tile
                }
                if (    worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                        worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                        worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                        worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)
                {
                    g2.drawImage(tileImage , screenX ,screenY , gp.tileSize, gp.tileSize , null);
                }
            }
            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}

