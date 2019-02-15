package battleshipbot;

import java.util.HashMap;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BattleListener extends ListenerAdapter {
    HashMap<String, Game> games = new HashMap<>();
    public static void loadGames() {
        
    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        String message = event.getMessage().getContentStripped();
        // getContentDisplay() is a getter that strips discords formatting from the message
        Member member = event.getMember();
    	User user = member.getUser();
    	String usertag = user.getAsTag();
        MessageChannel channel = event.getChannel(); // Finds which channel the message was sent to
        if (!channel.getName().equals("battleship-bot")) return;
        // For now only allow bot to send messages to a specific channel
        if ("!ng".equals(message)) {
            channel.sendMessage("Starting a New Game!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
            games.put(usertag, new Game(member));
        }
        if ("!sb".equals(message)) {
            Game temp = games.get(usertag);
            channel.sendMessage(temp.showBoard()).queue();
        }
        if ("!who".equals(message)) {
            channel.sendMessage("You are: " + member.getNickname()).queue();
        }
        if ("!help".equals(message)) {
            channel.sendMessage("For now the available commands are:\n```" +
                    "!help - Shows all available commands\n!ng - Which does nothing\n" +
                    "!who - Which outputs your nickname\n!sb - Shows your game board```").queue();
        }
    }
}
