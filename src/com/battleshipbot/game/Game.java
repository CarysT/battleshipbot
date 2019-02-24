package com.battleshipbot.game;

import net.dv8tion.jda.core.entities.Member;

import java.io.FileWriter;
import java.io.IOException;

public class Game {
    private Board playerBoard = new Board();
    private final Ship[] availableShips = {new Ship(5, "Carrier"), new Ship(4, "Battleship"),
            new Ship(3, "Cruiser"), new Ship(3, "Submarine"), new Ship(2, "Destroyer")};
    private Board AIBoard = new Board();
    public Game(Member player) {
        newGame(player.getUser().getId());
    }
    public Game(Board pb, Board ab) {
        playerBoard = pb;
        AIBoard = ab;
    }
    private void newGame(String userid) {
        try (FileWriter writer = new FileWriter("saves/" + userid + ".txt", true)) {
            writer.write(userid + "\n");
            writer.write(playerBoard.print());
            writer.write(AIBoard.print());
        } catch(IOException e) {
            System.out.println("Error in creating a new game!");
            System.out.println(e.getMessage());
        }
    }
    public boolean placeShip(String command, boolean isPlayer) {
        command = command.replace(command.substring(0,command.indexOf(" ")+1), "");
        String name = command.substring(0, command.indexOf(" "));
        int length = 0;
        for (Ship s : availableShips)
            if (s.getType().equals(name))
                length = s.getLength();
        command = command.replace(command.substring(0,command.indexOf(" ")+1), "");
        int coordy = Integer.parseInt(command.substring(0,1));
        int coordx = command.charAt(1)-65;
        command = command.replace(command.substring(0,command.indexOf(" ")+1), "");
        String direction = command;
        if (isPlayer && ("north".equalsIgnoreCase(direction) || "south".equalsIgnoreCase(direction) || "east".equalsIgnoreCase(direction) || "west".equalsIgnoreCase(direction))) {
            try {
                Board copy = playerBoard;
                if ("north".equalsIgnoreCase(direction)) {
                    for (int i = 0; i < length; i++) {
                        copy.setPosition(coordx, coordy, "2");
                        coordx += 1 * -1;
                    }
                }
                else if ("east".equalsIgnoreCase(direction)) {
                    for (int i = 0; i < length; i++) {
                        copy.setPosition(coordx, coordy, "2");
                        coordy += 1;
                    }
                }
                else if ("south".equalsIgnoreCase(direction)) {
                    for (int i = 0; i < length; i++) {
                        copy.setPosition(coordx, coordy, "2");
                        coordx += 1;
                    }
                }
                else if ("west".equalsIgnoreCase(direction)) {
                    for (int i = 0; i < length; i++) {
                        copy.setPosition(coordx, coordy, "2");
                        coordy += 1 * -1;
                    }
                }
                else {
                    return false;
                }
                playerBoard = copy;
                return true;
            } catch(Exception e) {
                System.out.println("Error placing ship: " + e.getMessage());
                return false;
            }
        }
        return false;
    }
    public boolean shootAt(int x, int y, boolean isPlayer) {
        return(isPlayer) ? playerBoard.isAHit(x, y) : AIBoard.isAHit(x, y);
    }
    public String showBoard() {
        return playerBoard.showBoard();
    }
}
