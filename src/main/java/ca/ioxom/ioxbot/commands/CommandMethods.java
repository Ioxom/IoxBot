package ca.ioxom.ioxbot.commands;

import ca.ioxom.ioxbot.frame.Main;
import ca.ioxom.ioxbot.other.Config;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandMethods {
    public static void bullyAlex(MessageReceivedEvent event) {
        if ((event.getAuthor().getId().equals("382659726778957858") || event.getAuthor().getId().equals("730866562189230110")) && (event.getMessage().getContentRaw().contains("https://www.youtu")
                || event.getMessage().getContentRaw().contains("www.youtube"))) {
            event.getMessage().delete().queue();
            event.getChannel().sendMessage("bad").queue();
            Main.frame.logCommand("very bad", event.getAuthor());
        } else if (event.getMessage().getContentRaw().equals(Config.prefix + "shut up alex")) {
            String[] messages = {
                    "shut up alex",
                    "shut up and go find the lambo chevy with jams",
                    "shut up alex you sodden tic tac",
                    "shut down alex"
            };
            event.getChannel().sendMessage(messages[Main.random.nextInt(messages.length)]).queue();
            Main.frame.logCommand("shut up alex", event.getAuthor());
        }
    }
}
