package com.battleshipbot.core;

import com.battleshipbot.game.Board;
import com.battleshipbot.game.Game;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.*;
import java.util.HashMap;

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
                Board playerBoard = new Board();
                Board AIBoard = new Board();
                String temp;
                for (int i = 0; i < playerBoard.getHeight();i++) {
                    temp = br.readLine();
                    for (int j = 0; j < playerBoard.getWidth(i);j++)
                        playerBoard.setPosition(i, j, String.valueOf(temp.charAt(j)));
                }
                for (int i = 0; i < AIBoard.getHeight();i++) {
                    temp = br.readLine();
                    for (int j = 0; j < AIBoard.getWidth(i);j++)
                        AIBoard.setPosition(i, j, String.valueOf(temp.charAt(j)));
                }
                games.put(names[x], new Game(playerBoard, AIBoard));
            } catch(IOException e) {
                return;
            }
        }
    }
    public static void saveGame() {
        games.forEach((userid,game) -> { try (FileWriter writer = new FileWriter("saves/" + userid + ".txt", false)) {
            writer.write(userid + "\n");
            writer.write(game.getPlayerBoard());
            writer.write(game.getAIBoard());
        } catch(IOException e) {
            System.out.println("Error in saving game!");
            System.out.println(e.getMessage());
        }
        });
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
        try {
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
            if ("!gr".equals(message)) {
                if (games.get(userid) != null) {
                    if (games.get(userid).isGameReady()) {
                        channel.sendMessage("You are ready to play").queue();
                    } else {
                        channel.sendMessage("You still have ships to place").queue();
                    }
                } else {
                    channel.sendMessage("You need to create a new game first!").queue();
                }
            }
            if (message.startsWith("!ps")) {
                String m = (games.get(userid).placeShip(message, true)) ? "Ship placed!" : "Invalid Ship placement";
                channel.sendMessage(m).queue();
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
                        "!battle - inside joke\n!ps - Allows you to place down your ships: [ship] [coords of front(1A)]" +
                        " [direction]\n!gr - checks to see if your game is ready to play```").queue();
            }
        } catch(Exception e) {
            System.out.println("Invalid command");
            channel.sendMessage("Invalid command").queue();
        }
    }
}
