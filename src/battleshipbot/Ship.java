package battleshipbot;

public class Ship {
    private final int length;
    private int direction;
    private final String name;
    private boolean afloat;
    private int[] locations;
    public Ship(int l, String n) {
        length = l;
        name = n;
        afloat = true;
        locations = new int[l];
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
    public void sink() {
        afloat = false;
    }
    public boolean isAfloat() {
        return afloat;
    }
    public void occupySpace(int[] spaces) {
        locations = spaces;
    }
    public int[] spacesOccupied() {
        return locations;
    }
}
