package Objects.Units;

import Fields.GameField;
import Landscape.Map1;
import Objects.Buildings.Base;
import Objects.UnitTypes.Unit;
import Resources.ResLoader;
import org.newdawn.slick.GameContainer;

import java.util.ArrayList;

public class Worker1 extends Unit {
    long timer = -1;
    int wait = 1000, inventory = 0;
    float resX, resY;
    Base bas = null;
    int speedHarvest = 3;

    public Worker1(float xPos, float yPos, int flag) {
        super(xPos, yPos, flag);
        speed = 1;
        hp = 3;
        type = "Worker";
        aimX = xPos;
        img1 = ResLoader.getImage("Drone");
        spawnTime = 5000;
    }

    @Override
    public void draw(GameContainer gmc) {
        super.draw(gmc);
        if (flipped == 1) img1 = ResLoader.getImage("Drone");
        else img1 = ResLoader.getImage("Drone").getFlippedCopy(true, false);
        img1.draw(xPos - img1.getWidth() / 2, yPos - img1.getHeight());
    }

    @Override
    public void behave(ArrayList<Unit> units) {
        super.behave(units);
        if (condition.equals("Moving"))
            timer = -1;
        if (condition.equals("Working")) {
            for (int i = 0; i < GameField.bases.size(); i++) {
                Base base = GameField.bases.get(i);
                if (base.getXPos() == aimX && Math.abs(aimX - xPos) <= img1.getWidth() / 4 &&
                        base.getHp() < base.getMaxHp()) {
                    if (timer == -1) timer = System.currentTimeMillis();
                    if (System.currentTimeMillis() - timer > wait) {
                        base.setHp(base.getHp() + 1);
                        timer = System.currentTimeMillis();
                    }
                }
                if (base.getXPos() == aimX &&
                        (GameField.bases.get(i).getHp() >= GameField.bases.get(i).getMaxHp() ||
                                base.getHp() <= 0)) {
                    condition = "Moving";
                }
            }
        }
        if (!condition.equals("Extracting")) {
            yPos = Map1.getLevel();
        }
        if (condition.equals("Extracting")) {
            if (inventory < maxInv) {
                bas = GameField.getRes(aimX, flag);
                if (bas == null) {
                    condition = "Moving";
                    aimX = xPos;
                    return;
                }
                if (timer == -1 && Math.abs(aimX - xPos) <= img1.getWidth() / 4) {
                    timer = System.currentTimeMillis();
                    if (bas.getType().equals("ResourceI")
                            || bas.getType().equals("ResourceS")) {
                        yPos = -50;
                    }
                } else if (Math.abs(aimX - xPos) > img1.getWidth() / 4) timer = -1;
                if (System.currentTimeMillis() - timer > wait * 3
                        && bas != null
                        && Math.abs(aimX - xPos) <= img1.getWidth() / 4
                        && timer != -1) {
                    resX = aimX;
                    timer = System.currentTimeMillis();
                    inventory += speedHarvest;
                }
            }
            if (inventory >= maxInv && bas != null) {
                yPos = Map1.getLevel();
                Base base = GameField.pickNearestBase(xPos, "Storage", flag);
                if (condition.equals("Extracting")) {
                    if (base == null) {
                        condition = "Moving";
                        aimX = xPos;
                    } else {
                        aimX = base.getXPos();
                    }
                }
                if (condition.equals("Extracting") && Math.abs(aimX - xPos) <= img1.getWidth() / 4) {
                    inventory = 0;
                    bas.addResource(maxInv, flag);
                    aimX = resX;
                    timer = -1;
                }
            }
        }
    }

    public void setAimX(float aimX) {
        this.aimX = aimX;
    }
}
