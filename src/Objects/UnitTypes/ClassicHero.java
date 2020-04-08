package Objects.UnitTypes;

import org.newdawn.slick.Image;

import java.util.Map;

public class ClassicHero {
    enum Weapon {
        Hammer, Gun;
    }

    private Map<Image[], String> animations;
    private Image idle;
    private String name;
    private int hitPoints;
    private int level;
    private float positionX;
    private float positionY;
    private float runCoefficient;
    private float walkSpeed;
    private float damage;
    Weapon weapon;

    public ClassicHero(String name, float positionX, float positionY) {
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
    }
    private void act(int input){
        switch (input){
            case 0:
        }
    }
    public void behave(int input) {

    }

    public void draw() {

    }
}
