package Resources;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.util.HashMap;
import java.util.Map;

public class ResLoader {
    private static Image temple1, temple2, tank1, tank2, help1, ship1, hero, fish1, background1, background2;
    private static Image base1, base2, base3, base4, base5, basement1, tile1, tile2, alga1, bubble1, bA1, bB1, bC1;
    private static Image coin, button1;
    private static Image hero2, tank3, worker1, menuLabel, build1, build2, build3, stone1;
    private static float scale = 1;
    private static Map<String, Image> map;
    private static Map<String, Sound> soundMap;

    public static Image getImage(String value) {
        return map.get(value);
    }

    public static Sound getSound(String value) {
        return soundMap.get(value);
    }

    private static void loadAnims() throws SlickException {
        for (int i = 0; i < 4; i++) {
            map.put("Ex1" + (i + 1), new Image("res\\Explosions\\a" + (i + 1) + ".png"
                    , false, Image.FILTER_NEAREST).getScaledCopy(scale));
        }
        for (int i = 0; i < 10; i++) {
            map.put("Ex2" + (i + 1), new Image("res\\Explosions\\b" + (i + 1) + ".png"
                    , false, Image.FILTER_NEAREST).getScaledCopy(scale));
        }
        for (int i = 0; i < 6; i++) {
            map.put("Ex3" + (i + 1), new Image("res\\Bullets\\Rockets\\b" + (i + 1) + ".png"
                    , false, Image.FILTER_NEAREST).getScaledCopy(scale));
        }
        for (int i = 0; i < 8; i++) {
            map.put("D" + (i + 1), new Image("res\\Heroes\\D" + (i + 1) + ".png"
                    , false, Image.FILTER_NEAREST).getScaledCopy(scale));
        }
    }

    private static void loadIcons() throws SlickException {
        for (int i = 1; i <= 5; i++) {
            map.put("Tank" + i + "I", new Image("res\\Interface\\Tank" + i + "I.png"
                    , false, Image.FILTER_NEAREST).getScaledCopy(scale));
        }
        map.put("Tank2UpI", new Image("res\\Interface\\Tank2UpI.png"
                , false, Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Tank3UpI", new Image("res\\Interface\\Tank3UpI.png"
                , false, Image.FILTER_NEAREST).getScaledCopy(scale));
        for (int i = 1; i <= 2; i++) {
            map.put("Academy" + i, new Image("res\\Interface\\Academy" + i + ".png"
                    , false, Image.FILTER_NEAREST).getScaledCopy(scale));
        }
        for (int i = 1; i <= 3; i++) {
            map.put("lvlA" + i, new Image("res\\Interface\\lvlA" + i + ".png",
                    false, Image.FILTER_NEAREST).getScaledCopy(scale));
        }
        for (int i = 1; i <= 3; i++) {
            map.put("lvlB" + i, new Image("res\\Interface\\lvlA" + i + ".png",
                    false, Image.FILTER_NEAREST).getScaledCopy(scale));
        }
    }

    private static void loadSounds() throws SlickException {
        soundMap.put("Shot1", new Sound("raw\\Shot1.ogg"));
        soundMap.put("Shot2", new Sound("raw\\Shot2.ogg"));
        soundMap.put("Shot3", new Sound("raw\\Shot3.ogg"));
        soundMap.put("Shot4", new Sound("raw\\Shot4.ogg"));
        soundMap.put("Click1", new Sound("raw\\Click1.ogg"));
        soundMap.put("Click2", new Sound("raw\\Click2.ogg"));
        soundMap.put("Wind", new Sound("raw\\Nature\\Wind.ogg"));
    }

    public static void load() throws SlickException {
        map = new HashMap<>();
        soundMap = new HashMap<>();
        loadIcons();
        loadAnims();
        loadSounds();
        map.put("Tile3", new Image("res\\Objects\\Map2\\Tile1.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Alga3", new Image("res\\Objects\\Map1\\Alga3.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Alga4", new Image("res\\Objects\\Map1\\Alga4.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("TurretShot", new Image("res\\Bullets\\Rockets\\anim4.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Space1", new Image("res\\Interface\\Backgrounds\\Space1.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("ButtonPlay", new Image("res\\Interface\\ButtonPlay.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("ButtonLevel", new Image("res\\Interface\\ButtonLevel.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Base10", new Image("res\\Objects\\Base10.png"
                , false, Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Base9", new Image("res\\Objects\\Base9.png"
                , false, Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Base8", new Image("res\\Objects\\Base8.png"
                , false, Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Base7", new Image("res\\Objects\\Base7.png"
                , false, Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Base6", new Image("res\\Objects\\Base6.png"
                , false, Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Base2", new Image("res\\Objects\\Base2.png"
                , false, Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Panel", new Image("res\\Interface\\Panel.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Corner", new Image("res\\Interface\\Corner.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("ChooseIdle", new Image("res\\Interface\\ChooseIdle.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Frame1", new Image("res\\Interface\\Frame1.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Frame2", new Image("res\\Interface\\Frame2.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("PanelSmall", new Image("res\\Interface\\PanelSmall.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("WorkerI", new Image("res\\Interface\\DroneI.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        for (int i = 0; i <= 5; i++) {
            map.put("bu" + i, new Image("res\\Objects\\b" + i + ".png", false,
                    Image.FILTER_NEAREST).getScaledCopy(scale));
        }
        map.put("f1", new Image("res\\Objects\\f1.png"
                , false, Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Build10", new Image("res\\Interface\\Build10.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("ButtonSandbox", new Image("res\\Interface\\ButtonSandbox.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("ButtonTips", new Image("res\\Interface\\ButtonTips.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Build9", new Image("res\\Interface\\Build9.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Build8", new Image("res\\Interface\\Build8.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Build7", new Image("res\\Interface\\Build7.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Build6", new Image("res\\Interface\\Build6.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Build5", new Image("res\\Interface\\Build5.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Build4", new Image("res\\Interface\\Build4.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Stone", new Image("res\\Objects\\Map1\\Stone.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Iron", new Image("res\\Objects\\Map1\\IronMine.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Background3", new Image("res\\Objects\\Map1\\Background3.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Drone", new Image("res\\Heroes\\Drone.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("StoneIcon", new Image("res\\Objects\\Stone1.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("IronIcon", new Image("res\\Objects\\IronBar.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        //Bullets
        bA1 = new Image("res\\Bullets\\Rockets\\anim1.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        bB1 = new Image("res\\Bullets\\Rockets\\anim2.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        bC1 = new Image("res\\Bullets\\Rockets\\anim3.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        //Objects
        ship1 = new Image("res\\Objects\\Ship1.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        temple1 = new Image("res\\Objects\\Temple1.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        temple2 = new Image("res\\Objects\\Temple2.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        base1 = new Image("res\\Objects\\Base1.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        basement1 = new Image("res\\Objects\\Basement1.png",
                false, Image.FILTER_LINEAR).getScaledCopy(scale);
        base2 = new Image("res\\Objects\\Base2.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        base3 = new Image("res\\Objects\\Base3.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        base4 = new Image("res\\Objects\\Base4.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        base5 = new Image("res\\Objects\\Base5.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        //Interface
        coin = new Image("res\\Objects\\Coin.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        build1 = new Image("res\\Interface\\Build1.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        build2 = new Image("res\\Interface\\Build2.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        build3 = new Image("res\\Interface\\Build3.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        button1 = new Image("res\\Interface\\Button1.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        menuLabel = new Image("res\\Interface\\MenuLabel.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        //Landscape
        tile1 = new Image("res\\Objects\\Map1\\Tile1.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        tile2 = new Image("res\\Objects\\Map1\\Tile2.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        alga1 = new Image("res\\Objects\\Map1\\Alga2.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        fish1 = new Image("res\\Objects\\Map1\\Fish1.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        background1 = new Image("res\\Objects\\Map1\\Background1.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        background2 = new Image("res\\Objects\\Map1\\Background2.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        bubble1 = new Image("res\\Objects\\Bubble1.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        //Heroes
        for (int i = 1; i <= 3; i++) {
            map.put("Tank" + i, new Image("res\\Heroes\\Tank" + i + ".png", false,
                    Image.FILTER_NEAREST).getScaledCopy(scale));
        }
        map.put("Tank2+", new Image("res\\Heroes\\Tank2+.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        map.put("Tank3+", new Image("res\\Heroes\\Tank3+.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale));
        tank1 = new Image("res\\Heroes\\Tank1.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        tank2 = new Image("res\\Heroes\\Tank2.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        tank3 = new Image("res\\Heroes\\Tank3.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        help1 = new Image("res\\Heroes\\Help1.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        hero = new Image("res\\Heroes\\Hero1.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        hero2 = new Image("res\\Heroes\\Hero3.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);
        worker1 = new Image("res\\Heroes\\Worker1.png", false,
                Image.FILTER_NEAREST).getScaledCopy(scale);

    }

    public static Image getWorker1() {
        return worker1;
    }

    public static Image getButton1() {
        return button1;
    }

    public static Image getHero2() {
        return hero2;
    }

    public static Image getCoin() {
        return coin;
    }

    public static Image getTank3() {
        return tank3;
    }

    public static Image getbA1() {
        return bA1;
    }

    public static Image getbB1() {
        return bB1;
    }

    public static Image getbC1() {
        return bC1;
    }

    public static Image getTemple1() {
        return temple1;
    }

    public static Image getTemple2() {
        return temple2;
    }

    public static Image getBase1() {
        return base1;
    }

    public static Image getBase2() {
        return base2;
    }

    public static Image getBase3() {
        return base3;
    }

    public static Image getBase4() {
        return base4;
    }

    public static Image getBase5() {
        return base5;
    }

    public static Image getHelp1() {
        return help1;
    }

    public static Image getTank1() {
        return tank1;
    }

    public static Image getTank2() {
        return tank2;
    }

    public static Image getShip1() {
        return ship1;
    }

    public static Image getBasement1() {
        return basement1;
    }

    public static Image getBubble1() {
        return bubble1;
    }

    public static Image getBackground1() {
        return background1;
    }

    public static Image getBackground2() {
        return background2;
    }

    public static Image getTile1() {
        return tile1;
    }

    public static Image getTile2() {
        return tile2;
    }

    public static Image getAlga1() {
        return alga1;
    }

    public static Image getFish1() {
        return fish1;
    }

    public static void setScale(float scale) {
        ResLoader.scale = scale;
    }

    public static float getScale() {
        return scale;
    }

    public static Image getHero() {
        return hero;
    }


    public static Image getMenuLabel() {
        return menuLabel;
    }

    public static Image getBuild1() {
        return build1;
    }

    public static Image getBuild2() {
        return build2;
    }

    public static Image getBuild3() {
        return build3;
    }

    public static Image getStone1() {
        return stone1;
    }
}
