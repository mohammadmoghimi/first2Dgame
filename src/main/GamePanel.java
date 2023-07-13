package main;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //screen settings
    final int originalTileSize = 16 ;
    final int scale = 3 ;

    final int tileSize = originalTileSize * scale ; //using this method our characters look better on the screen
    final int maxScreenCol = 16 ;
    final int maxScreenRow = 12 ;
    final int screenWidth = tileSize * maxScreenCol ;
    final int screenHeight = tileSize * maxScreenRow ;

    keyHandler keyH = new keyHandler() ;
    Thread gameThread ;

    int FPS = 60 ;

    //set player's default position
    int playerX = 100 ;
    int playerY = 100 ;
    int playerspeed = 4 ;
    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth , screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {

        gameThread = new Thread(this) ;
        gameThread.start();
    }
    @Override
    public void run() {

        double drawInterval = 1000000000/FPS ; // indicates how fast do we want to repaint everything
        double nextDrawTime = System.nanoTime() + drawInterval ;

        while (gameThread != null) {

            update(); // updates the player's position

            repaint(); // repaints the map

            try {
                double remainingTime = nextDrawTime -System.nanoTime() ;
                remainingTime = remainingTime/1000000 ; // because sleep method only accepts milliseconds

                if (remainingTime < 0)     // if update and repaint took more time than drawInterval ,
                    remainingTime = 0  ;  // then our thread doesn't need to sleep


                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval ; // 0.0166 seconds later is the next drawTime


            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public void update () {
        if (keyH.upPressed)
            playerY -= playerspeed ;


        else if (keyH.downPressed)
            playerY += playerspeed ;

        else if (keyH.leftPressed)
            playerX -= playerspeed ;

        else if(keyH.rightPressed)
            playerX += playerspeed ;


    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g ;

        g2.setColor(Color.white) ;

        g2.fillRect(playerX ,playerY ,tileSize , tileSize);

        g2.dispose();

    }
}
