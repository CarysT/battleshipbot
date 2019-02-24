package com.battleshipbot.game;

import net.dv8tion.jda.core.entities.Member;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Game {
    private Board playerBoard = new Board();
    private Board AIBoard = new Board();
    private HashMap<Ship, Boolean> playerShips = new HashMap<>();
    private HashMap<Ship, Boolean> AIShips = new HashMap<>();
    private final Ship[] availableShips = new Ship[ShipType.values().length];
    public Game(Member player) {
        newGame(player.getUser().getId());
    }
    public Game(Board pb, Board ab) {
        playerBoard = pb;
        AIBoard = ab;
        for (int i = 0; i < ShipType.values().length; i++)
            availableShips[i] = new Ship(ShipType.valueOf(i));
        for (ShipType s : ShipType.class.getEnumConstants()) {
            playerShips.put(new Ship(s), false);
            AIShips.put(new Ship(s), false);
        }
    }
    private void newGame(String userid) {
        try (FileWriter writer = new FileWriter("saves/" + userid + ".txt", false)) {
            writer.write(userid + "\n");
            writer.write(playerBoard.print());
            writer.write(AIBoard.print());
        } catch(IOException e) {
            System.out.println("Error in creating a new game!");
            System.out.println(e.getMessage());
        }
    }
    public boolean placeShip(String command, boolean isPlayer) {
        try {
            command = command.replace(command.substring(0,command.indexOf(" ")+1), "");
            String name = command.substring(0, command.indexOf(" "));
            ShipType type = ShipType.DEFAULT;
            for (ShipType s : ShipType.values()) {
                if (s.equals(name)) {
                    type = s;
                }
            }
            for (Ship s : playerShips.keySet()) {
                if(s != null){
                    Boolean isPlaced = playerShips.get(s);
                    if (isPlaced) {
                        return false;
                    }

                }
            }
            int length = type.getLength();
            command = command.replace(command.substring(0,command.indexOf(" ")+1), "");
            int coordy = Integer.parseInt(command.substring(0,1));
            int coordx = command.charAt(1)-65;
            command = command.replace(command.substring(0,command.indexOf(" ")+1), "");
            String direction = command;
            if (isPlayer && ("north".equalsIgnoreCase(direction) || "south".equalsIgnoreCase(direction) ||
                "east".equalsIgnoreCase(direction) || "west".equalsIgnoreCase(direction))) {
                Board copy = playerBoard;
                if ("north".equalsIgnoreCase(direction)) {
                    for (int i = 0; i < length; i++) {
                        if (!"0".equals(copy.getPosition(coordx, coordy)))
                            throw new Exception("Ship already there.");
                        copy.setPosition(coordx, coordy, type.getID());
                        coordx += -1;
                    }
                }
                else if ("east".equalsIgnoreCase(direction)) {
                    for (int i = 0; i < length; i++) {
                        if (!"0".equals(copy.getPosition(coordx, coordy)))
                            throw new Exception("Ship already there.");
                        copy.setPosition(coordx, coordy, type.getID());
                        coordy += 1;
                    }
                }
                else if ("south".equalsIgnoreCase(direction)) {
                    for (int i = 0; i < length; i++) {
                        if (!"0".equals(copy.getPosition(coordx, coordy)))
                            throw new Exception("Ship already there.");
                        copy.setPosition(coordx, coordy, type.getID());
                        coordx += 1;
                    }
                }
                else if ("west".equalsIgnoreCase(direction)) {
                    for (int i = 0; i < length; i++) {
                        if (!"0".equals(copy.getPosition(coordx, coordy)))
                            throw new Exception("Ship already there.");
                        copy.setPosition(coordx, coordy, type.getID());
                        coordy += -1;
                    }
                }
                else {
                    return false;
                }
                for (Ship s : availableShips)
                    if (s.getType() == type)
                        playerShips.replace(s, true);
                playerBoard = copy;
                return true;
            }
            return false;
        } catch(Exception e) {
            System.out.println("Error placing ship: " + e.getMessage());
            return false;
        }
    }
    public boolean shootAt(int x, int y, boolean isPlayer) {
        return(isPlayer) ? playerBoard.isAHit(x, y) : AIBoard.isAHit(x, y);
    }
    public boolean isGameReady() {
        return !playerShips.containsValue(false);
    }
    public String showBoard() {
        return playerBoard.showBoard();
    }
    public String getPlayerBoard() {
        return playerBoard.print();
    }
    public String getAIBoard() {
        return AIBoard.print();
    }
}
