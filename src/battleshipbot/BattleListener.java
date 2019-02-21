package battleshipbot;

import java.util.HashMap;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

class BattleListener extends ListenerAdapter {
    private static HashMap<String, Game> games = new HashMap<>();
    public static void loadGames() {
        File dir = new File("saves/");
        
        File[] loads = dir.listFiles((File dir1, String filename) -> filename.endsWith(".txt"));
        if (loads == null) return;
        String[] names = new String[loads.length];
        
        for (int x = 0; x < loads.length; x++) {
            try(BufferedReader br = new BufferedReader(new FileReader(loads[x]))) {
                names[x] = br.readLine();
                for (String name : names) {
                    Board playerBoard = new Board();
                    Board AIBoard = new Board();
                    String temp;
                    for (int i = 0; i < playerBoard.getHeight();i++) {
                        temp = br.readLine();
                        for (int j = 0; j < playerBoard.getWidth(i);j++) {
                            playerBoard.setPosition(i,j,String.valueOf(temp.charAt(j)));
                        }
                    }
                    for (int i = 0; i < AIBoard.getHeight();i++) {
                        temp = br.readLine();
                        for (int j = 0; j < AIBoard.getWidth(i);j++) {
                            AIBoard.setPosition(i,j,String.valueOf(temp.charAt(j)));
                        }
                    }
                    games.put(name, new Game(playerBoard, AIBoard));
                    br.close();
                }
            } catch(IOException e) {
                return;
            }
        }
    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        String message = event.getMessage().getContentStripped();
        Member member = event.getMember();
    	User user = member.getUser();
    	String userid = user.getId();
        MessageChannel channel = event.getChannel();
        if (!channel.getName().equals("battleship-bot")) return;
        if ("!ng".equals(message)) {
            channel.sendMessage("Starting a New Game!").queue();
            games.put(userid, new Game(member));
        }
        if ("!sb".equals(message)) {
            if (games.get(userid) != null) {
                channel.sendMessage(games.get(userid).showBoard()).queue();
            } else {
                channel.sendMessage("You have no board to show!").queue();
            }
        }
        if ("!who".equals(message)) {
            channel.sendMessage("You are: " + user.getName()).queue();
        }
        if ("!battle".equals(message)) {
            channel.sendMessage("To the death!").queue();
        }
        if ("!help".equals(message)) {
            channel.sendMessage("For now the available commands are:\n```" +
                    "!help - Shows all available commands\n!ng - Starts A New Game\n" +
                    "!who - Which outputs your nickname\n!sb - Shows your game board\n" +
                    "!battle - inside joke```").queue();
        }
    }
}
