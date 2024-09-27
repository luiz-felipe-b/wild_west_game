package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = 100;
        worldY = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(new FileInputStream("res/player/cowpoke_walk_up_1.png"));
            up2 = ImageIO.read(new FileInputStream("res/player/cowpoke_walk_up_2.png"));
            down1 = ImageIO.read(new FileInputStream("res/player/cowpoke_walk_down_1.png"));
            down2 = ImageIO.read(new FileInputStream("res/player/cowpoke_walk_down_2.png"));
            left1 = ImageIO.read(new FileInputStream("res/player/cowpoke_walk_left_1.png"));
            left2 = ImageIO.read(new FileInputStream("res/player/cowpoke_walk_left_2.png"));
            right1 = ImageIO.read(new FileInputStream("res/player/cowpoke_walk_right_1.png"));
            right2 = ImageIO.read(new FileInputStream("res/player/cowpoke_walk_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyHandler.upPressed) {
            direction = "up";
            worldY -= speed;
        } else if (keyHandler.downPressed) {
            direction = "down";
            worldY += speed;
        } else if (keyHandler.leftPressed) {
            direction = "left";
            worldX -= speed;
        } else if (keyHandler.rightPressed) {
            direction = "right";
            worldX += speed;
        }
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
            spriteCounter++;
        }
        if (spriteCounter > 12) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2d) {
        BufferedImage image = null;
        switch(direction) {
            case "up":
                if(spriteNum == 1) {
                    image = up1;
                }
                if(spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1) {
                    image = down1;
                }
                if(spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1) {
                    image = left1;
                }
                if(spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1) {
                    image = right1;
                }
                if(spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2d.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }

}
