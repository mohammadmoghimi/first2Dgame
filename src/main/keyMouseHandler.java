package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class keyMouseHandler implements KeyListener, MouseListener, MouseMotionListener {

    public boolean upPressed, downPressed, leftPressed , rightPressed ;
    private int mouseX, mouseY;
    GamePanel gp ;

    public keyMouseHandler(GamePanel gp) {
        this.gp = gp; // Initialize the GamePanel instance
        // ... (existing constructor code)
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode() ;

        if (code == KeyEvent.VK_C)
            gp.player.fireWeapon();

        if (code == KeyEvent.VK_W)
            upPressed = true ;

        if (code == KeyEvent.VK_S)
            downPressed = true ;

        if (code == KeyEvent.VK_A)
            leftPressed = true ;

        if (code == KeyEvent.VK_D)
            rightPressed = true ;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode() ;

        if (code == KeyEvent.VK_W)
            upPressed = false ;

        if (code == KeyEvent.VK_S)
            downPressed = false ;

        if (code == KeyEvent.VK_A)
            leftPressed = false ;

        if (code == KeyEvent.VK_D)
            rightPressed = false ;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Handle mouse clicked event if needed
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Handle mouse released event if needed
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Handle mouse entered event if needed
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Handle mouse exited event if needed
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        int prevMouseX = mouseX;
        int prevMouseY = mouseY;

        mouseX = e.getX();
        mouseY = e.getY();

        int deltaX = mouseX - prevMouseX;
        int deltaY = mouseY - prevMouseY;

        if (deltaX < 0) {
            // Dragging towards the left
            leftPressed = true;
            rightPressed = false;
        } else if (deltaX > 0) {
            // Dragging towards the right
            leftPressed = false;
            rightPressed = true;
        } else {
            // No horizontal movement
            leftPressed = false;
            rightPressed = false;
        }

        if (deltaY < 0) {
            // Dragging upwards
            upPressed = true;
            downPressed = false;
        } else if (deltaY > 0) {
            // Dragging downwards
            upPressed = false;
            downPressed = true;
        } else {
            // No vertical movement
            upPressed = false;
            downPressed = false;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Update mouse position
        mouseX = e.getX();
        mouseY = e.getY();
    }

    // Methods to get the current mouse position
    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }
}
