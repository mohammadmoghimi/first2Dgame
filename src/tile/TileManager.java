package tile;

import entity.Player;
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
    Player player ;
    public TileManager(GamePanel gp) {
        this.gp = gp ;

        tile = new Tile[10] ;
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow] ;
        getTileImage() ;
    }
    public void getTileImage() {
        tile[0] = new Tile();
        tile[0].image = createColoredTile(Color.gray);
        tile[0].position.setLocation(0, 0); // Set the position of the tile


        tile[1] = new Tile();
        tile[1].image = createColoredTile(Color.WHITE);
        tile[1].position.setLocation(0, 0); // Set the position of the tile

    }

    private BufferedImage createColoredTile(Color color) {
        BufferedImage image = new BufferedImage(gp.tileSize, gp.tileSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(1, 1, gp.tileSize, gp.tileSize);
        g2d.dispose();
        return image;
    }

    public boolean isPartOfHomeBase(int worldCol, int worldRow) {
        // Calculate the center coordinates of the screen
        int centerX = gp.worldWidth / 2;
        int centerY = gp.worldHeight / 2;

        // Calculate the center coordinates of the home base (2 * 2 square)
        int homeBaseCenterX = centerX / gp.tileSize - 1;
        int homeBaseCenterY = centerY / gp.tileSize - 1;

        // Check if the current tile is within the home base (2 * 2 square)
        return Math.abs(worldCol - homeBaseCenterX) <= 1 && Math.abs(worldRow - homeBaseCenterY) <= 1;
    }

    public void setTileColor(int worldCol, int worldRow, Color color) {
        mapTileNum[worldCol][worldRow] = color.hashCode();
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

                // Check if the current tile is part of the player's home base (2 * 2 square)
                boolean isPartOfHomeBase = isPartOfHomeBase(worldCol, worldRow);

                if (isPartOfHomeBase) {
                    tileImage = createColoredTile(gp.player.getPlayerColor());
                } else {
                    // Check if the current tile is in the player's trail
                    boolean isInTrail = gp.player.isTileInTrail(worldCol, worldRow);
                    if (isInTrail) {
                        // Draw the tile using the player's trail color
                        tileImage = createColoredTile(gp.player.getTrailColor());
                    } else {
                        if (isEvenTile) {
                            tileImage = tile[0].image; // Use the gray tile
                        } else {
                            tileImage = tile[1].image; // Use the white tile
                        }
                    }
                }

                if (    worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                        worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                        worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                        worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                    g2.drawImage(tileImage, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
            }
            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }

        // Draw the player's trail after drawing the tiles
        g2.setColor(gp.player.getTrailColor());
        for (int i = 0; i < gp.player.previousPositions.size(); i++) {
            Point prevPos = gp.player.previousPositions.get(i);
            int screenX = prevPos.x - gp.player.worldX + gp.player.screenX;
            int screenY = prevPos.y - gp.player.worldY + gp.player.screenY;
            g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
        }

        // Call player.updateTrail() to clear the trail when stopped
        gp.player.updateTrail();
    }
}