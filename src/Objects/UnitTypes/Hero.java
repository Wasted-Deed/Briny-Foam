package Objects.UnitTypes;

import Fields.GameField;
import Fields.MainWindow;
import Objects.Units.Rocket;
import Resources.ResLoader;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;


public class Hero {
    private static float xPos, yPos;
    private Image img1;
    private Vector2f vector2f;
    long timer = -1;
    private int width = MainWindow.getWidth(), height = MainWindow.getHeight();
    int loadTime;

    public Hero(float xPos, float yPos) {
        Hero.xPos = xPos;
        Hero.yPos = yPos;
        img1 = ResLoader.getHero2();
        loadTime = 1000;
        vector2f = new Vector2f(0, 0);
    }

    public void behave(GameContainer gmc) {
        float scale = ResLoader.getScale();
        if (gmc.getInput().isKeyDown(Input.KEY_W)) {
            vector2f.setY(-1);
        }
        if (gmc.getInput().isKeyDown(Input.KEY_A)) {
            vector2f.setX(-1);
        }
        if (gmc.getInput().isKeyDown(Input.KEY_S)) {
            vector2f.setY(1);
        }
        if (gmc.getInput().isKeyDown(Input.KEY_D)) {
            vector2f.setX(1);
        }
        if (vector2f.getX() != 0 && vector2f.getY() != 0) {
            vector2f.normalise();
        }
        xPos += vector2f.getX() * scale;
        yPos += vector2f.getY() * scale;
        if (xPos > width / 2 && xPos < GameField.getSizeX() - width / 2)
            GameField.cameraX -= vector2f.getX() * scale;
        else if (xPos <= width / 2) GameField.cameraX = 0;
        else GameField.cameraX = -(GameField.getSizeX() - width);
        if (vector2f.getX() < 0) {
            img1 = ResLoader.getHero2().getFlippedCopy(true, false);
        }
        if (vector2f.getX() > 0)
            img1 = ResLoader.getHero2();
        vector2f.setX(0);
        vector2f.setY(0);
        if (gmc.getInput().isKeyDown(Input.KEY_SPACE) && System.currentTimeMillis() - timer > loadTime) {
            GameField.rockets.add(new Rocket("Bomb", xPos, yPos, 0, 0));
            timer = System.currentTimeMillis();
        }
    }

    public void draw(GameContainer gmc) {
        img1.draw(xPos - img1.getWidth() / 2,
                yPos - img1.getHeight() / 2);
    }

    public static float getxPos() {
        return xPos;
    }

    public static float getyPos() {
        return yPos;
    }
}
