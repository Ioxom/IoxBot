package ca.ioxom.ioxbot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands {
    public static class Listener extends ListenerAdapter {
        @Override
        public void onMessageReceived(MessageReceivedEvent event) {
            Message message = event.getMessage();
            if (event.getAuthor().getId().equals("382659726778957858") && message.getContentRaw().startsWith("https://www.youtu")) {
                message.delete().queue();
                event.getChannel().sendMessage("bad").queue();
            } else if (message.getContentRaw().equals("-ping")) {
                long start = System.nanoTime();
                event.getChannel().sendMessage("calculating ze ping").queue();
                long end = System.nanoTime();
                event.getChannel().deleteMessageById(event.getChannel().getLatestMessageId()).queue();
                event.getChannel().sendMessage("ze ping is: " + 10. * (end - start) / 1000000 + "ms").queue();
            }
        }
    }
}
