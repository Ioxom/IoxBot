package ca.ioxom.ioxbot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import ca.ioxom.ioxbot.stuff.ExtraCommands;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Commands {
    public static class Listener extends ListenerAdapter {
        double ping;
        boolean checkingPing;
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            //cursed solution to fixing the ping command, don't do this
            if (checkingPing) {
                if (event.getAuthor().getId().equals("722835290644807711")) {
                    event.getChannel().editMessageById(event.getChannel().getLatestMessageId(),"ioxbot's ping is: " + ping + "ms").queue();
                } else {
                    RestAction<List<Message>> past = event.getChannel().getHistory().retrievePast(10);
                    for (int i = 0; i < past.complete().size(); i ++) {
                        Message message = past.complete().get(i);
                        if (message.getAuthor().getId().equals("722835290644807711") && message.getContentRaw().equals("calculating ping...")) {
                            message.editMessage("ioxbot's ping is: " + ping + "ms").queue();
                        }
                    }
                }
            }
            checkingPing = false;
            ExtraCommands.noYoutube(event);

            if (!event.getMessage().getContentRaw().startsWith("-i ")) return;
            String messageContent = event.getMessage().getContentRaw().split("-i ", 2)[1].toLowerCase();
            switch (messageContent.split(" ")[0]) {
                case "ping":
                    checkingPing = true;
                    long start = System.nanoTime();
                    event.getChannel().sendMessage("calculating ping...").queue();
                    long end = System.nanoTime();
                    ping = (double) (end - start) / 100000;
                    Main.frame.logCommand("check ping [" + ping + " ms]" , event);
                    break;
                case "help":
                    event.getChannel().sendMessage("help is under construction\nwhile you're waiting use the recently fixed -i ping").queue();
                    Main.frame.logCommand("help", event);
                    break;
                case "gh":
                    event.getChannel().sendMessage("https://github.com/" + messageContent.split(" ")[1]).queue();
                    Main.frame.logCommand("gh", event);
                    break;
                default:
                    Main.frame.log("[err] nonexistent command");
            }
        }
    }
}
