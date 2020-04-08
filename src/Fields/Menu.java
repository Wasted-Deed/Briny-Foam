package Fields;

import Interface.Button;
import Resources.ResLoader;
import org.newdawn.slick.*;

public class Menu {
    private float initialLine = -1;
    private Button button, button1;
    private Button button2, button3;
    private Image backGround;

    public void drawWait(GameContainer gmc, int x, int y) {
        int width = 200;
        int height = 20;
        Graphics graphics = gmc.getGraphics();
        graphics.drawRect(x, y, width, height);
        if (initialLine == -1)
            initialLine = x;
        if (initialLine < x + width + 100) {
            initialLine += 2;
        } else
            initialLine = -100;
        if (initialLine > x && initialLine < x + width) {
            graphics.setColor(new Color(100, 100, 100, 50));
        }
        for (int i = x; i < x + width; i++) {
            graphics.setColor(new Color(100, 100, 100, 50 / (Math.abs(i - initialLine) + 150)));
            graphics.setLineWidth(1);
            graphics.drawLine(i, y + 1, i, y + 19);
        }
        graphics.setColor(Color.white);
    }

    void init(GameContainer gmc) {
        int width = gmc.getWidth();
        int height = gmc.getHeight();
        backGround = ResLoader.getImage("Space1");
        int x0 = 100;
        int step = 20;
        button = new Button(width / 2, x0, "Play");
        int h = button.getHeight();
        button1 = new Button(width / 2, x0 + h + step, "Choose");
        button2 = new Button(width / 2, x0 + 2 * (h + step), "Sandbox");
        button3 = new Button(width / 2, x0 + 3 * (h + step), "Tips");
    }

    private void checkContacts(GameContainer gmc) {
        Input input = gmc.getInput();
        if (input.isKeyPressed(Input.KEY_ESCAPE))
            gmc.exit();
        boolean isClicked = false;
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
            isClicked = true;
        button1.check(gmc, isClicked);
        button.check(gmc, isClicked);
    }

    void update(GameContainer gmc) {
        checkContacts(gmc);
    }

    private void drawButtons(GameContainer gmc) {
        button.draw(gmc);
        button1.draw(gmc);
        button2.draw(gmc);
        button3.draw(gmc);
        Graphics graphics = gmc.getGraphics();
        graphics.drawString("Level: " + MainWindow.level, gmc.getWidth() / 4, 10);
    }

    void render(GameContainer gmc) {
        int width = gmc.getWidth();
        int height = gmc.getHeight();
        backGround.draw(width / 2 - backGround.getWidth() / 2, height / 2 - backGround.getHeight() / 2);
        drawWait(gmc, width / 2 - 100, height - 100);
        Image label1 = ResLoader.getMenuLabel();
        label1.draw(width - label1.getWidth(), 0);
        label1.getFlippedCopy(true, false).draw(0, 0);
        drawButtons(gmc);
    }
}
