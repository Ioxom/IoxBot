package ca.ioxom.ioxbot.commands;

import ca.ioxom.ioxbot.frame.Main;
import ca.ioxom.ioxbot.other.Config;
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
        Message message = event.getMessage();
        User author = event.getAuthor();
        if ((author.getId().equals("382659726778957858") || author.getId().equals("730866562189230110")) && (message.getContentRaw().contains("https://www.youtu")
                || message.getContentRaw().contains("www.youtube"))) {
            message.delete().queue();
            event.getChannel().sendMessage("bad").queue();
            Main.frame.logCommand(author, "very bad", true);
        } else if (message.getContentRaw().equals(Config.prefix + "shut up alex")) {
            event.getChannel().sendMessage(messages[Main.random.nextInt(messages.length)]).queue();
            Main.frame.logCommand(author, "shut up alex", true);
        } else if (message.getContentRaw().equals("bo bo bo") && author.getId().equals("730866562189230110")) {
            message.delete().queue();
        }
    }

    public static void yum(MessageReceivedEvent event) {
        if (event.getAuthor().getId().equals("675553099490000926") && Main.random.nextInt(100) == 50) {
            event.getChannel().sendMessage("yum").queue();
            Main.frame.logCommand(event.getAuthor(), "yum", true);
        }
    }
}
