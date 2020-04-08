package Players;

import Objects.Buildings.Base;
import Objects.UnitTypes.Unit;

import java.util.ArrayList;

public class CleverPlayer {
    ArrayList<Unit> thisUnits;
    ArrayList<Base> thisBases;
    int x;
    int flag;

    public CleverPlayer(int x, int flag) {
        this.x = x;
        this.flag = flag;
        thisUnits = new ArrayList<>();
        thisBases = new ArrayList<>();
    }

    public void setThisUnits(ArrayList<Unit> thisUnits) {
        this.thisUnits = thisUnits;
    }

    public void setThisBases(ArrayList<Base> thisBases) {
        this.thisBases = thisBases;
    }

    void update() {
    }
}
