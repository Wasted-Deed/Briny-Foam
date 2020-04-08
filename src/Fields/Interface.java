package Fields;

import Landscape.Map1;
import Objects.Buildings.Base;
import Objects.UnitTypes.Unit;
import Players.Player;
import Resources.ResLoader;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Interface {
    private GameField gf;
    private Graphics graphics;
    private float step = GameField.step;
    private ArrayList<Base> bases = GameField.bases;
    private ArrayList<String> chosen;
    private Map<String, Integer> count;
    public String menuCond = "";
    private String infoLine = "";
    private float cameraX;
    private float height = GameField.height;
    private float width = GameField.width;
    public float xM = 0, yM = 0;
    private int countButtons;
    private float miniMapW;
    private float miniMapH;
    private int buildPage = 1;
    private int maxPages = 2;
    private int h;
    private int leftMen;
    private String l = "";

    public void setM(float x, float y) {
        xM = x;
        yM = y;
    }

    public void setCameraX(float x) {
        cameraX = x;
    }

    public Interface(Graphics graphics, GameField gameField) {
        this.graphics = graphics;
        gf = gameField;
        cameraX = GameField.cameraX;
        chosen = new ArrayList<>();
        count = new HashMap<>();
        h = ResLoader.getImage("Tank1I").getHeight();
        leftMen = (int) (height / 5);
    }

    int getCount(String s) {
        return count.getOrDefault(s, 0);
    }

    void setCount(String s, int c) {
        count.put(s, c);
    }

    void clearCount() {
        count.clear();
    }

    public ArrayList<String> getChosen() {
        return chosen;
    }

    public void addChosen(String l) {
        if (!chosen.contains(l))
            chosen.add(l);
    }

    public void clearChosen() {
        chosen.clear();
    }

    public void setMenuCond(String menuCond) {
        this.menuCond = menuCond;
    }

    private void menLine(Image img, String menLine) {
        graphics.setColor(Color.black);
        if (!infoLine.equals(""))
            menLine = infoLine;
        graphics.drawString(menLine, -cameraX + step / 3, height - img.getHeight() + step / 4);
        graphics.setColor(Color.white);
    }

    void setValues(float cameraX, ArrayList<Base> bases, String typeMen, int countButtons) {
        this.cameraX = cameraX;
        this.bases = bases;
        this.countButtons = countButtons;
    }

    public boolean touchedInterface(float xMouse, float yMouse) {
        boolean result = false;
        float size = ResLoader.getBuild1().getWidth();
        float step = size * 21 / 32;
        if (xMouse + cameraX < size * countButtons + step && yMouse > height - size * 2
                && countButtons > 0)
            result = true;
        if (xMouse + cameraX > width - miniMapW && yMouse < miniMapH)
            result = true;
        if (xMouse + cameraX < h && yMouse > leftMen
                && yMouse < leftMen + chosen.size() * h && chosen.size() > 1)
            result = true;
        return result;
    }

    private boolean selectIdlers() {
        ArrayList<Unit> units = gf.getUnits(0);
        boolean res = false;
        for (Unit unit : units) {
            unit.chosen = false;
            if (unit.getCondition().equals("Moving") && unit.getType().equals("Worker")) {
                unit.chosen = true;
                res = true;
            }
        }
        return res;
    }

    void updateMen(String type, int cond, int thisFlag) {
        ArrayList<Player> players = GameField.players;
        int state = numButton();
        switch (type) {
            case "Multiple":
                int s = state - 50;
                if (s >= 0 && s < chosen.size() && cond == 1) {
                    l = chosen.get(s);
                }
                if (l.length() > 0 && cond == 0) {
                    chosen.clear();
                    count.clear();
                    gf.setSeparated(l);
                    l = "";
                }
                break;
            case "Port":
                if (cond != 1 && cond != 2)
                    break;
                gf.countButtons = 2;
                for (Base base : bases) {
                    if (base.isCurrent && base.getType().equals("Port")) {
                        if (state == 1 && players.get(0).money >= Base.getPrice("Tank5")) {
                            base.addToQueue("Tank5");
                            players.get(0).money -= Base.getPrice("Tank5");
                        }
                    }
                }
                break;
            case "Barracks":
                if (cond != 1 && cond != 2)
                    break;
                gf.countButtons = 4;
                switch (state) {
                    default:
                        for (Base base : bases) {
                            if (base.isCurrent && base.getType().equals("Barracks")) {
                                if (cond == 1 && players.get(0).money >= Base.getPrice("Tank" + state)) {
                                    base.addToQueue("Tank" + state);
                                    players.get(0).money -= Base.getPrice("Tank" + state);
                                    break;
                                }
                                if (cond == 2) {
                                    base.removeFromQueue("Tank" + state);
                                    players.get(0).money += Base.getPrice("Tank" + state);
                                    break;
                                }
                            }
                        }
                }
                break;
            case "Centre":
                if (cond != 1)
                    break;
                gf.countButtons = 2;
                switch (state) {
                    case 1:
                        for (Base base : bases) {
                            if (base.isCurrent && base.getType().equals("Centre")
                                    && players.get(0).money >= Base.getPrice("Worker")) {
                                base.addToQueue("Worker");
                                players.get(0).money -= Base.getPrice("Worker");
                            }
                        }
                        return;
                    case 2:
                        boolean isSelected = selectIdlers();
                        if (isSelected)
                            gf.setBasesBack();
                        return;
                }
                break;
            case "Academy":
                if (cond != 1) {
                    break;
                }
                gf.countButtons = 4;
                if (state >= 1 && state <= 4) {
                    for (Base base : bases) {
                        if (base.isCurrent && base.getType().equals("Academy")
                                && base.getTypeInvest() == -1) {
                            base.setTypeInvest(state);
                        }
                    }
                }
                break;
            case "Build":
                if (cond != 1)
                    break;
                gf.countButtons = 4;
                if (buildPage > maxPages) {
                    gf.countButtons = 0;
                }
                switch (state) {
                    case 1:
                        if (buildPage == 1) {
                            gf.building = new Base(xM, Map1.getLevel(), thisFlag, "Barracks");
                            gf.building.setHp(gf.building.getMaxHp());
                            gf.typeMen = "Placement";
                        }
                        if (buildPage == 2) {
                            gf.building = new Base(xM, Map1.getLevel(), thisFlag, "Port");
                            gf.building.setHp(gf.building.getMaxHp());
                            gf.typeMen = "Placement";
                        }
                        return;
                    case 2:
                        if (buildPage == 1) {
                            gf.building = new Base(xM, Map1.getLevel(), thisFlag, "Energy");
                            gf.building.setHp(gf.building.getMaxHp());
                            gf.typeMen = "Placement";
                        }
                        return;
                    case 3:
                        if (buildPage == 1) {
                            gf.building = new Base(xM, Map1.getLevel(), thisFlag, "Centre");
                            gf.building.setHp(gf.building.getMaxHp());
                            gf.typeMen = "Placement";
                        }
                        return;
                    case 4:
                        if (buildPage == 1) {
                            gf.building = new Base(xM, Map1.getLevel(), thisFlag, "Storage");
                            gf.building.setHp(gf.building.getMaxHp());
                            gf.typeMen = "Placement";
                        }
                        return;
                    case 5:
                        if (buildPage == 1) {
                            gf.building = new Base(xM, Map1.getLevel(), thisFlag, "Battery");
                            gf.building.setHp(gf.building.getMaxHp());
                            gf.typeMen = "Placement";
                        }
                        return;
                    case 6:
                        if (buildPage == 1) {
                            gf.building = new Base(xM, Map1.getLevel(), thisFlag, "Academy");
                            gf.building.setHp(gf.building.getMaxHp());
                            gf.typeMen = "Placement";
                        }
                        return;
                    case 7:
                        if (buildPage == 1) {
                            gf.building = new Base(xM, Map1.getLevel(), thisFlag, "Turret");
                            gf.building.setHp(gf.building.getMaxHp());
                            gf.typeMen = "Placement";
                        }
                        return;
                    case 8:
                        if (buildPage == 1) {
                            gf.building = new Base(xM, Map1.getLevel(), thisFlag, "ResourceS");
                            gf.building.setHp(gf.building.getMaxHp());
                            gf.typeMen = "Placement";
                        }
                        return;
                    case 9:
                        if (buildPage > 1)
                            buildPage--;
                        return;
                    case 10:
                        if (buildPage <= maxPages)
                            buildPage++;
                        return;
                }
                break;
            case "":
            case "Placement":
                gf.countButtons = 0;
                break;
            default:
                gf.countButtons = 2;
                if (cond != 1)
                    break;
                break;
        }
        return;
    }

    private int numButton() {
        int size = ResLoader.getBuild1().getWidth();
        float step = size * 21 / 32;
        if (xM + cameraX < size && yM > height - size) {
            switch (menuCond) {
                case "Build":
                    if (buildPage == 1)
                        infoLine = "Barracks";
                    else infoLine = "Port";
                    break;
                case "Barracks":
                    infoLine = "Tank1";
                    break;
                case "Academy":
                    infoLine = "Efficiency (Iron) + 100%";
                    break;
            }
            return 1;
        }
        if (xM + cameraX < size * 2 && yM > height - size &&
                xM + cameraX > size) {
            switch (menuCond) {
                case "Build":
                    if (buildPage == 1)
                        infoLine = "Energy Station";
                    break;
                case "Barracks":
                    infoLine = "Tank2";
                    break;
                case "Academy":
                    infoLine = "Efficiency (Stone) + 100%";
                    break;
            }
            return 2;
        }
        if (xM + cameraX < size * 3 && yM > height - size &&
                xM + cameraX > size * 2) {
            switch (menuCond) {
                case "Build":
                    if (buildPage == 1)
                        infoLine = "Town Hall";
                    break;
                case "Barracks":
                    infoLine = "Tank3";
                    break;
                case "Academy":
                    //infoLine = "";
                    break;
            }
            return 3;
        }
        if (xM + cameraX < size * 4 && yM > height - size &&
                xM + cameraX > size * 3) {
            switch (menuCond) {
                case "Build":
                    if (buildPage == 1)
                        infoLine = "Storage";
                    break;
                case "Barracks":
                    infoLine = "Tank4";
                    break;
                case "Academy":
                    //infoLine = "";
                    break;
            }
            return 4;
        }
        if (xM + cameraX < size && yM > height - size * 2 && yM < height - size) {
            if (menuCond.equals("Build") && buildPage == 1)
                infoLine = "Living Cells";
            return 5;
        }
        if (xM + cameraX > size && xM + cameraX < size * 2
                && yM > height - size * 2 && yM < height - size) {
            if (menuCond.equals("Build") && buildPage == 1)
                infoLine = "Academy";
            return 6;
        }
        if (xM + cameraX > size * 2 && xM + cameraX < size * 3
                && yM > height - size * 2 && yM < height - size) {
            if (menuCond.equals("Build") && buildPage == 1)
                infoLine = "Turret";
            return 7;
        }
        if (xM + cameraX > size * 3 && xM + cameraX < size * 4
                && yM > height - size * 2 && yM < height - size) {
            if (menuCond.equals("Build") && buildPage == 1)
                infoLine = "Mining Station";
            return 8;
        }
        if (xM + cameraX > size * 4 && xM + cameraX < size * 4 + step
                && yM > height - size * 2 && yM < height - size) {
            if (menuCond.equals("Build"))
                infoLine = "Previous page";
            return 9;
        }
        if (xM + cameraX > size * 4 && xM + cameraX < size * 4 + step
                && yM > height - size && yM < height) {
            if (menuCond.equals("Build"))
                infoLine = "Next page";
            return 10;
        }
        if (xM + cameraX < h && yM > leftMen && yM < leftMen + h * chosen.size()) {
            return (int) (50 + (yM - leftMen) / h);
        }
        if (xM + cameraX > width - miniMapW && yM < miniMapH) {
            return 101;
        }
        infoLine = "";
        return 0;
    }

    void drawMMap(int[] arr, GameContainer gmc) {
        Graphics graphics = gmc.getGraphics();
        graphics.setColor(Color.black);
        Image img = ResLoader.getImage("Frame2");
        float width = gmc.getWidth();
        float sizeX = GameField.getSizeX();
        float starting = width - cameraX - miniMapW;
        img.draw(width - miniMapW - Math.abs(miniMapH - img.getHeight()) - cameraX, 0);
        graphics.fillRect(starting, 0, miniMapW, miniMapH);
        graphics.setColor(Color.white);
        for (int i = 0; i < arr.length; i += 10) {
            if (arr[i] == 1) {
                graphics.fillRect(starting + i / 10, 0, 1, 20);
            }
        }
        graphics.setColor(Color.red);
        float w = miniMapW * width / sizeX;
        graphics.drawRect(starting - miniMapW * cameraX / sizeX, 0, w, 20);
        graphics.setColor(Color.white);
    }

    void setMiniMap(float width, float height) {
        miniMapW = width;
        miniMapH = height;
    }

    void deleteChosen(String l) {
        chosen.remove(l);
        count.remove(l);
    }

    void drawBar(int a, int b) {
        Image img = ResLoader.getImage("Frame1");
        int x0 = (int) (width - img.getWidth() / 2);
        img.draw(x0 - img.getWidth() / 2 - cameraX, 50);
        graphics.setColor(Color.red);
        int y1 = (int) (img.getHeight() / 2 + 50 - step * 0.6f);
        graphics.fillRect(x0 - b - cameraX, y1, b * 2, step * 1.2f);
        graphics.setColor(Color.green);
        graphics.fillRect(x0 - b - cameraX, y1, a * 2, step * 1.2f);
        graphics.setColor(Color.blue);
        graphics.drawString(a + "%", x0 - b + a - 15 - cameraX, y1 + step / 5);
        graphics.drawString((b - a) + "%", x0 + a - 15 - cameraX, y1 + step / 5);
        graphics.setColor(Color.white);
    }

    private void drawStats() {
        Image img1 = ResLoader.getImage("Corner");
        img1.draw(-img1.getWidth() / 2 - cameraX, -img1.getHeight() / 2);
        img1 = ResLoader.getCoin();
        img1.draw(step - cameraX - img1.getWidth() / 2, step);
        img1 = ResLoader.getImage("StoneIcon");
        img1.draw(step - cameraX - img1.getWidth() / 2, step * 2.05f);
        img1 = ResLoader.getImage("IronIcon");
        img1.draw(step - cameraX - img1.getWidth() / 2, step * 3.1f);
    }

    private void drawAcademyIcons(String menLine) {
        Image img = ResLoader.getImage("Panel");
        int i = 0;
        int percentage = 0;
        img.draw(-cameraX, height - img.getHeight());
        menLine(img, menLine);
        img = ResLoader.getImage("Academy1");
        img.draw(-cameraX, height - img.getHeight());
        img = ResLoader.getImage("Academy2");
        img.draw(-cameraX + img.getWidth(), height - img.getHeight());
        img = ResLoader.getImage("Tank2UpI");
        img.draw(-cameraX + img.getWidth() * 2, height - img.getHeight());
        img = ResLoader.getImage("Tank3UpI");
        img.draw(-cameraX + img.getWidth() * 3, height - img.getHeight());
        for (Base base : bases) {
            if (base.getType().equals("Academy") && base.isCurrent) {
                percentage = (int) (base.percentages[0] * 100);
                i = base.getTypeInvest();
                break;
            }
        }
        if (percentage != 0) {
            graphics.drawString(percentage + "%",
                    -cameraX + step + img.getWidth() * (i - 1), height - img.getHeight());
        }
    }

    private void drawAttackMode(String menLine) {
        Image img = ResLoader.getImage("PanelSmall");
        img.draw(-cameraX, height - img.getHeight());
        menLine(img, menLine);
    }

    private void drawMultiple() {
        for (int i = 0; i < chosen.size(); i++) {
            Image img2 = ResLoader.getImage(chosen.get(i) + "I");
            if (img2 == null)
                break;
            img2.draw(-cameraX, leftMen + h * i);
            graphics.drawString(String.valueOf(count.get(chosen.get(i))), 2 - cameraX, leftMen + h * i);
        }
    }

    private void drawBuild(String menLine) {
        Image img = ResLoader.getImage("Panel");
        img.draw(-cameraX, height - img.getHeight());
        menLine(img, menLine);
        if (buildPage == 1) {
            img = ResLoader.getBuild1();
            img.draw(-cameraX, height - img.getHeight());
            img = ResLoader.getBuild2();
            img.draw(-cameraX + img.getWidth(), height - img.getHeight());
            img = ResLoader.getBuild3();
            img.draw(-cameraX + img.getWidth() * 2, height - img.getHeight());
            img = ResLoader.getImage("Build4");
            img.draw(-cameraX + img.getWidth() * 3, height - img.getHeight());
            img = ResLoader.getImage("Build5");
            img.draw(-cameraX, height - img.getHeight() * 2);
            img = ResLoader.getImage("Build6");
            img.draw(-cameraX + img.getWidth(), height - img.getHeight() * 2);
            img = ResLoader.getImage("Build7");
            img.draw(-cameraX + img.getWidth() * 2, height - img.getHeight() * 2);
            img = ResLoader.getImage("Build8");
            img.draw(-cameraX + img.getWidth() * 3, height - img.getHeight() * 2);
        } else {
            img = ResLoader.getImage("Build10");
            img.draw(-cameraX, height - img.getHeight());
        }
    }

    private void drawPort(String menLine) {
        Image img = ResLoader.getImage("PanelSmall");
        img.draw(-cameraX, height - img.getHeight());
        menLine(img, menLine);
    }

    private void drawCentre(String menLine) {
        Image img = ResLoader.getImage("PanelSmall");
        img.draw(-cameraX, height - img.getHeight());
        menLine(img, menLine);
        img = ResLoader.getImage("WorkerI");
        img.draw(-cameraX, height - img.getHeight());
        img = ResLoader.getImage("ChooseIdle");
        img.draw(-cameraX + img.getWidth(), height - img.getHeight());
        float timer = 0, spawnTime = 0;
        int count = 0;
        for (Base base : bases) {
            if (base.isCurrent && base.getType().equals("Centre")) {
                count += base.units.size();
                if (base.timer > timer)
                    timer = base.timer;
                if (base.units.size() > 0)
                    spawnTime = base.units.get(0).spawnTime;
            }
        }
        graphics.drawString(String.valueOf(count), -cameraX + 2, height - img.getHeight());
        if (spawnTime != 0)
            graphics.drawString(Math.round(timer * 100 / spawnTime) + "%",
                    -cameraX + step, height - img.getHeight());
    }

    private void drawBarracks(String menLine) {
        int[] counts = new int[4];
        float[] percentages = new float[4];
        int amount = 4;
        Image img = ResLoader.getImage("Panel");
        img.draw(-cameraX, height - img.getHeight());
        menLine(img, menLine);
        for (int i = 0; i < amount; i++) {
            img = ResLoader.getImage("Tank" + (i + 1) + "I");
            img.draw(-cameraX + img.getWidth() * i, height - img.getHeight());
        }
        for (Base base : bases) {
            if (base.isCurrent && base.getType().equals("Barracks")) {
                for (int i = 0; i < amount; i++) {
                    counts[i] += base.countQueue("Tank" + (i + 1));
                    if (base.percentages[i] > percentages[i])
                        percentages[i] = base.percentages[i];
                }
            }
        }
        for (int i = 0; i < amount; i++) {
            if (counts[i] != 0) {
                graphics.drawString(String.valueOf(counts[i]), -cameraX + img.getWidth() * i + 2,
                        height - img.getHeight());
                if (percentages[i] != 0)
                    graphics.drawString(Math.round(percentages[i] * 100) + "%",
                            -cameraX + step + img.getWidth() * i, height - img.getHeight());
            }
        }
    }

    void drawMen(String type, String menLine) {
        drawStats();
        if (!type.equals("Build"))
            buildPage = 1;
        switch (type) {
            case "Attack":
                drawAttackMode(menLine);
                break;
            case "Multiple":
                drawMultiple();
                break;
            case "Academy":
                drawAcademyIcons(menLine);
                break;
            case "Build":
                if (buildPage == maxPages + 1)
                    return;
                drawBuild(menLine);
                break;
            case "Port":
                drawPort(menLine);
                break;
            case "Centre":
                drawCentre(menLine);
                break;
            case "Barracks":
                drawBarracks(menLine);
                break;
        }
    }
}
