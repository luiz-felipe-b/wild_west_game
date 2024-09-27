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
        mapTileNum = new int[gamePanel.maxScreenRow][gamePanel.maxScreenCol];
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

            while(col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
                String line = br.readLine();

                while (col < gamePanel.maxScreenCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[row][col] = num;
                    col++;
                }
                if (col == gamePanel.maxScreenCol) {
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

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {

            int tileNum = mapTileNum[row][col];

            g2d.drawImage(tile[tileNum].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
            x += gamePanel.tileSize;
            col++;
            if (col == gamePanel.maxScreenCol) {
                col = 0;
                row++;
                x = 0;
                y += gamePanel.tileSize;
            }
        }
    }
}
