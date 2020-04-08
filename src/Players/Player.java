package Players;

import Fields.GameField;
import Landscape.Map1;
import Objects.Buildings.Base;
import Objects.UnitTypes.Unit;
import Resources.ResLoader;
import Resources.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Player {
    public int flag, xPos;
    public int money = 0;
    public int stone = 0;
    public int iron = 0;
    public int coal = 0;
    public int oil = 0;
    public int limUnits = 0;
    private int progress = 0;
    private boolean shortageRes = false;
    String[] plan;
    public int[] visiblePixels;
    private ArrayList<Base> thisBases;
    private ArrayList<Unit> thisUnits;
    private GameField gf;
    private int flipDev, prX;
    private long timer, timeOut;
    private int sizeForm;
    private ArrayList<Formation> formations;
    private Map<String, String> levels;

    public Player(int flag, GameField gameField, int xPos) {
        this.flag = flag;
        levels = new HashMap<>();
        if (flag < 0) {
            plan = new String[]{"Centre", "ResourceS", "ResourceI", "Energy", "Energy",
                    "Barracks", "Barracks", "Academy", "Storage"};
        }
        visiblePixels = new int[GameField.getSizeX()];
        this.xPos = xPos;
        if (xPos > GameField.getSizeX() / 2)
            flipDev = -1;
        else flipDev = 1;
        gf = gameField;
        prX = xPos;
        thisBases = new ArrayList<>();
        thisUnits = new ArrayList<>();
        thisBases = gf.getBases(flag);
        thisUnits = gf.getUnits(flag);
        formations = new ArrayList<>();
        formations.add(new Formation(6, flag, "Tank1"));
        timeOut = 10000;
    }

    public String getLevel(String key) {
        return levels.getOrDefault(key, "");
    }

    public void setLevel(String key, String level) {
        if (levels.containsKey(key))
            levels.replace(key, level);
        else levels.put(key, level);
    }

    public void setLimUnits(ArrayList<Base> bases) {
        limUnits = 0;
        for (Base base : bases) {
            if (base.getHp() == base.getMaxHp()) {
                if (base.getType().equals("Centre"))
                    limUnits += 5;
                if (base.getType().equals("Barracks"))
                    limUnits += 5;
                if (base.getType().equals("Battery"))
                    limUnits += 5;
            }
        }
    }

    private void buildPlan() {
        if (progress < plan.length) {
            Base base;
            if (plan[progress].startsWith("Resource")) {
                Enum type = Resource.Type.Stone;
                if (plan[progress].endsWith("I"))
                    type = Resource.Type.Iron;
                if (plan[progress].endsWith("S"))
                    type = Resource.Type.Stone;
                float res = gf.getNearestRes(type, xPos, 0);
                base = new Base(res, Map1.getLevel(), flag, plan[progress]);
            } else
                base = new Base(xPos, Map1.getLevel(), flag, plan[progress]);
            if (money >= base.getThisPrice() && stone > base.getStone() && iron >= base.getIron()) {
                shortageRes = false;
                base.setHp(1);
                GameField.bases.add(base);
                money -= base.getThisPrice();
                stone -= base.getStone();
                iron -= base.getIron();
                progress++;
            } else shortageRes = true;
        }
    }

    private Unit pickNearest(float x, int flag) {
        Unit un1 = null;
        float distance = GameField.getSizeX();
        for (Unit unit : thisUnits) {
            if (Math.abs(unit.getxPos() - x) < distance
                    && unit.getType().equals("Worker") && flag == unit.getFlag() &&
                    !unit.getCondition().equals("Working")) {
                distance = Math.abs(unit.getxPos() - x);
                un1 = unit;
            }
        }
        return un1;
    }

    private Base getBase(String type) {
        for (Base base : thisBases) {
            if (base.getType().equals(type) && base.getHp() == base.getMaxHp())
                return base;
        }
        return null;
    }

    private ArrayList<Base> getAllBases(String type) {
        ArrayList<Base> bases = new ArrayList<>();
        for (Base base : thisBases) {
            if (base.getType().equals(type) & base.getHp() == base.getMaxHp())
                bases.add(base);
        }
        return bases;
    }

    private void hireUnits(int amount, String type, String typeBuild) {
        int done = 0;
        int minQueue = 0, min = 0;
        ArrayList<Base> bases = getAllBases(typeBuild);
        if (bases.size() > 0)
            minQueue = bases.get(0).getQueue();
        else return;
        while (done < amount) {
            int i = 0;
            for (Base base : bases) {
                if (base.getQueue() < minQueue) {
                    min = i;
                    minQueue = base.getQueue();
                }
                i++;
            }
            if (money < Base.getPrice(type))
                break;
            Base base = bases.get(min);
            base.addToQueue(type);
            money -= Base.getPrice(type);
            done++;
            min = 0;
            minQueue = bases.get(0).getQueue();
        }
    }

    public void fillVision(Unit unit, Base base) {
        if (unit != null) {
            int left = (int) (unit.getxPos()) - unit.getView();
            if (left < 0)
                left = 0;
            int right = (int) (unit.getxPos()) + unit.getView();
            if (right > visiblePixels.length - 1)
                right = visiblePixels.length - 1;
            Arrays.fill(visiblePixels, left, right, 1);
        } else {
            int left = (int) (base.getXPos()) - base.getView();
            if (left < 0)
                left = 0;
            int right = (int) (base.getXPos()) + base.getView();
            if (right > visiblePixels.length - 1)
                right = visiblePixels.length - 1;
            Arrays.fill(visiblePixels, left, right, 1);
        }
    }

    private void updateForms(ArrayList<Unit> troops) {
        Unit toAttack = gf.getNearestUnit(prX, flag, visiblePixels);
        Formation form = formations.get(0);
        for (int i = 0; i < troops.size(); i++) {
            if (!form.isFilled()) {
                form.addUnit(troops.get(i));
                if (!form.isInFight())
                    form.setAimX(prX);
            } else {
                if (toAttack != null) {
                    form.setAimX((int) toAttack.getxPos());
                } else {
                    if (xPos > gf.getSizeX() / 2)
                        form.setAimX(0);
                    else form.setAimX(gf.getSizeX());
                }
            }
        }
        form.update();
    }

    private void uploadUnits() {
        int countW = 0;
        int countS;
        Arrays.fill(visiblePixels, 0);
        ArrayList<Unit> troops = new ArrayList<>();
        for (Unit unit : thisUnits) {
            fillVision(unit, null);
            if (unit.getType().equals("Worker"))
                countW++;
            if (unit.getType().contains("Tank")) {
                unit.setAimX(xPos);
                troops.add(unit);
            }
        }
        countS = troops.size();
        for (Base base : thisBases) {
            fillVision(null, base);
            if (base.getType().equals("Centre"))
                countW += base.units.size();
            if (base.getType().equals("Barracks"))
                countS += base.units.size();
        }
        updateForms(troops);
        int limTroops = (limUnits / 12) * 6;
        int limWorkers = limUnits / 3;
        if (countS < limTroops) {
            hireUnits(limTroops - countS, "Tank1", "Barracks");
        }
        if (countW < limWorkers) {
            hireUnits(limWorkers - countW, "Worker", "Centre");
        }

    }

    private void sendHarvest(int amount) {
        int count = 0;
        for (Unit unit : thisUnits) {
            if (count > amount)
                break;
            if (unit.getType().equals("Worker")) {
                if (count < amount / 2) {
                    Base bas = gf.pickNearestRes("ResourceI", unit.getxPos(), flag);
                    if (bas != null && !unit.getCondition().equals("Extracting")) {
                        count++;
                        unit.setAimX(bas.getXPos());
                        unit.setCondition("Extracting");
                    }
                } else {
                    Base bas = gf.pickNearestRes("ResourceS", unit.getxPos(), flag);
                    if (bas != null && !unit.getCondition().equals("Extracting")) {
                        count++;
                        unit.setAimX(bas.getXPos());
                        unit.setCondition("Extracting");
                    }
                }
            }

        }
    }

    public void behave() {
        thisBases = gf.getBases(flag);
        thisUnits = gf.getUnits(flag);
        boolean repaired = true;
        Base toRepair = null;
        xPos = prX;
        uploadUnits();
        if (progress >= plan.length - 1) {
            sendHarvest(4);
        }
        if (shortageRes)
            sendHarvest(4);
        for (Base base : thisBases) {
            if (base.getHp() < base.getMaxHp()) {
                repaired = false;
                toRepair = base;
            }
            xPos += flipDev * ResLoader.getBase1().getWidth();
        }
        if (repaired)
            buildPlan();
        else {
            Unit worker = pickNearest(toRepair.getXPos(), flag);
            while (worker != null) {
                worker.setAimX(toRepair.getXPos());
                worker.setCondition("Working");
                worker = pickNearest(toRepair.getXPos(), flag);
            }
        }
    }
}
