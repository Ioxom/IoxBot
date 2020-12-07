package ca.ioxom.ioxbot.stuff;

import ca.ioxom.ioxbot.Main;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ExtraCommands {
    public static void noYoutube(MessageReceivedEvent event) {
        if (event.getAuthor().getId().equals("382659726778957858") && event.getMessage().getContentRaw().startsWith("https://www.youtu")) {
            event.getMessage().delete().queue();
            event.getChannel().sendMessage("bad").queue();
            Main.frame.log("forg used very bad");
        }
    }
}
