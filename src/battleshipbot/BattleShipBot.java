package battleshipbot;

import java.io.FileReader;
import java.io.BufferedReader;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.Member;

public class BattleShipBot {
    public static void main(String[] args) throws Exception {
        String token = new BufferedReader(new FileReader("token.txt")).readLine(); /* This token file should only be accessed by 
                                                                                Carys Tryhorn. It is vitally important that nobody
                                                                                knows what the token is beyond the bot owner. */
        JDA battleBot = new JDABuilder(token).build(); // This creates a new bot and verifies it with discord using its token
        battleBot.addEventListener(new BattleListener()); // This adds an event listener that lets the program know what goes on in discord
    }
    public static void newGame() {
        
    }
}
