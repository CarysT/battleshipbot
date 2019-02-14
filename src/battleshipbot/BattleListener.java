package battleshipbot;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BattleListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        String message = event.getMessage().getContentStripped();
        // getContentDisplay() is a getter that strips discords formatting from the message
        Member member = event.getMember();
    	User user = member.getUser();
    	String username = user.getName();
        MessageChannel channel = event.getChannel(); // Finds which channel the message was sent to
        if (!channel.getName().equals("battleship-bot")) return;
        // For now only allow bot to send messages to a specific channel
        if (message.equals("!ng")) {
            channel.sendMessage("Starting a New Game!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
            BattleShipBot.newGame();
        }
        if (message.equals("!who")) {
            channel.sendMessage("You are: " + member.getNickname()).queue();
        }
    }
}
