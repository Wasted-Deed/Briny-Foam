package Players;

import Landscape.Map1;
import Objects.UnitTypes.Unit;
import Objects.Units.*;

import java.util.ArrayList;

public class WaveGenerator {
    private ArrayList<Unit> units;
    private long time;
    private long timer;
    private int stage;
    private int level;
    private int field;

    public WaveGenerator(ArrayList<Unit> units, int level, int field) {
        this.units = units;
        this.level = level;
        this.field = field;
        time = System.currentTimeMillis();
        stage = 0;
    }

    private void firstStage() {
        switch (level) {
            case 1:
                createTanks(1);
                break;
        }
    }

    private void createTanks(int type) {
        Soldier tank;
        switch (type) {
            case 1:
                tank = new Tank1(-100, Map1.getLevel(), -1);
                tank.setAimX(field / 2);
                units.add(tank);
                tank = new Tank1(field + 100, Map1.getLevel(), -1);
                tank.setAimX(field / 2);
                units.add(tank);
                break;
            case 2:
                tank = new Tank2(-110, Map1.getLevel(), -1);
                tank.setAimX(field / 2);
                units.add(tank);
                tank = new Tank2(field + 110, Map1.getLevel(), -1);
                tank.setAimX(field / 2);
                units.add(tank);
                break;
            case 3:
                tank = new Tank3(-120, Map1.getLevel(), -1);
                tank.setAimX(field / 2);
                units.add(tank);
                tank = new Tank3(field + 120, Map1.getLevel(), -1);
                tank.setAimX(field / 2);
                units.add(tank);
                break;
            case 4:
                tank = new Tank4(-130, Map1.getLevel(), -1);
                tank.setAimX(field / 2);
                units.add(tank);
                tank = new Tank4(field + 130, Map1.getLevel(), -1);
                tank.setAimX(field / 2);
                units.add(tank);
                break;
        }
    }

    private void otherStages() {
        switch (level) {
            case 1:
                switch (stage) {
                    case 2:
                        createTanks(1);
                        createTanks(2);
                        break;
                    case 3:
                        for (int i = 0; i < 2; i++) {
                            createTanks(1);
                            createTanks(2);
                        }
                        break;
                    case 4:
                        createTanks(3);
                        for (int i = 0; i < 3; i++) {
                            createTanks(1);
                            createTanks(2);
                        }
                        break;
                }
                break;
        }
    }

    public void behave() {
        timer = System.currentTimeMillis() - time;
        switch (stage) {
            case 0:
                if (timer > 3000) {
                    stage++;
                    time = System.currentTimeMillis();
                }
                break;
            case 1:
                if (timer > 4000) {
                    stage++;
                    firstStage();
                    timer = System.currentTimeMillis();
                }
                break;
            default:
                if (timer > 15000) {
                    stage++;
                    otherStages();
                    timer = System.currentTimeMillis();
                }
        }
    }
}
