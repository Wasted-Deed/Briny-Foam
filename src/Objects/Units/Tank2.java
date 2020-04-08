package Objects.Units;

import Resources.ResLoader;
import org.newdawn.slick.GameContainer;

public class Tank2 extends Soldier {

    public Tank2(float xPos, float yPos, int flag) {
        super(xPos, yPos, flag);
        reward = 15;
        speed = 1;
        loadSpeed = 200;
        radius = 550;
        type = "Tank2";
        img1 = ResLoader.getTank2();
        aimX = xPos;
        xShot = xPos + flipped * 60;
        yShot = yPos - img1.getHeight() / 2 - 3;
        hp = 4;
    }

    @Override
    public void draw(GameContainer gmc) {
        super.draw(gmc);
        if (flipped == 1) img1 = ResLoader.getImage("Tank2" + upgrade);
        else img1 = ResLoader.getImage("Tank2" + upgrade).getFlippedCopy(true, false);
        img1.draw(super.xPos - img1.getWidth() / 2, super.yPos - img1.getHeight());
    }

}
