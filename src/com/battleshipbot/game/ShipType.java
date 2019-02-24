package com.battleshipbot.game;

enum ShipType {
    DEFAULT     (0, "0"),
    CARRIER     (5, "5"),
    BATTLESHIP  (4, "4"),
    CRUISER     (3, "3"),
    SUBMARINE   (3, "2"),
    DESTROYER   (2, "1");

    private final int length;
    private final String id;

    ShipType(int l, String i) {
        length = l;
        id = i;
    }

    public int getLength() {
        return length;
    }

    public String getID() {
        return id;
    }
}