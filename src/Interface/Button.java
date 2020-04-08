package Interface;

import Fields.MainWindow;
import Resources.ResLoader;
import org.newdawn.slick.*;

public class Button {
    public float xPos, yPos;
    public String type;
    private Image img1;
    private float sizeX, sizeY;
    private long timer = -1;
    private int await = 500;

    public Button(float xPos, float yPos, String type) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.type = type;
        switch (type) {
            case "Upgrade":
                img1 = ResLoader.getButton1();
                break;
            case "Play":
                img1 = ResLoader.getImage("ButtonPlay");
                break;
            case "Choose":
                img1 = ResLoader.getImage("ButtonLevel");
                break;
            case "Sandbox":
                img1 = ResLoader.getImage("ButtonSandbox");
                break;
            case "Tips":
                img1 = ResLoader.getImage("ButtonTips");
                break;
        }
        if (img1 != null) {
            sizeX = img1.getWidth();
            sizeY = img1.getHeight();
        } else {
            sizeX = ResLoader.getScale() * 100;
            sizeY = sizeX / 5;
        }
    }

    public int getHeight() {
        return img1.getHeight();
    }

    public void draw(GameContainer gmc) {
        if (img1 != null)
            img1.draw(xPos - sizeX / 2, yPos - sizeY / 2);
        else {
            Graphics graphics = gmc.getGraphics();
            graphics.fillRect(xPos - sizeX / 2, yPos - sizeY / 2, sizeX, sizeY);
            graphics.setColor(Color.black);
            graphics.drawString(type, xPos - sizeX / 4, yPos - sizeY / 2);
            graphics.setColor(Color.white);
        }
    }

    public void check(GameContainer gmc, boolean isClicked) {
        Input in = gmc.getInput();
        float mouseX = in.getMouseX();
        float mouseY = in.getMouseY();
        if (isClicked &&
                Math.abs(mouseX - xPos) < sizeX / 2 && Math.abs(mouseY - yPos) < sizeY / 2 &&
                timer == -1) {
            ResLoader.getSound("Click2").play(1, 2);
            timer = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - timer >= await && timer != -1) {
            timer = -1;
            switch (type) {
                case "Play":
                    if (MainWindow.level != 0)
                        MainWindow.state = "Game";
                    break;
                case "Choose":
                    MainWindow.state = "Choose";
                    break;
                case "Sandbox":
                    MainWindow.state = "Sandbox";
                    break;
            }
        }
    }
}
