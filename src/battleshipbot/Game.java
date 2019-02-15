package battleshipbot;

import java.io.IOException;
import java.io.FileWriter;

import net.dv8tion.jda.core.entities.Member;

public class Game {
    private Board playerBoard = new Board();
    private Board AIBoard = new Board();
    public Game(Member player) {
        newGame(player.getUser().getAsTag());
    }
    private void newGame(String usertag) {
        try (FileWriter writer = new FileWriter(usertag + ".txt", true)) {
            writer.write(usertag + "\n");
            writer.write(playerBoard.showBoard() + "\n\n");
            writer.write(AIBoard.showBoard());
        } catch(IOException e) {
        }
    }
    public String showBoard() {
        return playerBoard.showBoard();
    }
}
