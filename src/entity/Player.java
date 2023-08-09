package entity;

import main.GamePanel;
import main.Weapon;
import main.keyMouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Player extends Entity{
    public GamePanel gp ;
    keyMouseHandler keyH ;
    public final int screenX ;
    public final int screenY ;
    private Color playerColor;
    private Color trailColor; // Add a new field to store the trail color
    private Weapon weapon;

    public ArrayList<Point> previousPositions;


    public Color getTrailColor() {
        return trailColor;
    }

    private boolean moving; // Boolean to track whether the player is moving


    public Player(GamePanel gp , keyMouseHandler keyH , Color playerColor ){
        super(gp);
        this.gp = gp ;
        this.keyH = keyH ;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        weapon = new Weapon(10, 5); // For example, 10 ammo and 5-tile range

        solidArea = new Rectangle(8 , 16 , 32 , 32) ;
        this.playerColor = playerColor; // Set the player's color
        setDefaultValues() ;
        getPlayerImage();
        this.trailColor = Color.CYAN ; // Set the trail color to the provided color
        this.previousPositions = new ArrayList<>();
        this.moving = false ;

    }
    public boolean isMoving() {
        return moving;
    }

    public Color getPlayerColor() {
        return playerColor;
    }
    private void addCurrentPositionToTrail() {
        Point currentPosition = new Point(worldX, worldY);
        previousPositions.add(currentPosition);
    }

    public void updateTrail() {
        if (isMoving()) {
            // If the player is moving, add their current position to the trail
            addCurrentPositionToTrail();
        } else {
            // If the player is not moving, remove the current position from the trail
            Point currentPosition = new Point(worldX, worldY);
            previousPositions.remove(currentPosition);
        }
    }


    public boolean isTileInTrail(int worldCol, int worldRow) {
        Point tilePosition = new Point(worldCol, worldRow);
        return previousPositions.contains(tilePosition);
    }


    public void setDefaultValues(){
        worldX = gp.worldWidth / 2 ;
        worldY = gp.worldHeight / 2 ;
        speed = 3 ;
        direction = "down" ;
    }
    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_1.png"))) ;
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_2.png"))) ;
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_1.png"))) ;
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_2.png"))) ;
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_1.png"))) ;
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_2.png"))) ;
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_1.png"))) ;
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_2.png"))) ;

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playerUpdate() {
        // Get the movement input from both keyboard and mouse
        boolean keyboardUpPressed = keyH.upPressed;
        boolean keyboardDownPressed = keyH.downPressed;
        boolean keyboardLeftPressed = keyH.leftPressed;
        boolean keyboardRightPressed = keyH.rightPressed;

        boolean mouseUpPressed = keyH.upPressed;
        boolean mouseDownPressed = keyH.downPressed;
        boolean mouseLeftPressed = keyH.leftPressed;
        boolean mouseRightPressed = keyH.rightPressed;

        // Set the movement direction based on the input from both keyboard and mouse
        if ((keyboardUpPressed || mouseUpPressed) && !(keyboardDownPressed || mouseDownPressed)) {
            direction = "up";
        } else if ((keyboardDownPressed || mouseDownPressed) && !(keyboardUpPressed || mouseUpPressed)) {
            direction = "down";
        } else if ((keyboardLeftPressed || mouseLeftPressed) && !(keyboardRightPressed || mouseRightPressed)) {
            direction = "left";
        } else if ((keyboardRightPressed || mouseRightPressed) && !(keyboardLeftPressed || mouseLeftPressed)) {
            direction = "right";
        }

        int prevWorldX = worldX;
        int prevWorldY = worldY;

        collisionOn = false;
        gp.cChecker.checkTile(this);

        if (!collisionOn) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        spriteCounter++;
        if (spriteCounter > 10) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if (prevWorldX != worldX || prevWorldY != worldY) {
            addCurrentPositionToTrail();
        }
    }
    public void playerDraw(Graphics2D g2){

        BufferedImage image = null ;
        switch (direction) {
            case "up" -> {
                if (spriteNum == 1)
                    image = up1;
                if (spriteNum == 2)
                    image = up2;
            }
            case "down" -> {
                if (spriteNum == 1)
                    image = down1;
                if (spriteNum == 2)
                    image = down2;
            }
            case "left" -> {
                if (spriteNum == 1)
                    image = left1;
                if (spriteNum == 2)
                    image = left2;
            }
            case "right" -> {
                if (spriteNum == 1)
                    image = right1;
                if (spriteNum == 2)
                    image = right2;
            }
        }
        g2.drawImage(image , screenX , screenY , gp.tileSize , gp.tileSize ,null);

    }
    public void fireWeapon() {
        if (weapon.getAmmoCount() <= 0) {
            System.out.println("you are out of ammo "); // Don't fire if ammo is empty
        }

        // Calculate the center coordinates of the 3x3 square around the player
        int centerX = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
        int centerY = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;

        // Calculate the boundaries of the 3x3 square
        int startX = centerX - 1;
        int startY = centerY - 1;
        int endX = centerX + 1;
        int endY = centerY + 1;

        // Set the color of tiles within the 3x3 square to the player's home base color
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (x >= 0 && x < gp.maxWorldCol && y >= 0 && y < gp.maxWorldRow) {
                    if (!gp.tileM.isPartOfHomeBase(x, y)) {
                        gp.tileM.setTileColor(x, y, getPlayerColor());
                    }
                }
            }
        }

        // Reduce ammo count
        weapon.setAmmoCount(weapon.getAmmoCount() - 1);
    }
}