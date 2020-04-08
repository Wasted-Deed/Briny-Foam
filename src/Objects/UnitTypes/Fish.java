package Objects.UnitTypes;


import Resources.ResLoader;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

public class Fish {
    public float xPos, yPos, aimX, aimY, dist = 0;
    Image img;
    String type;
    long timer = -1;
    int timeout = 3000;

    public Fish(float xPos, float yPos, String type) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.type = type;
        switch (type) {
            case "Fish1":
                img = ResLoader.getFish1();
                break;

            case "Fish2":
                break;
        }
    }

    public void draw() {
        img.draw(xPos - img.getWidth() / 2, yPos - img.getHeight() / 2);
    }

    public void behave(GameContainer gmc) {
        int width = gmc.getWidth();
        int height = gmc.getHeight();
        if (timer == -1)
            timer = System.currentTimeMillis();
        if (System.currentTimeMillis() - timer > timeout) {
            aimX = (float) (Math.random() * width);
            aimY = (float) (Math.random() * height);
            timer = System.currentTimeMillis();
            dist = 0;
        }
        float diag = (float) Math.sqrt(Math.pow(aimX - xPos, 2) + Math.pow(aimY - yPos, 2));
        float cos = (aimX - xPos) / diag;
        float sin = (aimY - yPos) / diag;
        if (Math.abs(aimX - xPos) > 2 && Math.abs(aimY - yPos) > 2 && dist < 200) {
            xPos += cos;
            yPos += sin;
            dist++;
        }
        if (aimX < xPos)
            img = ResLoader.getFish1();
        else img = ResLoader.getFish1().getFlippedCopy(true, false);
    }
}