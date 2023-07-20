package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //screen settings
    final int originalTileSize = 16 ;
    final int scale = 3 ;

    public final int tileSize = originalTileSize * scale ; //using this method our characters look better on the screen
    public final int maxScreenCol = 16 ;
    public final int maxScreenRow = 12 ;
    public final int screenWidth = tileSize * maxScreenCol ;
    public final int screenHeight = tileSize * maxScreenRow ;

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50 ;
    public final int worldWidth = tileSize * maxWorldCol ;
    public final int worldHeight = tileSize * maxWorldRow ;

    keyHandler keyH = new keyHandler() ;
    Thread gameThread ;
    public Player player = new Player(this  ,keyH) ;
    TileManager tileM= new TileManager(this) ;

    int FPS = 60 ;

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

            gamepanelUpdate(); // updates the map based on the position of the player

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
    public void gamepanelUpdate() {
        player.playerUpdate();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g ;

        tileM.gamepanelDraw(g2);

        player.playerDraw(g2);

        g2.dispose();

    }
}