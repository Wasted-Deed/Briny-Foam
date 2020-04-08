package Objects.Units;

import Resources.ResLoader;
import org.newdawn.slick.GameContainer;

public class Tank1 extends Soldier {

    public Tank1(float xPos, float yPos, int flag) {
        super(xPos, yPos, flag);
        reward = 5;
        speed = 1;
        loadSpeed = 200;
        radius = 450;
        type = "Tank1";
        img1 = ResLoader.getTank1();
        xShot = xPos;
        yShot = yPos - img1.getHeight() / 2 - 2;
        hp = 3;
    }

    @Override
    public void draw(GameContainer gmc) {
        super.draw(gmc);
        if (flipped == 1) img1 = ResLoader.getTank1();
        else img1 = ResLoader.getTank1().getFlippedCopy(true, false);
        img1.draw(super.xPos - img1.getWidth() / 2, super.yPos - img1.getHeight());
    }
}
