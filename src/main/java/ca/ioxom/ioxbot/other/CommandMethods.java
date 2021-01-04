package ca.ioxom.ioxbot.other;

import ca.ioxom.ioxbot.frame.Main;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandMethods {
    protected static final String[] messages = {
            "shut up alex",
            "shut up and go find the lambo chevy with jams",
            "shut up alex you sodden tic tac",
            "shut down alex",
            "shut",
            "shut up already alex"
    };

    public static void bullyAlex(MessageReceivedEvent event) {
        //delete youtube
        Message message = event.getMessage();
        User author = event.getAuthor();
        if (message.getContentRaw().contains("https://www.youtu") || message.getContentRaw().contains("www.youtube.com")) {
            for (String id : Main.config.youtubeBlacklist) {
                if (id.equals(author.getId())) {
                    message.delete().queue();
                    event.getChannel().sendMessage("bad").queue();
                    Main.frame.logCommand(author, "very bad", true);
                }
            }
        }

        if (message.getContentRaw().equals(Main.config.formattedPrefix + "shut up alex")) {
            event.getChannel().sendMessage(messages[Main.random.nextInt(messages.length)]).queue();
            Main.frame.logCommand(author, "shut up alex", true);
        } else if (message.getContentRaw().equals("bo bo bo") && author.getId().equals("730866562189230110")) {
            message.delete().queue();
        }
    }
}
