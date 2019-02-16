package battleshipbot;

import java.io.IOException;
import java.io.FileWriter;

import net.dv8tion.jda.core.entities.Member;

public class Game {
    private Board playerBoard = new Board();
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
            writer.write(userid + "\n".getBytes());
            writer.write(playerBoard.showBoard() + "\n\n".getBytes());
            writer.write(AIBoard.showBoard());
            writer.close();
        } catch(IOException e) {
            return;
        }
    }
    public String showBoard() {
        return playerBoard.showBoard();
    }
}
