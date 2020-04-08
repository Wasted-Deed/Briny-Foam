package Objects.Units;

import Fields.GameField;
import Landscape.Map1;
import Objects.Buildings.Base;
import Objects.UnitTypes.Unit;
import Resources.ResLoader;
import org.newdawn.slick.Image;

import java.util.ArrayList;

public class Rocket {
    public String type;
    public float xPos, yPos, originX;
    float speed, distance = 0, maxDistance;
    float damage;
    Image img1;
    String anim;
    public int flag;
    long timer = -1;
    public boolean shot = false, isExp = false;
    int flipped;
    int frame = 0;
    float speedY = -1;
    private boolean anShot = true;

    public Rocket(String type, float xPos, float yPos, int flag, int flipped) {
        this.type = type;
        this.xPos = xPos;
        this.yPos = yPos;
        this.originX = xPos;
        this.flag = flag;
        this.flipped = flipped;
        switch (type) {
            case "Usual":
                speed = 3;
                damage = 1;
                maxDistance = 550;
                img1 = ResLoader.getbA1();
                anim = "Ex1";
                break;
            case "Armorer":
                speed = 4;
                damage = 1.5f;
                maxDistance = 700;
                img1 = ResLoader.getbB1();
                anim = "Ex1";
                break;
            case "Bomb":
                speed = 2;
                damage = 2;
                maxDistance = 400;
                img1 = ResLoader.getbC1();
                anim = "Ex3";
                break;
            case "Turret":
                speed = 3;
                damage = 2;
                maxDistance = 628;
                img1 = ResLoader.getbC1();
                anim = "Ex3";
                break;
            default:
                speed = 0;
                damage = 0;
                img1 = ResLoader.getbA1();
        }
    }

    public static boolean drawAnim(int num, String type, float x, float y) {
        boolean res = false;
        switch (type) {
            case "Ex1":
                if (num >= 1 && num <= 4) {
                    Image img1 = ResLoader.getImage("Ex1" + num);
                    img1.draw(x - img1.getWidth() / 2, y - img1.getHeight() / 2);
                    res = true;
                }
                break;
            case "Ex2":
                if (num >= 1 && num <= 10) {
                    Image img1 = ResLoader.getImage("Ex2" + num);
                    img1.draw(x - img1.getWidth() / 2, y - img1.getHeight() / 2);
                    res = true;
                }
                break;
            case "Ex3":
                if (num >= 1 && num <= 6) {
                    Image img1 = ResLoader.getImage("Ex3" + num);
                    img1.draw(x - img1.getWidth() / 2, y - img1.getHeight() / 2);
                    res = true;
                }
                break;
        }
        return res;
    }

    public void draw() {
        if (timer == -1)
            timer = System.currentTimeMillis();
        if (shot)
            anShot = drawAnim(frame / 10 + 1, anim, xPos, yPos);
        else
            img1.draw(xPos - img1.getWidth() / 2, yPos - img1.getHeight() / 2);
    }

    private void checkCol(ArrayList<Unit> units) {
        for (Unit unit : units) {
            if (unit.getFlag() != flag) {
                if (Math.abs(unit.getxPos() - xPos) <= unit.getSize() / 4 && !type.equals("Explosion") &&
                        Math.abs(unit.getyPos() - yPos) <= unit.getHeight() / 2) {
                    shot = true;
                    unit.setHp(unit.getHp() - damage);
                    unit.setKillerFlag(flag);
                    break;
                }
            }
        }
    }

    private void checkBaseCol() {
        ArrayList<Base> bases = GameField.bases;
        for (Base base : bases) {
            if(base.getFlag() != flag){
                if(base.isPressed(xPos, yPos)){
                    shot = true;
                    base.setHp(base.getHp() - damage);
                    break;
                }
            }
        }
    }

    public void behave(ArrayList<Unit> units) {
        if (shot) {
            frame++;
            if (!anShot)
                isExp = true;
        } else {
            switch (type) {
                case "Bomb":
                    yPos += speed;
                    distance += speed;
                    checkBaseCol();
                    break;
                case "Turret":
                    float l = (float) (2 * maxDistance / Math.PI);
                    float a = speed * speed / l;
                    float angle = (float) (Math.asin(Math.abs(flipped) * a / (speed * speed)) / 2);
                    if (speedY == -1)
                        speedY = (float) (speed * Math.sin(angle));
                    if (flipped > 0)
                        xPos += speed * Math.cos(angle);
                    else xPos -= speed * Math.cos(angle);
                    yPos -= speedY;
                    speedY -= a;
                    distance += speed;
                    break;
                default:
                    xPos += speed * flipped;
                    distance += speed;
            }
            checkCol(units);
            if (distance >= maxDistance)
                shot = true;
            if (type.equals("Bomb") && yPos >= Map1.getLevel()) {
                shot = true;
            }
            if (yPos >= Map1.getLevel())
                shot = true;
        }
    }

    public boolean isShot() {
        return shot;
    }
}
