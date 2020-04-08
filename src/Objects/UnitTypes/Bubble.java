package Objects.UnitTypes;


import Fields.GameField;
import Resources.ResLoader;
import org.newdawn.slick.Image;

public class Bubble {
    float xPos, yPos;
    Image img1 = ResLoader.getBubble1();

    public Bubble(float xPos, float yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void behave() {
        xPos += (int) (Math.random() * 2) - 1;
        yPos -= 1;
    }

    public void draw() {
        img1.draw(xPos - img1.getWidth() / 2, yPos - img1.getHeight() / 2);
    }

    public float getyPos() {
        return yPos;
    }
}
