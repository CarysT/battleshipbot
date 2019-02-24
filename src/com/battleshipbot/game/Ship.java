package com.battleshipbot.game;

class Ship {
    private final ShipType type;
    private boolean afloat;
    private int[] locations;
    public Ship(ShipType t) {
        type = t;
        afloat = true;
        locations = new int[t.getLength()];
    }
    public int getLength() {
        return type.getLength();
    }
    public ShipType getType() {
        return type;
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
