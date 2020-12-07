package ca.ioxom.ioxbot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import ca.ioxom.ioxbot.stuff.ExtraCommands;
import org.jetbrains.annotations.NotNull;

public class Commands {
    public static class Listener extends ListenerAdapter {
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            ExtraCommands.noYoutube(event);

            if (!event.getMessage().getContentRaw().startsWith("-")) return;
            String messageContent = event.getMessage().getContentRaw().split("-", 2)[1].toLowerCase();
            switch (messageContent) {
                case "ping":
                    long start = System.nanoTime();
                    event.getChannel().sendMessage("calculating ping...").queue();
                    long end = System.nanoTime();
                    event.getChannel().sendMessage("ioxbot's ping is: " + 10. * (end - start) / 1000000 + "ms").queue();
                    Main.frame.logCommand("check ping", event);
                    break;
                case "help":
                    event.getChannel().sendMessage("help is under construction").queue();
                    Main.frame.logCommand("help", event);
                    break;
            }
        }
    }
}
