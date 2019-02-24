package com.battleshipbot.game;

enum ShipType {
    DEFAULT     ("default",0, "6"),
    CARRIER     ("carrier",5, "5"),
    BATTLESHIP  ("battleship",4, "4"),
    CRUISER     ("cruiser",3, "3"),
    SUBMARINE   ("submarine",3, "2"),
    DESTROYER   ("destroyer",2, "1");

    private final int length;
    private final String id;
    private final String name;

    ShipType(String n, int l, String i) {
        name = n;
        length = l;
        id = i;
    }

    public boolean equals(String n) {
        return n.equalsIgnoreCase(name);
    }

    public int getLength() {
        return length;
    }

    public String getID() {
        return id;
    }

    public int getIDAsInt() {
        return Integer.parseInt(id);
    }

    static public ShipType valueOf(int i) {
        for (ShipType s : ShipType.class.getEnumConstants()) {
            if (s.getIDAsInt() == i)
                return s;
        }
        return ShipType.DEFAULT;
    }
}