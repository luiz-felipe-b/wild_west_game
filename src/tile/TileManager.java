package tile;

import main.GamePanel;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gamePanel;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10];
        mapTileNum = new int[gamePanel.maxWorldRow][gamePanel.maxWorldCol];
        // grass
        tile[0] = new Tile();
        // sand
        tile[1] = new Tile();
        // water
        tile[2] = new Tile();
        // rock
        tile[3] = new Tile();
        // log
        tile[4] = new Tile();
        getTileImage();
        loadMap("res/maps/map01.txt");
    }

    public void getTileImage() {
        try {
            tile[0].image = ImageIO.read(new FileInputStream("res/tiles/grass.png"));
            tile[1].image = ImageIO.read(new FileInputStream("res/tiles/sand.png"));
            tile[2].image = ImageIO.read(new FileInputStream("res/tiles/water.png"));
            tile[3].image = ImageIO.read(new FileInputStream("res/tiles/rock.png"));
            tile[4].image = ImageIO.read(new FileInputStream("res/tiles/log.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = new FileInputStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                String line = br.readLine();

                while (col < gamePanel.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    System.out.println(col);
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[row][col] = num;
                    col++;
                }
                if (col == gamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }

            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {

            int tileNum = mapTileNum[worldRow][worldCol];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            if (
                worldX > gamePanel.player.worldX - gamePanel.player.screenX - gamePanel.tileSize && 
                worldX <  gamePanel.player.worldX + gamePanel.player.screenX + gamePanel.tileSize && 
                worldY >  gamePanel.player.worldY - gamePanel.player.screenY - gamePanel.tileSize &&
                worldY <  gamePanel.player.worldY + gamePanel.player.screenY + gamePanel.tileSize) {
                g2d.drawImage(tile[tileNum].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            }

            worldCol++;

            if (worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
