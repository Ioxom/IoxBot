package ca.ioxom.ioxbot.stuff;

import ca.ioxom.ioxbot.Config;
import ca.ioxom.ioxbot.Main;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ExtraCommands {
    public static void bullyAlex(MessageReceivedEvent event) {
        if (event.getAuthor().getId().equals("382659726778957858") && (event.getMessage().getContentRaw().contains("https://www.youtu")
                || event.getMessage().getContentRaw().contains("www.youtube"))) {
            event.getMessage().delete().queue();
            event.getChannel().sendMessage("bad").queue();
            Main.frame.logCommand("very bad", event);
        } else if (event.getMessage().getContentRaw().equals(Config.prefix + "shut up alex")) {
            String[] messages = {
                    "shut up alex",
                    "shut up and go find the lambo chevy with jams",
                    "shut up alex you sodding tic tac",
                    "shut down alex"
            };
            event.getChannel().sendMessage(messages[Main.random.nextInt(messages.length)]).queue();
            Main.frame.logCommand("shut up alex", event);
        }
    }
}
