package Fields;

import Resources.ResLoader;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import java.util.ArrayList;

public class Sandbox {
    ArrayList<ButtonLvl> buttons;
    Image backGround;

    class ButtonLvl {
        int x, y;
        int state;
        Image img;

        public ButtonLvl(int x, int y, int state) {
            this.x = x;
            this.y = y;
            this.state = state;
            img = ResLoader.getImage("lvlA" + state);
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
        int step = 10;
        int n = 3;
        buttons = new ArrayList<>();
        float st = ResLoader.getImage("lvlA1").getHeight();
        for (int i = 1; i <= n; i++) {
            buttons.add(new ButtonLvl(gmc.getWidth() / 2, (int) (step + st * i), i));
        }
        backGround = ResLoader.getImage("Space1");
    }

    void update(GameContainer gmc) {
        Input input = gmc.getInput();
        for (ButtonLvl bl : buttons) {
            if (bl.isTouched(input.getMouseX(), input.getMouseY()) && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                MainWindow.level = bl.state;
                MainWindow.state = "Menu";
            }
        }
    }

    void render(GameContainer gmc) {
        int width = gmc.getWidth();
        int height = gmc.getHeight();
        backGround.draw(width / 2 - backGround.getWidth() / 2, height / 2 - backGround.getHeight() / 2);
        Image label1 = ResLoader.getMenuLabel();
        label1.draw(width - label1.getWidth(), 0);
        label1.getFlippedCopy(true, false).draw(0, 0);
        for (ButtonLvl bl : buttons) {
            bl.draw();
        }
    }
}
