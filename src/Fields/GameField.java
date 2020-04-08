package Fields;

import Interface.Button;
import Landscape.Map1;
import Objects.Buildings.Base;
import Objects.UnitTypes.Bubble;
import Objects.UnitTypes.Fish;
import Objects.UnitTypes.Unit;
import Objects.Units.Rocket;
import Objects.Units.Worker1;
import Players.Formation;
import Players.Player;
import Players.WaveGenerator;
import Resources.Iron;
import Resources.ResLoader;
import Resources.Resource;
import Resources.Stone;
import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.Arrays;

public class GameField {
    public static ArrayList<Rocket> rockets;
    private ArrayList<Formation> formations;
    private ArrayList<WaveGenerator> waves;
    public static ArrayList<Base> bases;
    private static ArrayList<Bubble> bubbles;
    public ArrayList<Unit> units;
    private static ArrayList<Resource> resources;
    private static ArrayList<Button> buttons;
    private static ArrayList<Fish> fish;
    public static ArrayList<Player> players;
    private Interface inter;
    private GameContainer gmc;
    private Formation chosen;
    Base building;
    private int vision[];
    private String regime = "Survival", menLine = "";
    String typeMen = "";
    public static float cameraX;
    static float cameraY;
    static float step;
    public static float width;
    static float height;
    private float xMouse, yMouse;
    private float chX, chY, chX1, chY1;
    int countButtons;
    private static int sizeX;
    private int thisFlag;
    private int sendUnits;
    private int level;
    private int winPoints;
    private int maxPoints;

    private enum Touch {
        Click, Choose;
    }

    public int[] getVision() {
        return vision;
    }

    public Unit getNearestUnit(int x, int flag, int[] vision) {
        int dist = sizeX;
        Unit found = null;
        for (Unit unit : units) {
            boolean isVisible = vision[(int) (unit.getxPos())] == 1;
            if (Math.abs(unit.getxPos() - x) < dist && unit.getFlag() != flag && isVisible) {
                dist = (int) Math.abs(unit.getxPos() - x);
                found = unit;
            }
        }
        return found;
    }

    private void firstLevelPlayers() {
        players.add(new Player(-1, this, sizeX - 100));
        for (int i = 1; i < 5; i++) {
            units.add(new Worker1(sizeX - i * 10, Map1.getLevel(), -1));
        }
        players.get(1).money = 1050;
        players.get(1).stone = 100;
        players.get(1).iron = 100;
        resources.add(new Stone(300,
                Map1.getLevel() - ResLoader.getImage("Stone").getHeight()));
        resources.add(new Stone(sizeX - 300,
                Map1.getLevel() - ResLoader.getImage("Stone").getHeight()));
        resources.add(new Iron(600,
                Map1.getLevel() - ResLoader.getImage("Iron").getHeight()));
        resources.add(new Iron(sizeX - 600,
                Map1.getLevel() - ResLoader.getImage("Iron").getHeight()));

    }

    private void secondLevelPlayers() {
        players.add(new Player(-1, this, 100));
        players.add(new Player(-2, this, sizeX - 100));
        for (int i = 0; i < 4; i++) {
            units.add(new Worker1(100 + i * 10, Map1.getLevel(), -1));
            units.add(new Worker1(sizeX - 100 - i * 10, Map1.getLevel(), -2));
        }
        resources.add(new Stone(300,
                Map1.getLevel() - ResLoader.getImage("Stone").getHeight()));
        resources.add(new Stone(sizeX / 2 - 300,
                Map1.getLevel() - ResLoader.getImage("Stone").getHeight()));
        resources.add(new Iron(600,
                Map1.getLevel() - ResLoader.getImage("Iron").getHeight()));
        resources.add(new Iron(sizeX / 2 + 600,
                Map1.getLevel() - ResLoader.getImage("Iron").getHeight()));
        resources.add(new Stone(sizeX - 300,
                Map1.getLevel() - ResLoader.getImage("Stone").getHeight()));
        resources.add(new Iron(sizeX - 600,
                Map1.getLevel() - ResLoader.getImage("Iron").getHeight()));
        players.get(1).money = 1050;
        players.get(1).stone = 100;
        players.get(1).iron = 100;
        players.get(2).money = 1050;
        players.get(2).stone = 100;
        players.get(2).iron = 100;
    }

    private void thirdLevelPlayers() {
        players.add(new Player(-1, this, 100));
        players.add(new Player(-2, this, sizeX - 100));
        for (int i = 0; i < 8; i++) {
            units.add(new Worker1(100 + i * 10, Map1.getLevel(), -1));
            units.add(new Worker1(sizeX - 100 - i * 10, Map1.getLevel(), -2));
        }
        resources.add(new Stone(300,
                Map1.getLevel() - ResLoader.getImage("Stone").getHeight()));
        resources.add(new Stone(sizeX / 2 - 300,
                Map1.getLevel() - ResLoader.getImage("Stone").getHeight()));
        resources.add(new Iron(600,
                Map1.getLevel() - ResLoader.getImage("Iron").getHeight()));
        resources.add(new Iron(sizeX / 2 + 600,
                Map1.getLevel() - ResLoader.getImage("Iron").getHeight()));
        resources.add(new Stone(sizeX - 300,
                Map1.getLevel() - ResLoader.getImage("Stone").getHeight()));
        resources.add(new Iron(sizeX - 600,
                Map1.getLevel() - ResLoader.getImage("Iron").getHeight()));
        players.get(1).money = 1050;
        players.get(1).stone = 100;
        players.get(1).iron = 100;
        players.get(2).money = 1050;
        players.get(2).stone = 100;
        players.get(2).iron = 100;
    }

    private void firstWavePlayers() {
        resources.add(new Stone(sizeX / 2 - 300,
                Map1.getLevel() - ResLoader.getImage("Stone").getHeight()));
        resources.add(new Iron(sizeX / 2 + 300,
                Map1.getLevel() - ResLoader.getImage("Iron").getHeight()));
    }

    private void createPlayers(int regime) {
        switch (regime) {
            case 1:
                firstLevelPlayers();
                break;
            case 2:
                secondLevelPlayers();
                break;
            case 3:
                thirdLevelPlayers();
                break;
            case 4:
                firstWavePlayers();
        }
    }

    private void firstLevel() {
        int n = 3;
        int x0 = 500;
        for (int i = 0; i < n; i++) {
            units.add(new Worker1(x0 - step * n + step * i * 2, Map1.getLevel(), 0));
        }
        players.get(0).money = 10000;
        players.get(0).stone = 155;
        players.get(0).iron = 155;
        createPlayers(1);
        cameraX = -x0 + width / 2;
        cameraY = 0;
        winPoints = 50;
        maxPoints = 100;
    }

    private void secondLevel() {
        int n = 5;
        int x0 = sizeX / 2;
        for (int i = 0; i < n; i++) {
            units.add(new Worker1(x0 - step * n + step * i * 2, Map1.getLevel(), 0));
        }
        players.get(0).money = 10000;
        players.get(0).stone = 155;
        players.get(0).iron = 155;
        createPlayers(2);
        cameraX = -x0 + width / 2;
        cameraY = 0;
        winPoints = 33;
        maxPoints = 100;
    }

    private void thirdLevel() {
        int n = 7;
        int x0 = sizeX / 2;
        for (int i = 0; i < n; i++) {
            units.add(new Worker1(x0 - step * n + step * i * 2, Map1.getLevel(), 0));
        }
        players.get(0).money = 10000;
        players.get(0).stone = 155;
        players.get(0).iron = 155;
        createPlayers(3);
        cameraX = -x0 + width / 2;
        cameraY = 0;
        winPoints = 33;
        maxPoints = 100;
    }

    private void firstWave() {
        int n = 5;
        int x0 = sizeX / 2;
        for (int i = 0; i < n; i++) {
            units.add(new Worker1(x0 - step * n + step * i * 2, Map1.getLevel(), 0));
            cameraX = -x0 + width / 2;
            cameraY = 0;
            winPoints = 0;
            maxPoints = 100;
        }
        createPlayers(4);
        players.get(0).money = 10000;
        players.get(0).stone = 155;
        players.get(0).iron = 155;
        waves.add(new WaveGenerator(units, 1, sizeX));
    }

    private void setRegime(int[] params) {
        thisFlag = 0;
        switch (level) {
            case -2:
                break;
            case -1:
                createLevel(params);
                break;
            case 1:
                firstLevel();
                break;
            case 2:
                secondLevel();
                break;
            case 3:
                thirdLevel();
                break;
            case 4:
                firstWave();
                break;
        }
    }

    public ArrayList<Unit> getUnits(int flag) {
        ArrayList<Unit> uns = new ArrayList<>();
        for (Unit unit : units) {
            if (unit.getFlag() == flag)
                uns.add(unit);
        }
        return uns;
    }

    public Base getBuilding() {
        return building;
    }

    public static ArrayList<Base> getBases(int flag) {
        ArrayList<Base> bs = new ArrayList<>();
        for (Base base : bases) {
            if (base.getFlag() == flag)
                bs.add(base);
        }
        return bs;
    }

    public static Player getPlayer(int flag) {
        for (Player player : players) {
            if (flag == player.flag)
                return player;
        }
        return null;
    }

    public static Image getBuoy(int flag) {
        Image img;
        for (int i = 0; i < players.size(); i++) {
            img = ResLoader.getImage("bu" + i);
            if (flag == players.get(i).flag)
                return img;
        }
        return ResLoader.getImage("bu0");
    }

    public Base pickNearestRes(String type, float xPos, int flag) {
        float dist = sizeX;
        Base base1 = null;
        for (Base base : bases) {
            if (Math.abs(xPos - base.getXPos()) < dist && base.getType().equals(type)
                    && base.getFlag() == flag) {
                dist = Math.abs(xPos - base.getXPos());
                base1 = base;
            }
        }
        return base1;
    }

    public float getNearestRes(Enum type, float xPos, int flag) {
        float dist = sizeX;
        Resource res1 = null;
        for (Resource resource : resources) {
            if (Math.abs(xPos - resource.getXPos()) < dist && resource.getType() == type
                    && resource.getFlag() == flag) {
                dist = Math.abs(xPos - resource.getXPos());
                res1 = resource;
            }
        }
        if (res1 == null)
            return 0;
        else
            return res1.xPos;
    }

    public static Base getRes(float aimX, int flag) {
        for (Base base : bases) {
            if (base.getXPos() == aimX && base.getType().startsWith("Resource")
                    && base.getFlag() == flag)
                return base;
        }
        return null;
    }

    public void createLevel(int[] params) {
        sizeX = params[0];
        int size = params[0];
        int count = params[1];
        maxPoints = 100;
        winPoints = maxPoints / count;
        int s = size / count;
        int x1 = (int) (Math.random() * count);
        for (int i = 0; i < count; i++) {
            int x0 = i * s + s / 2;
            if (i != x1) {
                players.add(new Player(-1 - i, this, x0));
                for (int j = 0; j < 3; j++) {
                    units.add(new Worker1(x0 - step * 3 + step * j * 2, Map1.getLevel(), -1 - i));
                }
            } else {
                for (int j = 0; j < 3; j++) {
                    units.add(new Worker1(x0 - step * 3 + step * j * 2, Map1.getLevel(), 0));
                }
                cameraX = -x0 + width / 2;
            }
            resources.add(new Iron(x0 - 200,
                    Map1.getLevel() - ResLoader.getImage("Iron").getHeight()));
            resources.add(new Stone(x0 + 200,
                    Map1.getLevel() - ResLoader.getImage("Stone").getHeight()));
            players.get(i).money = params[2];
            players.get(i).stone = params[3];
            players.get(i).iron = params[4];
        }
    }

    private void initLists() {
        formations = new ArrayList<>();
        waves = new ArrayList<>();
        players = new ArrayList<>();
        bubbles = new ArrayList<>();
        units = new ArrayList<>();
        rockets = new ArrayList<>();
        buttons = new ArrayList<>();
        bases = new ArrayList<>();
        resources = new ArrayList<>();
        fish = new ArrayList<>();
    }

    private void initParams(GameContainer gmc, int[] params) {
        sizeX = params[0];
        vision = new int[sizeX];
        width = gmc.getWidth();
        height = gmc.getHeight();
        step = ResLoader.getScale() * 12;
        initLists();
        Map1.init(gmc);
        players.add(new Player(0, this, 100));
        setRegime(params);
        makeZero();
        sendUnits = -1;
    }

    public void setWinPoints(int winPoints) {
        this.winPoints = winPoints;
    }

    public int getWinPoints() {
        return winPoints;
    }

    private void fillLists(GameContainer gmc) {
        for (int i = 0; i < 2; i++) {
            fish.add(new Fish((float) Math.random() * sizeX,
                    (float) Math.random() * gmc.getHeight(), "Fish1"));
        }
    }

    void init(GameContainer gmc, int level, int[] params) throws SlickException {
        this.level = level;
        initParams(gmc, params);
        fillLists(gmc);
        inter = new Interface(gmc.getGraphics(), this);
        inter.setMiniMap(vision.length / 10, 20);
        this.gmc = gmc;
    }

    private void makeZero() {
        chX = -1;
        chY = -1;
        chX1 = -1;
        chY1 = -1;
    }

    private void setChosen(float x, float y) {
        if (chX == -1 && chY == -1) {
            chX = x;
            chY = y;
        }
        chX1 = x;
        chY1 = y;
    }

    private Base isBasePressed(int mX, int mY) {
        for (Base base : bases) {
            if (base.getFlag() == thisFlag && base.isPressed(mX, mY))
                return base;
        }
        return null;
    }

    private void updateUnits(float cameraX, int cond, float xMouse, float yMouse) {
        inter.clearChosen();
        inter.clearCount();
        String chosenType = "";
        Unit chosenUnit = null;
        Base picked = null;
        int aimX = -1;
        if (cond == 2) {
            aimX = (int) (xMouse);
            picked = isBasePressed((int) (xMouse), (int) yMouse);
            if (picked != null) {
                if (picked.getHp() >= picked.getMaxHp() && !picked.getType().startsWith("Resource"))
                    picked = null;
            }
        }
        for (int i = 0; i < units.size(); i++) {
            Unit unit = units.get(i);
            if (unit.getHp() <= 0) {
                if (unit.getFlag() == thisFlag)
                    winPoints--;
                else if (unit.getKillerFlag() == thisFlag) winPoints++;
                units.remove(i);
            } else {
                String type = unit.getType();
                if (unit.isChosen()) {
                    chosenUnit = unit;
                    chosenType = type;
                    if (!typeMen.equals("Placement") && aimX != -1) {
                        unit.setAimX(aimX);
                        System.out.println(aimX);
                        unit.setCondition("Moving");
                    }
                    if (type.equals("Worker")) {
                        if (sendUnits != -1) {
                            unit.setAimX(sendUnits);
                            unit.setCondition("Working");
                            unit.chosen = false;
                        } else if (picked != null) {
                            if (picked.getHp() < picked.getMaxHp()) {
                                unit.setAimX(picked.getXPos());
                                unit.setCondition("Working");
                            } else if (picked.getType().startsWith("Resource")) {
                                unit.setAimX(picked.getXPos());
                                unit.setCondition("Extracting");
                            }
                            aimX = -1;
                            unit.chosen = false;
                            picked = null;
                        } else {
                            inter.addChosen(type);
                            inter.setCount(type, inter.getCount(type) + 1);
                        }
                    } else {
                        inter.addChosen(type);
                        inter.setCount(type, inter.getCount(type) + 1);
                    }
                }
                unit.behave(units);
            }
        }
        if (typeMen.equals("Placement")) {
            return;
        }
        typeMen = "";
        menLine = "";
        if (inter.getChosen().size() > 1) {
            typeMen = "Multiple";
            menLine = "";
        } else if (inter.getChosen().size() == 1) {
            setMenLine(chosenType, chosenUnit);
        }
        sendUnits = -1;
    }

    private boolean checkFrame(float x, float y) {
        if (x > chX && x < chX1 && y > chY && y < chY1 && chX <= chX1 && chY <= chY1)
            return true;
        if (chX > chX1 && chY > chY1 && x > chX1 && x < chX && y > chY1 && y < chY)
            return true;
        else return false;
    }

    private void setMenLine(String type, Unit unit) {
        int count = inter.getCount(type);
        if (type.equals("Worker"))
            typeMen = "Build";
        else if (type.startsWith("Tank"))
            typeMen = "Attack";
        if (count > 1)
            menLine = type + ": " + count;
        else
            menLine = type + ": " + +Math.round(unit.getHp()) + " hp";
    }

    void setSeparated(String type) {
        for (Unit unit : units) {
            if (unit.getFlag() != thisFlag || !unit.getType().equals(type))
                unit.chosen = false;
        }
    }

    void setBasesBack() {
        for (Base base : bases) {
            if (base.isCurrent)
                base.isCurrent = false;
        }
    }

    private void setPicked(Touch touch) {
        if (!inter.touchedInterface(xMouse, yMouse)) {
            if (!typeMen.equals("Placement")) {
                typeMen = "";
                menLine = "";
            }
        }
        boolean unitPicked = false;
        for (Unit unit : units) {
            if (!inter.touchedInterface(xMouse, yMouse) && !typeMen.equals("Placement"))
                unit.chosen = false;
            if (checkFrame(unit.getxPos(), unit.getyPos()) && unit.getFlag() == thisFlag) {
                unit.chosen = true;
                unitPicked = true;
            }
        }
        for (Base base : bases) {
            if (!inter.touchedInterface(xMouse, yMouse))
                base.isCurrent = false;
            if (!inter.touchedInterface(xMouse, yMouse) && base.getFlag() == thisFlag
                    && !typeMen.equals("Placement")) {
                if (touch.equals(Touch.Choose)) {
                    if (checkFrame(base.getXPos(), base.getYPos()) && !unitPicked) {
                        base.isCurrent = true;
                    }
                } else if (base.isPressed(xMouse, yMouse)) {
                    base.isCurrent = true;
                }
            }
        }
    }

    private boolean checkAvailable() {
        boolean result = true;
        if (vision[normalizeVision((int) building.getXPos())] == 0)
            return false;
        for (Base base : bases) {
            if (Math.abs(building.getXPos() - base.getXPos())
                    < (building.getSizeX() + base.getSizeX()) / 2) {
                return false;
            } else if (building.getType().startsWith("Resource"))
                result = false;
        }
        for (Resource res : resources) {
            if (Math.abs(building.getXPos() - res.getXPos())
                    < (building.getSizeX() + res.getSizeX()) / 2) {
                if (building.getType().startsWith("Resource")) {
                    result = true;
                    String line = "";
                    if (res.getType() == Resource.Type.Stone)
                        line = "ResourceS";
                    if (res.getType() == Resource.Type.Iron)
                        line = "ResourceI";
                    building = new Base(res.xPos, Map1.getLevel(), thisFlag, line);
                } else result = false;
            }
        }
        return result;
    }

    private void checkBuilding(int cond) {
        if (building != null && typeMen.equals("Placement")) {
            building.setXPos(xMouse);
            Player player = players.get(0);
            if (cond == 1
                    && building.isAvailable(player.stone, player.iron, player.money)
                    && checkAvailable()
                    && !inter.touchedInterface(xMouse, yMouse)) {
                building.setHp(1);
                building.setFlagX(gmc.getInput().getMouseX() - cameraX);
                sendUnits = (int) building.getXPos();
                bases.add(building);
                player.money -= building.getThisPrice();
                player.stone -= building.getStone();
                player.iron -= building.getIron();
                if (building.getType().equals("Turret")) {
                    building.setGf(this);
                    building.setVision(vision);
                }
                building = null;
                typeMen = "";
            }
            if (cond == 2) {
                building = null;
                typeMen = "";
            }
        } else if (!typeMen.equals("Placement")) {
            building = null;
        }
    }

    private Touch classifyTouch() {
        if (Math.abs(chX - chX1) < 3 && Math.abs(chY - chY1) < 3)
            return Touch.Click;
        else return Touch.Choose;
    }

    private void moveCamera(Input input) {
        if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D))
            cameraX -= 10;
        if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A))
            cameraX += 10;
        /*if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S))
            cameraY -= 10;
        if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W))
            cameraY += 10;*/
    }

    private void updateRockets() {
        for (int i = 0; i < rockets.size(); i++) {
            Rocket rocket = rockets.get(i);
            rocket.behave(units);
            if (rocket.isShot() && rocket.isExp) {
                rockets.remove(i);
            }
        }
    }

    private void updateWaves() {
        for (WaveGenerator wave : waves) {
            wave.behave();
        }
    }

    void update(GameContainer gmc) {
        Input input = gmc.getInput();
        xMouse = input.getMouseX() - cameraX;
        yMouse = input.getMouseY();
        int cond = 0;
        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
            cond = 10;
        if (input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON))
            cond = 20;
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
            cond = 1;
        if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON))
            cond = 2;
        if (cond == 10 && !typeMen.equals("Placement") && !inter.touchedInterface(xMouse, yMouse))
            setChosen(xMouse, yMouse);
        else {
            if (chX != -1)
                setPicked(classifyTouch());
            makeZero();
        }
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            gmc.exit();
        }
        if (chosen != null)
            chosen.update();
        updateRockets();
        for (Bubble bubble : bubbles) {
            bubble.behave();
        }
        for (Fish fish : fish) {
            fish.behave(gmc);
        }
        inter.setValues(cameraX, bases, typeMen, countButtons);
        updateWaves();
        updateUnits(cameraX, cond, xMouse, yMouse);
        updateBases(gmc);
        checkBuilding(cond);
        updateInter(cond);
        updatePlayers();
        moveCamera(input);
        setCameraPos();
        if (winPoints > 99) {
            MainWindow.state = "Menu";
            winPoints = 50;
        }
    }

    private void setCameraPos() {
        if (-cameraX > sizeX - width) {
            cameraX = -sizeX + width;
        }
        if (-cameraX < 0) {
            cameraX = 0;
        }
        inter.setCameraX(cameraX);
    }

    private void updateInter(int cond) {
        inter.updateMen(typeMen, cond, thisFlag);
        inter.setMenuCond(typeMen);
        inter.setM(xMouse, yMouse);
    }

    private void setView(Player player) {
        Arrays.fill(player.visiblePixels, 0);
        ArrayList<Unit> thisUnits = getUnits(0);
        ArrayList<Base> thisBases = getBases(0);
        for (Unit unit : thisUnits) {
            player.fillVision(unit, null);
        }
        for (Base base : thisBases) {
            player.fillVision(null, base);
        }
        vision = player.visiblePixels;
    }

    private void updatePlayers() {
        for (Player player : players) {
            player.setLimUnits(bases);
            if (player.flag < 0)
                player.behave();
            if (player.flag == 0) {
                setView(player);
            }
        }
    }

    public static void addStone(int stone1) {
        players.get(0).stone += stone1;
    }

    public static void addIron(int iron1) {
        players.get(0).iron += iron1;
    }

    private void drawAll() {
        for (Bubble bubble : bubbles) {
            bubble.draw();
        }
        for (Fish fish : fish) {
            int x = (int) fish.xPos;
            x = normalizeVision(x);
            if (vision[x] == 1)
                fish.draw();
        }
        for (Resource resource : resources) {
            int x = (int) resource.getXPos();
            x = normalizeVision(x);
            if (vision[x] == 1)
                resource.draw();
        }
        for (Base base : bases) {
            int x = (int) base.getXPos();
            x = normalizeVision(x);
            if (vision[x] == 1)
                base.draw(gmc);
        }
        for (Unit unit : units) {
            int x = (int) unit.getxPos();
            x = normalizeVision(x);
            if (vision[x] == 1)
                unit.draw(gmc);
        }
        for (Rocket rocket : rockets) {
            int x = (int) rocket.xPos;
            x = normalizeVision(x);
            if (vision[x] == 1)
                rocket.draw();
        }
        for (Button button : buttons) {
            button.draw(gmc);
        }
    }

    private int normalizeVision(int x) {
        if (x < 0)
            return 0;
        if (x >= vision.length)
            return vision.length - 1;
        return x;
    }

    private void drawVision(Graphics graphics) {
        for (int i = 0; i < vision.length; i++) {
            if (i >= -cameraX && i <= width - cameraX) {
                if (vision[i] == 0) {
                    Color color = new Color(0, 0, 0, 200);
                    graphics.setColor(color);
                    graphics.drawLine(i, 0, i, height);
                    graphics.setColor(Color.white);
                }
            }
        }
    }

    void render(GameContainer gmc) {
        Graphics graphics = gmc.getGraphics();
        graphics.translate(cameraX, cameraY);
        Map1.drawMap(gmc);
        drawAll();
        drawBuilds(gmc);
        Map1.drawBorder(gmc);
        drawVision(graphics);
        graphics.drawRect(chX, chY, chX1 - chX, chY1 - chY);
        inter.drawMen(typeMen, menLine);
        drawPlayers(graphics);
        inter.drawMMap(vision, gmc);
        inter.drawBar(winPoints, maxPoints);
    }

    private void drawPlayers(Graphics graphics) {
        graphics.setColor(Color.blue);
        graphics.drawString("  " + players.get(0).money, step - cameraX, step + cameraY);
        graphics.drawString("  " + players.get(0).stone, step - cameraX, step * 2 + cameraY);
        graphics.drawString("  " + players.get(0).iron, step - cameraX, step * 3 + cameraY);
        graphics.setColor(Color.white);
    }

    private void updateBases(GameContainer gmc) {
        for (int i = 0; i < bases.size(); i++) {
            Base base = bases.get(i);
            if (base.isCurrent) {
                if (gmc.getInput().isKeyDown(Input.KEY_DELETE))
                    base.setHp(0);
                if (base.getHp() == base.getMaxHp()) {
                    typeMen = base.getType();
                    menLine = base.getType() + ": " + Math.round(base.getHp()) + " hp";
                }
            }
            if (base.getHp() == base.getMaxHp())
                base.behave(gmc, units, inter.touchedInterface(xMouse, yMouse));
            else if (!base.anShot)
                bases.remove(base);
        }
    }

    private void stopChosen() {
        for (Unit unit : units) {
            if (unit.isChosen()) {
                unit.setAimX(unit.getxPos());
            }
        }
    }

    private Unit pickNearest(float xPos, int flag) {
        Unit un1 = null;
        float distance = sizeX;
        for (Unit unit : units) {
            if (Math.abs(unit.getxPos() - xPos) < distance
                    && unit.getType().equals("Worker") && flag == unit.getFlag()
                    && unit.isChosen()) {
                distance = Math.abs(unit.getxPos() - xPos);
                un1 = unit;
            }
        }
        return un1;
    }

    public static Base pickNearestBase(float xPos, String type, int flag) {
        Base base = null;
        float distance = sizeX;
        String curType = "";
        for (Base bas : bases) {
            curType = bas.getType();
            if (bas.getFlag() == flag) {
                if (Math.abs(bas.getXPos() - xPos) < distance
                        && bas.getHp() == bas.getMaxHp()) {
                    if ((curType.equals("Storage") || curType.equals("Centre") && type.equals("Storage"))) {
                        distance = Math.abs(bas.getXPos() - xPos);
                        base = bas;
                    } else if (curType.equals(type)) {
                        distance = Math.abs(bas.getXPos() - xPos);
                        base = bas;
                    }
                }
            }
        }
        return base;
    }

    private void drawBuilds(GameContainer gmc) {
        if (building != null) {
            building.draw(gmc);
        }
    }

    public static int getSizeX() {
        return sizeX;
    }

    public static int getMoney() {
        return players.get(0).money;
    }

    public static void setMoney(int money) {
        players.get(0).money = money;
    }

    public static float getCameraX() {
        return cameraX;
    }

    public static float getCameraY() {
        return cameraY;
    }

    public static void setCameraX(float cameraX) {
        GameField.cameraX = cameraX;
    }
}
