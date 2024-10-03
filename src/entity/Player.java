package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        this.screenX = gamePanel.screenWidth / 2 - gamePanel.tileSize / 2;
        this.screenY = gamePanel.screenHeight / 2  - gamePanel.tileSize / 2;

        hitBox = new Rectangle(8, 16, 32, 32);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 32;
        worldY = gamePanel.tileSize * 32;
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
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
            if (keyHandler.upPressed) {
                direction = "up";
            } else if (keyHandler.downPressed) {
                direction = "down";
            } else if (keyHandler.leftPressed) {
                direction = "left";
            } else if (keyHandler.rightPressed) {
                direction = "right";
            }

            // Check for collision
            colliding = false;
            gamePanel.collisionChecker.checkTileCollision(this);
            if (colliding == false) {
                switch(direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

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
        g2d.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }

}
