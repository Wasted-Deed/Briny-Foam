package Objects.Plans;

import Landscape.Map1;
import Objects.Buildings.Base;
import Objects.UnitTypes.Unit;

import java.util.ArrayList;

public class EnemyPlan1 {
    ArrayList<Base> bases;
    ArrayList<Unit> units;
    private float x, y;
    private int flag;
    private float availX;

    public EnemyPlan1(float x, float y, int flag) {
        this.x = x;
        this.y = y;
        this.flag = flag;
        bases = new ArrayList<>();
        units = new ArrayList<>();
    }

    public void updateBuilds() {
        int minHp;
        for (Base base : bases) {

        }
    }

    public void behave() {
        int countHall = 0;
        int countWorkers = 0;
        for (int i = 0; i < bases.size(); i++) {
            if (bases.get(i).getType().equals("Centre"))
                countHall++;
        }
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i).getType().equals("Worker"))
                countWorkers++;
        }
        if (countHall < 1 && countWorkers > 0) {
            bases.add(new Base(availX, Map1.getLevel(), flag, "Centre"));
        }
        if (countHall == 0 && countWorkers == 0) {
        }
    }
}
