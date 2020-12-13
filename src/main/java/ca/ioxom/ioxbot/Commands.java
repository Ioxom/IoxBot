package ca.ioxom.ioxbot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import ca.ioxom.ioxbot.stuff.ExtraCommands;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.util.List;

public class Commands {
    public static class Listener extends ListenerAdapter {
        double ping;
        boolean checkingPing;
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            //cursed solution to fixing the ping command, don't do this
            if (checkingPing && event.getAuthor().getId().equals("722835290644807711")) {
                event.getChannel().editMessageById(event.getChannel().getLatestMessageId(),"ioxbot's ping is: " + ping + "ms").queue();
            } else if (checkingPing){
                RestAction<List<Message>> past = event.getChannel().getHistory().retrievePast(10);
                for (int i = 0; i < past.complete().size(); i ++) {
                    Message message = past.complete().get(i);
                    if (message.getAuthor().getId().equals("722835290644807711") && message.getContentRaw().equals("calculating ping...")) {
                        message.editMessage("ioxbot's ping is: " + ping + "ms").queue();
                    }
                }
            }
            checkingPing = false;
            ExtraCommands.bullyAlex(event);

            if (!event.getMessage().getContentRaw().startsWith(Config.prefix)) return;
            String messageContent = event.getMessage().getContentRaw().split(Config.prefix, 2)[1].toLowerCase().strip();
            switch (messageContent) {
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
                case "coinflip":
                    EmbedBuilder embed = new EmbedBuilder().setAuthor("ioxbot", "https://cdn.discordapp.com/attachments/728781398911221795/786384261821104158/ioxbot_profile_photo.png");
                    embed.setColor(new Color(0x00FF00));
                    embed.setTitle("You flipped a coin!");
                    boolean tails = Main.random.nextBoolean();
                    embed.setThumbnail(tails? "https://cdn.discordapp.com/attachments/728781398911221795/739249795469803612/coin_tails.jpg" : "https://cdn.discordapp.com/attachments/728781398911221795/739249818081296384/coin_heads.jpg");
                    embed.setDescription(tails? "your coin landed on tails!" : "your coin landed on heads!");
                    event.getChannel().sendMessage(embed.build()).queue();
                    break;
            }
        }
    }
}
