package battleshipbot;

public class Ship {
    private final int length;
    private int direction;
    private final String name;
    public Ship(int l, String n) {
        length = l;
        name = n;
    }
    public int getLength() {
        return length;
    }
    public String getType() {
        return name;
    }
    public int getDirection() {
        return direction;
    }
    public void setDirection(int d) {
        direction = d;
    }
}
