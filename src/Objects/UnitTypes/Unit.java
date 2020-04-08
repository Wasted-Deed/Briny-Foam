package Objects.UnitTypes;

import Objects.Buildings.Base;
import Resources.ResLoader;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

import java.util.ArrayList;

public class Unit {
    protected float xPos, yPos, xShot, yShot, aimX;
    protected Image img1;
    protected Unit target = null;
    protected Base bTarget = null;
    protected float speed, radius;
    protected int flag, maxInv = 12;
    protected long timer = -1;
    protected float loadSpeed;
    protected int flipped;
    protected int view = 600;
    public int reward, level, spawnTime;
    public boolean chosen = false;
    protected String upgrade = "";
    protected String condition = "Moving", amount = "", type;
    private int count = 0, counter = 1, step = 2;
    protected float hp;
    int killerFlag;

    public Unit(float xPos, float yPos, int flag) {
        this.xPos = xPos;
        this.yPos = yPos;
        aimX = xPos;
        this.flag = flag;
        level = 1;
        spawnTime = 2000;
        killerFlag = 0;
    }

    public void setUpgrade(String upgrade) {
        this.upgrade = upgrade;
    }

    private void drawCondition(GameContainer gmc) {
        gmc.getGraphics().drawString(condition, xPos, yPos - img1.getHeight() * 4);
    }

    public void setKillerFlag(int killerFlag) {
        this.killerFlag = killerFlag;
    }

    public int getKillerFlag() {
        return killerFlag;
    }

    public void draw(GameContainer gmc) {
        if (chosen)
            gmc.getGraphics().drawOval(xPos, yPos - img1.getHeight() * 2, 2, 2);
    }

    private void checkAreas(ArrayList<Unit> units) {
        for (Unit unit : units) {
            if (!unit.equals(this) && unit.getFlag() != flag
                    && Math.abs(unit.getxPos() - getxPos()) < radius
                    && Math.abs(unit.getyPos() - getyPos()) < step) {
                count++;
                target = unit;
            }
            if (!unit.equals(this) && unit.getFlag() == flag && Math.abs(unit.getxPos() - getxPos()) < step) {
                counter++;
            }
        }
    }

    public void behave(ArrayList<Unit> units) {
        if (type.equals("Tank5")) {
        } else
            checkAreas(units);
        if (counter > 1)
            amount = counter + "";
        else amount = "";
        counter = 1;
        if (count > 0 && !type.equals("Worker")) {
            condition = "Shooting";
        } else if (!type.equals("Worker")) condition = "Moving";
        count = 0;
        if(!condition.equals("Shooting") || flag >= 0){
            toAim();
        }
        if (aimX >= xPos) flipped = 1;
        else flipped = -1;

    }

    private void toAim() {
        if (Math.abs(aimX - xPos) > img1.getWidth() / 4) {
            xPos += ResLoader.getScale() / 2
                    * (aimX - xPos) * speed / Math.abs(aimX - xPos);
        }
    }

    public int getFlag() {
        return flag;
    }

    public float getxPos() {
        return xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public float getSize() {
        return img1.getWidth();
    }

    public float getHeight() {
        return img1.getHeight();
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getHp() {
        return hp;
    }

    public void setChar(float[] chars) {
        speed = chars[0];
        loadSpeed = chars[1];
        radius = chars[2];
    }

    public int getReward() {
        return reward;
    }

    public void setAimX(float aimX) {
        this.aimX = aimX;
    }

    public String getType() {
        return type;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean isChosen() {
        return chosen;
    }

    public int getView() {
        return view;
    }
}
