package main;

import javax.swing.*;
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

    Thread gameThread ;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth , screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }

    public void startGameThread() {

        gameThread = new Thread(this) ;
        gameThread.start();
    }
    @Override
    public void run() {
        while (gameThread != null) {
            System.out.println("kfvjfbvkdfvb");
        }

    }
}
