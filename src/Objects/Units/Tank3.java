package Objects.Units;

import Resources.ResLoader;
import org.newdawn.slick.GameContainer;

public class Tank3 extends Soldier {

    public Tank3(float xPos, float yPos, int flag) {
        super(xPos, yPos, flag);
        reward = 10;
        speed = 1.4f;
        loadSpeed = 100;
        radius = 450;
        type = "Tank3";
        img1 = ResLoader.getTank3();
        aimX = xPos;
        xShot = xPos;
        yShot = yPos - img1.getHeight() / 2 - 2;
        hp = 2;
    }

    @Override
    public void draw(GameContainer gmc) {
        super.draw(gmc);
        if (flipped == 1) img1 = ResLoader.getImage("Tank3" + upgrade);
        else img1 = ResLoader.getImage("Tank3" + upgrade).getFlippedCopy(true, false);
        img1.draw(super.xPos - img1.getWidth() / 2, super.yPos - img1.getHeight());
    }

}
