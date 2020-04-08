package Resources;

public class Iron extends Resource {

    public Iron(float xPos, float yPos) {
        super(xPos, yPos);
        img = ResLoader.getImage("Iron");
        type = Type.Iron;
    }
}
