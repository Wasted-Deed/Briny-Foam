package Players;

import Objects.UnitTypes.Unit;

import java.util.ArrayList;

public class Formation {
    ArrayList<Unit> units;
    private String type;
    private int size;
    private int flag;
    private int aimX;
    private int curSize;
    private boolean isInFight;

    public Formation(int size, int flag, String type) {
        this.size = size;
        this.flag = flag;
        this.type = type;
        units = new ArrayList<>();
        curSize = 0;
        isInFight = false;
    }

    public int getAimX() {
        return aimX;
    }

    public void update() {
        curSize = units.size();
        isInFight = false;
        if (curSize > 0) {
            for (int i = 0; i < units.size(); i++) {
                Unit unit = units.get(i);
                unit.setAimX(aimX);
                if (unit.getCondition().equals("Shooting")) {
                    unit.setAimX(unit.getxPos());
                    isInFight = true;
                }
                if (unit.getHp() <= 0)
                    units.remove(i);
            }
        }
    }

    public void setAimX(int aimX) {
        this.aimX = aimX;
    }

    public String getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public int getCurSize() {
        return curSize;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void addUnit(Unit unit) {
        if (unit.getType().equals(type) && units.size() < size &&
                unit.getFlag() == flag && !units.contains(unit))
            units.add(unit);
    }

    public boolean isFilled() {
        return curSize >= size;
    }

    public boolean isInFight() {
        return isInFight;
    }
}

