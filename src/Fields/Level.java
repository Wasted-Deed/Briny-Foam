package Fields;

import Resources.ResLoader;
import org.newdawn.slick.*;

import java.util.ArrayList;

public class Level {
    ArrayList<ButtonLvl> buttons;
    Image backGround;
    int st1;
    int step;
    int buttonW;

    class ButtonLvl {
        int x, y;
        int state;
        Image img;

        public ButtonLvl(int x, int y, int state) {
            this.x = x;
            this.y = y;
            this.state = state;
            if (state >= 1 && state <= 3)
                img = ResLoader.getImage("lvlA" + state);
            else if (state >= 4 && state <= 6)
                img = ResLoader.getImage("lvlB" + (state - 3));
            buttonW = img.getWidth();
        }

        void draw() {
            img.draw(x - img.getWidth() / 2, y - img.getHeight() / 2);
        }

        boolean isTouched(int x1, int y1) {
            if (Math.abs(x1 - x) <= img.getWidth() / 2 &&
                    Math.abs(y1 - y) <= img.getHeight() / 2)
                return true;
            return false;
        }
    }

    void init(GameContainer gmc) {
        step = 30;
        int n = 3;
        st1 = gmc.getWidth() / 4;
        buttons = new ArrayList<>();
        float st = ResLoader.getImage("lvlA1").getHeight();

        for (int i = 1; i <= n; i++) {
            buttons.add(new ButtonLvl(st1, (int) (step * 2 + st * i), i));
            buttons.add(new ButtonLvl(st1 * 2, (int) (step * 2 + st * i), i + 3));
        }
        backGround = ResLoader.getImage("Space1");
    }

    void update(GameContainer gmc) throws SlickException {
        Input input = gmc.getInput();
        for (ButtonLvl bl : buttons) {
            if (bl.isTouched(input.getMouseX(), input.getMouseY()) && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                MainWindow.level = bl.state;
                MainWindow.state = "Menu";
                MainWindow.initGameField(gmc, new int[]{8192});
            }
        }
    }

    void render(GameContainer gmc) {
        Graphics graphics = gmc.getGraphics();
        int width = gmc.getWidth();
        int height = gmc.getHeight();
        backGround.draw(width / 2 - backGround.getWidth() / 2, height / 2 - backGround.getHeight() / 2);
        Image label1 = ResLoader.getMenuLabel();
        label1.draw(width - label1.getWidth(), 0);
        label1.getFlippedCopy(true, false).draw(0, 0);
        for (ButtonLvl bl : buttons) {
            bl.draw();
        }
        graphics.drawString("Campaign:", st1 - buttonW / 2, step * 2);
        graphics.drawString(" Waves:", st1 * 2 - buttonW / 2, step * 2);
        graphics.drawString("Arcade:", st1 * 3 - buttonW / 2, step * 2);
    }
}
