package Objects.Units;

import Fields.GameField;
import Objects.UnitTypes.Unit;
import Resources.ResLoader;
import org.newdawn.slick.Sound;

import java.util.ArrayList;

public class Soldier extends Unit {
    private int timer = 0;
    private Sound[] sounds;

    Soldier(float xPos, float yPos, int flag) {
        super(xPos, yPos, flag);
        sounds = new Sound[4];
        for (int i = 0; i < sounds.length; i++) {
            sounds[i] = ResLoader.getSound("Shot" + (i + 1));
        }
    }

    private void shoot() {
        GameField.rockets.add(new Rocket("Armorer", xPos, yPos - 20, flag, flipped));
        int rand = (int) (Math.random() * 4);
        if (rand == 4)
            rand = 3;
        sounds[rand].playAt(xPos, yPos, 0);
    }

    @Override
    public void behave(ArrayList<Unit> units) {
        super.behave(units);
        if (condition.equals("Shooting")) {
            if (target != null && (Math.abs(aimX - xPos) <= img1.getWidth() / 4 || flag < 0)) {
                if (target.getxPos() > xPos)
                    flipped = 1;
                else flipped = -1;
                timer++;
                if (timer > loadSpeed) {
                    shoot();
                    timer = 0;
                }
            } else timer = 0;
        } else target = null;
    }

}
