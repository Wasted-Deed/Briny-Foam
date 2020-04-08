package Objects.Units;

import Resources.ResLoader;
import org.newdawn.slick.GameContainer;

public class Tank4 extends Soldier {
    float frame = 1;

    public Tank4(float xPos, float yPos, int flag) {
        super(xPos, yPos, flag);
        reward = 10;
        speed = 1;
        loadSpeed = 25;
        radius = 450;
        type = "Tank4";
        img1 = ResLoader.getImage("D1");
        aimX = xPos;
        xShot = xPos;
        yShot = yPos - img1.getHeight() / 2 - 2;
        hp = 2;
    }

    @Override
    public void draw(GameContainer gmc) {
        super.draw(gmc);
        if (flipped == -1) img1 = ResLoader.getImage("D" + (int) frame);
        else img1 = ResLoader.getImage("D" + (int) frame).getFlippedCopy(true, false);
        img1.draw(super.xPos - img1.getWidth() / 2, super.yPos - img1.getHeight());
        if (frame < 8.9) {
            frame += 0.1f;
        } else frame = 1;
        if (Math.abs(aimX - xPos) <= img1.getWidth() / 4)
            frame = 1;
    }

}
