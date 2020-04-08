package Fields;

import Resources.ResLoader;
import org.newdawn.slick.*;

public class MainWindow extends BasicGame {
    public static String state = "Menu";
    static GameField gameField;
    Menu menu;
    Level lvl;
    public static int Width, Height;
    public static int level;
    private Music music1, music2;
    private Music music;
    private Sound wind;
    private Sound click;
    boolean isInit = false;

    public MainWindow(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        ResLoader.setScale(2);
        ResLoader.load();
        menu = new Menu();
        lvl = new Level();
        Width = gameContainer.getWidth();
        Height = gameContainer.getHeight();
        level = 0;
        int[] params = new int[]{8192, 4, 300, 100, 100};
        menu.init(gameContainer);
        lvl.init(gameContainer);
        music1 = new Music("raw\\Nature\\Music1.ogg");
        music2 = new Music("raw\\Nature\\Music2.ogg");
        wind = ResLoader.getSound("Wind");
        click = ResLoader.getSound("Click2");
    }

    private void checkSounds() {
        switch (state) {
            case "Menu":
                music = music1;
                break;
            case "Game":
                music = music2;
                if (!wind.playing())
                    wind.play(1, 0.1f);
                break;
        }
        if (!music.playing())
            music.play(1, 0.1f);

    }

    public static void initGameField(GameContainer gmc, int[] params) throws SlickException {
        gameField = new GameField();
        gameField.init(gmc, level, params);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        checkSounds();
        switch (state) {
            case "Game":
                gameField.update(gameContainer);
                break;
            case "Menu":
                menu.update(gameContainer);
                break;
            case "Choose":
                lvl.update(gameContainer);
                break;
            case "Sandbox":
                break;
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        switch (state) {
            case "Game":
                gameField.render(gameContainer);
                break;
            case "Menu":
                menu.render(gameContainer);
                break;
            case "Choose":
                lvl.render(gameContainer);
                break;
        }
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer agc = new AppGameContainer(new MainWindow("Game"));
        agc.setDisplayMode(1280, 720, true);
        agc.setSmoothDeltas(true);
        agc.setShowFPS(false);
        agc.setTargetFrameRate(200);
        agc.start();
    }

    public static int getWidth() {
        return Width;
    }

    public static int getHeight() {
        return Height;
    }
}
