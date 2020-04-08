package Objects.Buildings;

import Fields.GameField;
import Landscape.Map1;
import Objects.UnitTypes.Unit;
import Objects.Units.*;
import Players.Player;
import Resources.ResLoader;
import org.newdawn.slick.*;

import java.util.ArrayList;

public class Base {
    private GameField gf;
    private float xPos, yPos, flagX;
    int flag;
    protected Image img1;
    protected Image imgF;
    private Image imgA;
    public float timer = 0;
    private String type;
    float hp, maxHp;
    public boolean isCurrent = false, destroy = false;
    int await, thisPrice;
    protected int view = 400;
    int stone, iron;
    int investTime = 500;
    private float[] chars1 = {1, 2000, 450};
    private float[] chars2 = {0.8f, 2000, 550};
    private float[] chars3 = {1.4f, 1000, 450};
    public int level = 1;
    public ArrayList<Unit> units;
    public float[] percentages;
    private int[] vision;
    public boolean anShot = true;
    int amount = 0;
    int typeInvest = -1;

    public void setTypeInvest(int typeInvest) {
        this.typeInvest = typeInvest;
    }

    public int getTypeInvest() {
        return typeInvest;
    }

    public void setVision(int[] vision) {
        this.vision = vision;
    }

    public void setGf(GameField gf) {
        this.gf = gf;
    }

    public static int getPrice(String type) {
        int price = 0;
        switch (type) {
            case "Tank1":
                price = 40;
                break;
            case "Tank2":
                price = 100;
                break;
            case "Tank3":
                price = 60;
                break;
            case "Tank4":
                price = 100;
                break;
            case "Tank5":
                price = 60;
                break;
            case "Worker":
                price = 20;
                break;
        }
        return price;
    }

    public void addToQueue(String type) {
        Unit unit = null;
        switch (type) {
            case "Worker":
                unit = new Worker1(xPos, yPos, flag);
                break;
            case "Tank1":
                unit = new Tank1(xPos, yPos, flag);
                break;
            case "Tank2":
                unit = new Tank2(xPos, yPos, flag);
                break;
            case "Tank3":
                unit = new Tank3(xPos, yPos, flag);
                break;
            case "Tank4":
                unit = new Tank4(xPos, yPos, flag);
                break;
            case "Tank5":
                unit = new Tank5(xPos, yPos, flag);
                break;
        }
        if (unit != null) {
            unit.setUpgrade(gf.getPlayer(flag).getLevel(unit.getType()));
            units.add(unit);
        }
    }

    public void removeFromQueue(String type) {
        for (int i = 0; i < units.size(); i++) {
            Unit unit = units.get(i);
            if (unit.getType().equals(type)) {
                units.remove(i);
                break;
            }
        }
    }

    public void setFlagX(float flagX) {
        this.flagX = flagX;
    }

    public float getFlagX() {
        return flagX;
    }

    public Base(float xPos, float yPos, int flag, String type) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.flag = flag;
        this.type = type;
        flagX = this.xPos;
        units = new ArrayList<>();
        switch (type) {
            case "Centre":
                img1 = ResLoader.getBase1();
                maxHp = 25;
                await = 500;
                thisPrice = 60;
                amount = 1;
                stone = 30;
                iron = 30;
                break;
            case "Academy":
                img1 = ResLoader.getImage("Base7");
                maxHp = 30;
                thisPrice = 80;
                amount = 1;
                stone = 80;
                iron = 100;
                break;
            case "Battery":
                img1 = ResLoader.getImage("Base2");
                maxHp = 5;
                thisPrice = 30;
                stone = 10;
                iron = 10;
                break;
            case "Barracks":
                img1 = ResLoader.getBase4();
                maxHp = 15;
                await = 2000;
                thisPrice = 50;
                amount = 4;
                stone = 45;
                iron = 50;
                break;
            case "Energy":
                img1 = ResLoader.getBase3();
                maxHp = 10;
                await = 250;
                thisPrice = 50;
                amount = 0;
                stone = 25;
                iron = 35;
                break;
            case "Storage":
                img1 = ResLoader.getBase5();
                maxHp = 5;
                thisPrice = 40;
                stone = 20;
                iron = 10;
                break;
            case "Turret":
                img1 = ResLoader.getImage("Base8");
                maxHp = 15;
                thisPrice = 100;
                stone = 30;
                iron = 40;
                await = 1000;
                break;
            case "ResourceS":
                img1 = ResLoader.getImage("Base9");
                maxHp = 5;
                thisPrice = 50;
                stone = 10;
                iron = 10;
                break;
            case "ResourceI":
                img1 = ResLoader.getImage("Base9");
                maxHp = 5;
                thisPrice = 50;
                stone = 10;
                iron = 10;
                break;
            case "Port":
                img1 = ResLoader.getImage("Base10");
                maxHp = 20;
                thisPrice = 100;
                stone = 50;
                iron = 50;
                break;
        }
        percentages = new float[amount];
        for (int i = 0; i < percentages.length; i++) {
            percentages[i] = 0;
        }
        imgF = gf.getBuoy(flag);
        imgA = ResLoader.getImage("f1");
    }

    private int timer1 = 0;

    public int getQueue() {
        return units.size();
    }

    void drawFlag() {
        imgA.draw(flagX - imgA.getWidth() / 2, Map1.getLevel() - imgA.getHeight());
    }

    void drawF() {
        imgF.draw(xPos - imgF.getWidth() / 2, yPos - img1.getHeight() / 1.2f - imgF.getHeight());
    }

    public void draw(GameContainer gmc) {
        Graphics graphics = gmc.getGraphics();
        drawF();
        if (hp <= 0) {
            timer1++;
            anShot = Rocket.drawAnim(timer1 / 10 + 1, "Ex2", xPos, yPos - img1.getHeight() / 2);
        } else
            img1.draw(xPos - img1.getWidth() / 2, yPos - img1.getHeight());
        if (hp < maxHp)
            img1.setAlpha(0.7f);
        else {
            img1.setAlpha(1);
            if (isCurrent)
                if (type.equals("Centre") || type.equals("Barracks") || type.equals("Port")) {
                    drawFlag();
                }
        }
        if (isCurrent) {
            graphics.setColor(Color.red);
            graphics.drawOval(xPos - 5, yPos - img1.getHeight() * 1.5f, 10, 10);
            graphics.setColor(Color.white);
        }
    }

    public int countQueue(String type) {
        int count = 0;
        for (Unit unit : units) {
            if (unit.getType().equals(type))
                count++;
        }
        return count;
    }

    public boolean isPressedCircle(float x, float y) {
        boolean result = false;
        float distX, distY;
        distX = xPos - x;
        distY = yPos - y;
        float radius = (img1.getWidth() + img1.getHeight()) / 4;
        if (distY < Math.sqrt(radius * radius - distX * distX))
            result = true;
        return result;
    }

    public boolean isDestroy() {
        return destroy;
    }

    public boolean isPressed(float x, float y) {
        boolean result = false;
        float distX, distY;
        distX = xPos - x;
        distY = yPos - y;
        if (Math.abs(distX) < img1.getWidth() / 2 && Math.abs(distY) < img1.getHeight())
            result = true;
        return result;
    }

    public void addResource(int count, int flag) {
        if (type.equals("ResourceI"))
            GameField.getPlayer(flag).iron += count;
        if (type.equals("ResourceS"))
            GameField.getPlayer(flag).stone += count;
    }

    private void setPercentages() {
        switch (type) {
            case "Barracks":
                String[] els = units.get(0).getType().split("");
                int index = Integer.parseInt(els[els.length - 1]) - 1;
                percentages[index] = timer / units.get(0).spawnTime;
                break;
            case "Centre":
                percentages[0] = timer / units.get(0).spawnTime;
                break;
        }
    }

    void checkFlag(Input input, boolean touched) {
        if (input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && !touched) {
            flagX = input.getMouseX() - GameField.cameraX;
        }
    }

    public void behave(GameContainer gmc, ArrayList<Unit> units1, boolean touchedInter) {
        if (isCurrent)
            checkFlag(gmc.getInput(), touchedInter);
        switch (type) {
            case "Turret":
                timer += 10;
                if (timer > await) {
                    Unit unit = gf.getNearestUnit((int) xPos, flag, vision);
                    if (unit == null)
                        break;
                    if (Math.abs(xPos - unit.getxPos()) <= view && unit.getyPos() >= Map1.getLevel()) {
                        GameField.rockets.add(new Rocket("Turret", xPos, yPos - getSizeY(), flag,
                                (int) (unit.getxPos() - xPos)));
                        timer = 0;
                    }
                }
                break;
            case "Energy":
                timer += 10;
                if (timer > await) {
                    Player player = GameField.getPlayer(flag);
                    if (player != null)
                        player.money += 1;
                    timer = 0;
                }
                break;
            case "Academy":
                if (timer < investTime && typeInvest != -1)
                    timer++;
                percentages[0] = timer / investTime;
                if (timer >= investTime) {
                    switch (typeInvest) {
                        case 1:
                            typeInvest = -1;
                            timer = 0;
                            break;
                        case 2:
                            typeInvest = -1;
                            timer = 0;
                            break;
                        case 3:
                            typeInvest = -1;
                            timer = 0;
                            gf.getPlayer(0).setLevel("Tank2", "+");
                            break;
                        case 4:
                            typeInvest = -1;
                            timer = 0;
                            gf.getPlayer(0).setLevel("Tank3", "+");
                            break;
                    }
                }
                break;
            default:
                if (units.size() > 0) {
                    if (timer < units.get(0).spawnTime)
                        timer += 10;
                    else timer = units.get(0).spawnTime;
                    for (int i = 0; i < percentages.length; i++) {
                        percentages[i] = 0;
                    }
                    setPercentages();
                    if (timer >= units.get(0).spawnTime && GameField.players.get(0).limUnits
                            > units.size()) {
                        Unit unit = units.get(0);
                        unit.setAimX(flagX);
                        units1.add(unit);
                        units.remove(unit);
                        timer = 0;
                    }
                }
                break;
        }
    }


    public void upgradeChars1() {

    }

    public void upgradeChars2() {

    }

    public void upgradeChars3() {
        chars3[0] *= 1.2f;
        chars3[1] /= 1.2f;
        chars3[2] *= 1.2f;
    }

    public float getHp() {
        return hp;
    }

    public float getMaxHp() {
        return maxHp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
    }

    public int getThisPrice() {
        return thisPrice;
    }

    public boolean isAvailable(int stone1, int iron1, int money1) {
        return stone1 >= stone && iron1 >= iron && money1 >= thisPrice;
    }

    public int getFlag() {
        return flag;
    }

    public float getSizeX() {
        return img1.getWidth();
    }

    public float getSizeY() {
        return img1.getHeight();
    }

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public String getType() {
        return type;
    }

    public int getStone() {
        return stone;
    }

    public int getIron() {
        return iron;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getView() {
        return view;
    }
}
