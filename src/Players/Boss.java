package Players;

import Objects.UnitTypes.Unit;
import Resources.ResLoader;
import org.newdawn.slick.Image;

public class Boss {
    private Image img1;
    private float x, y;
    private float aimX, aimY;
    private float speed;
    private Unit target;
    private int dist;

    public Boss(float x, float y, Unit target) {
        this.x = x;
        this.y = y;
        this.target = target;
        img1 = ResLoader.getHero();
        speed = 3;
        aimX = (float) (Math.random() * 1000 - 500 + target.getxPos());
        aimY = (float) (-Math.random() * 500 + target.getyPos());
    }

    public void draw() {
        img1.draw(x - img1.getWidth() / 2, y - img1.getHeight() / 2);
    }

    public void behave() {
        float diag = (float) Math.sqrt(Math.pow(aimX - x, 2) + Math.pow(aimY - y, 2));
        float cos = (aimX - x) / diag;
        float sin = (aimY - y) / diag;
        if (Math.abs(aimX - x) > 5 || Math.abs(aimY - y) > 5) {
            x += cos * speed;
            y += sin * speed;
            dist += speed;
        }
        if (Math.abs(x - target.getxPos()) <= 5 && Math.abs(y - target.getyPos()) <= 5) {
            dist = 0;
        } else if (dist > 400) {
            aimX = target.getxPos();
            aimY = target.getyPos();
        }
        if (Math.abs(aimX - x) <= 5 && Math.abs(aimY - y) <= 5) {
            aimX = (float) (Math.random() * 1000 - 500 + target.getxPos());
            aimY = (float) (-Math.random() * 500 + target.getyPos());
        }
    }
}

