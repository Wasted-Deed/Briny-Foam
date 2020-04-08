package Objects.Units;

import Fields.GameField;
import Objects.UnitTypes.Unit;
import Resources.ResLoader;
import org.newdawn.slick.GameContainer;

import java.util.ArrayList;

public class Tank5 extends Soldier {

    public Tank5(float xPos, float yPos, int flag) {
        super(xPos, yPos, flag);
        reward = 10;
        speed = 1.6f;
        loadSpeed = 100;
        radius = 450;
        type = "Tank5";
        img1 = ResLoader.getHero();
        aimX = xPos;
        xShot = xPos;
        yShot = yPos - img1.getHeight() / 2 - 2;
        hp = 6;
    }

    @Override
    public void draw(GameContainer gmc) {
        super.draw(gmc);
        if (flipped == 1) img1 = ResLoader.getHero();
        else img1 = ResLoader.getHero().getFlippedCopy(true, false);
        img1.draw(super.xPos - img1.getWidth() / 2, super.yPos - img1.getHeight());
    }

    @Override
    public void behave(ArrayList<Unit> units) {
        super.behave(units);
        if (yPos > GameField.width / 3)
            yPos -= speed;
    }
}
