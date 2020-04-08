package Landscape;

import Fields.GameField;
import Resources.ResLoader;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

public class Map2 {
    static Image img2;
    static int width, height;
    static double amount;
    public static float level;
    static Image background1;
    static Image background2;
    static Image background3;
    static int upValue;

    public static void init(GameContainer gmc) {
        width = GameField.getSizeX();
        height = gmc.getHeight();
        img2 = ResLoader.getImage("Tile3.png");
        gmc.getGraphics().setBackground(Color.decode("#121341"));
        level = height - img2.getHeight() + 5;
    }

    public static void drawMap(GameContainer gmc) {
        upValue = 0;
        amount = Math.ceil(width / background2.getWidth()) + 1;
        for (int i = 0; i < amount; i++) {
            background2.draw(i * background2.getWidth() - GameField.cameraX / 2,
                    height - background2.getHeight() - upValue);
        }

    }

    public static void drawBorder(GameContainer gmc) {
        amount = Math.ceil(width / img2.getWidth()) + 1;
        for (int i = 0; i < amount; i++) {
            img2.draw(i * img2.getWidth(), height - img2.getHeight());
        }
    }

    public static float getLevel() {
        return level;
    }
}
