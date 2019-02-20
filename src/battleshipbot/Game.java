package battleshipbot;

import java.io.IOException;
import java.io.FileWriter;

import net.dv8tion.jda.core.entities.Member;

public class Game {
    private Board playerBoard = new Board();
    private Ship[] playerShips = {new Ship(5, "Carrier"), new Ship(4, "Battleship"), new Ship(3, "Cruiser"),
    new Ship(3, "Submarine"), new Ship(2, "Destroyer")};
    private Board AIBoard = new Board();
    private Ship[] AIShips = {new Ship(5, "Carrier"), new Ship(4, "Battleship"), new Ship(3, "Cruiser"),
    new Ship(3, "Submarine"), new Ship(2, "Destroyer")};
    public Game(Member player) {
        newGame(player.getUser().getId());
    }
    public Game(Board pb, Board ab) {
        playerBoard = pb;
        AIBoard = ab;
    }
    private void newGame(String userid) {
        try (FileWriter writer = new FileWriter("saves/" + userid + ".txt", true)) {
            writer.write(userid + "\n".getBytes());
            writer.write(playerBoard.showBoard() + "\n\n".getBytes());
            writer.write(AIBoard.showBoard());
            writer.close();
        } catch(IOException e) {
        }
    }
    public void placeShip(String name, int location, int direction) {
        
    }
    public boolean shootAt(int x, int y) {
        return(AIBoard.isAHit(x, y));
    }
    public String showBoard() {
        return playerBoard.showBoard();
    }
}
