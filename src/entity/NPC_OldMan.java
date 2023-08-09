package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class NPC_OldMan extends Entity{

    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction ="down" ;
        speed = 1 ;
        getImage();
    }
    public void getImage(){
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman_up_1.png"))) ;
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman_up_2.png"))) ;
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman_left_1.png"))) ;
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman_left_2.png"))) ;
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman_down_1.png"))) ;
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman_down_2.png"))) ;
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman_right_1.png"))) ;
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman_right_2.png"))) ;

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
