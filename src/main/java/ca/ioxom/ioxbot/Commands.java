package ca.ioxom.ioxbot;

import ca.ioxom.ioxbot.stuff.ExtraCommands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Commands {
    public static class Listener extends ListenerAdapter {
        double ping;
        boolean checkingPing;
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            //cursed solution to fixing the ping command, don't do this
            if (checkingPing && event.getAuthor().getId().equals("722835290644807711") && event.getMessage().getContentRaw().equals("calculating ping...")) {
                event.getChannel().editMessageById(event.getChannel().getLatestMessageId(),"ioxbot's ping is: " + ping + "ms").queue();
                checkingPing = false;
            }
            ExtraCommands.bullyAlex(event);

            if (event.getMessage().getContentRaw().startsWith(Config.prefix)) {
                MessageChannel channel = event.getChannel();
                String messageContent = event.getMessage().getContentRaw().split(Config.prefix, 2)[1].toLowerCase().strip();
                switch (messageContent) {
                    case "ping":
                        checkingPing = true;
                        long start = System.nanoTime();
                        channel.sendMessage("calculating ping...").queue();
                        long end = System.nanoTime();
                        //this has weird inaccuracies and I'm not sure why
                        ping = (double) (end -  start) / 1000000;
                        if (ping < 10) ping *= 10;
                        Main.frame.logCommand("check ping [" + ping + " ms]" , event.getAuthor());
                        break;
                    case "help":
                        EmbedBuilder helpEmbed = new EmbedBuilder().setAuthor("ioxbot");
                        helpEmbed.setColor(new Color(0x00FF00));
                        helpEmbed.addField("ping", "checks the current ping in ms of ioxbot", false);
                        helpEmbed.addField("coinflip", "flips a coin", false);
                        helpEmbed.setFooter("also includes lots of diverse functions to bully Alex with!");
                        channel.sendMessage(helpEmbed.build()).queue();
                        Main.frame.logCommand("help", event.getAuthor());
                        break;
                    case "coinflip":
                        //we just generate a boolean to see which side
                        EmbedBuilder coinflipEmbed = new EmbedBuilder().setAuthor("ioxbot");
                        coinflipEmbed.setColor(new Color(0x00FF00));
                        coinflipEmbed.setTitle("You flipped a coin!");
                        boolean tails = Main.random.nextBoolean();
                        coinflipEmbed.setThumbnail(tails? "https://cdn.discordapp.com/attachments/728781398911221795/739249795469803612/coin_tails.jpg" : "https://cdn.discordapp.com/attachments/728781398911221795/739249818081296384/coin_heads.jpg");
                        coinflipEmbed.setDescription(tails? "your coin landed on tails!" : "your coin landed on heads!");
                        channel.sendMessage(coinflipEmbed.build()).queue();
                        break;
                }
            //yum
            } else {
                yum(event);
            }
        }
    }

    public static void yum(MessageReceivedEvent event) {
        if (event.getAuthor().getId().equals("675553099490000926") && Main.random.nextInt(100) == 50) {
            event.getChannel().sendMessage("yum").queue();
        }
    }
}
