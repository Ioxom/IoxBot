package ca.ioxom.ioxbot.commands;

import ca.ioxom.ioxbot.frame.Main;
import ca.ioxom.ioxbot.other.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class MainListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        CommandMethods.bullyAlex(event);

        if (event.getMessage().getContentRaw().startsWith(Config.prefix)) {
            MessageChannel channel = event.getChannel();
            String messageContent = event.getMessage().getContentRaw().split(Config.prefix, 2)[1].toLowerCase().strip();
            switch (messageContent) {
                case "ping":
                    //code copied from the JDA discord, thanks minn
                    long time = System.currentTimeMillis();
                    channel.sendMessage("calculating ping...").queue( message ->
                            message.editMessageFormat("ioxbot's ping is: %dms", System.currentTimeMillis() - time).queue());
                    Main.frame.logCommand("check ping" , event.getAuthor());
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

    public static void yum(MessageReceivedEvent event) {
        if (event.getAuthor().getId().equals("675553099490000926") && Main.random.nextInt(100) == 50) {
            event.getChannel().sendMessage("yum").queue();
            Main.frame.logCommand("", event.getAuthor());
        }
    }
}
