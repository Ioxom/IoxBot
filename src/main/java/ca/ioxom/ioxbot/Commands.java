package ca.ioxom.ioxbot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import ca.ioxom.ioxbot.stuff.ExtraCommands;
import org.jetbrains.annotations.NotNull;

public class Commands {
    public static class Listener extends ListenerAdapter {
        long start, end;
        boolean checkingPing;
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            if (checkingPing) {
                event.getChannel().editMessageById(event.getChannel().getLatestMessageId(),"ioxbot's ping is: " + 10. * (end - start) / 1000000 + "ms" ).queue();
                checkingPing = false;
            }
            ExtraCommands.noYoutube(event);

            if (!event.getMessage().getContentRaw().startsWith("-")) return;
            String messageContent = event.getMessage().getContentRaw().split("-i ", 2)[1].toLowerCase();
            switch (messageContent.split(" ")[0]) {
                case "ping":
                    checkingPing = true;
                    start = System.nanoTime();
                    event.getChannel().sendMessage("calculating ping...").queue();
                    end = System.nanoTime();
                    Main.frame.logCommand("check ping", event);
                    break;
                case "help":
                    event.getChannel().sendMessage("help is under construction").queue();
                    Main.frame.logCommand("help", event);
                    break;
                case "gh":
                    event.getChannel().sendMessage("https://github.com/" + messageContent.split(" ")[1]).queue();
                    Main.frame.logCommand("gh", event);
                    break;
            }
        }
    }
}
