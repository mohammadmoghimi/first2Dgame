package entity;

import main.GamePanel;
import main.keyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gp ;
    keyHandler keyH ;
    public final int screenX ;
    public final int screenY ;
    private Color playerColor;
    private Color trailColor; // Add a new field to store the trail color
    public ArrayList<Point> previousPositions;


    public Color getTrailColor() {
        return trailColor;
    }

    private boolean moving; // Boolean to track whether the player is moving


    public Player(GamePanel gp , keyHandler keyH , Color playerColor ){
        this.gp = gp ;
        this.keyH = keyH ;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(8 , 16 , 32 , 32) ;
        this.playerColor = playerColor; // Set the player's color
        setDefaultValues() ;
        getPlayerImage();
        this.trailColor = playerColor; // Set the trail color to the provided color
        this.previousPositions = new ArrayList<>();
        this.moving = false ;
    }
    public void startMoving() {
        moving = true;
    }

    // Method to stop the player's movement
    public void stopMoving() {
        moving = false;
    }

    // Method to check if the player is currently moving
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
    private void updateTrail() {
        if (isMoving()) {
            // If the player is moving, add their current position to the trail
            addCurrentPositionToTrail();
        } else if (!isMoving() && !previousPositions.isEmpty()) {
            // If the player is not moving and the trail is not empty, clear the trail
            previousPositions.clear();
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

    public  void playerUpdate(){
        if (keyH.upPressed){
            direction = "up" ;
        }

        else if (keyH.downPressed){
            direction = "down" ;

        }
        else if (keyH.leftPressed){
            direction = "left" ;
        }

        else if(keyH.rightPressed){
            direction = "right" ;
        }

        collisionOn = false ;
        gp.cChecker.checkTile(this);

        if (!collisionOn){
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }
        spriteCounter ++ ;
        if (spriteCounter > 10 ){
            if (spriteNum == 1 ){
                spriteNum =2 ;
            }
            else if (spriteNum == 2){
                spriteNum  = 1 ;
            }
            spriteCounter = 0 ;
        }
        updateTrail();

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
}