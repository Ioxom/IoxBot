package ca.ioxom.ioxbot.commands;

import ca.ioxom.ioxbot.frame.Main;
import ca.ioxom.ioxbot.other.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

public class MainListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        CommandMethods.bullyAlex(event);

        if (event.getMessage().getContentRaw().startsWith(Config.prefix)) {
            User author = event.getAuthor();
            MessageChannel channel = event.getChannel();
            String messageContent = event.getMessage().getContentRaw().split(Config.prefix, 2)[1].toLowerCase().strip();
            switch (messageContent) {
                case "ping":
                    //code copied from the JDA discord, don't credit me for this
                    long time = System.currentTimeMillis();
                    channel.sendMessage("calculating ping...").queue( message ->
                            message.editMessageFormat("ioxbot's ping is: %dms", System.currentTimeMillis() - time).queue());
                    Main.frame.logCommand(author, "checked", "ping");
                    break;
                case "help":
                    EmbedBuilder helpEmbed = new EmbedBuilder()
                            .setAuthor("ioxbot")
                            .setColor(new Color(0x00FF00))
                            .addField("current prefix:", Config.prefix, false)
                            .addField("ping", "checks the current ping in ms of ioxbot", false)
                            .addField("coinflip", "flips a coin", false)
                            .setFooter("also includes lots of diverse functions to bully Alex with!");
                    channel.sendMessage(helpEmbed.build()).queue();
                    Main.frame.logCommand(author, "help");
                    break;
                case "coinflip":
                    //we just generate a boolean to see which side
                    boolean tails = Main.random.nextBoolean();
                    EmbedBuilder coinflipEmbed = new EmbedBuilder()
                            .setAuthor("ioxbot")
                            .setColor(new Color(0x00FF00))
                            .setTitle("You flipped a coin!")
                            .setThumbnail(tails? "https://user-images.githubusercontent.com/66223394/102388590-978ace00-3f97-11eb-8b79-74c8b123f264.jpg" : "https://user-images.githubusercontent.com/66223394/102388542-8a6ddf00-3f97-11eb-9f15-60d852a2bc2b.jpg")
                            .setDescription(tails? "your coin landed on tails!" : "your coin landed on heads!");
                    channel.sendMessage(coinflipEmbed.build()).queue();
                    Main.frame.logCommand(author, "flipped", "a coin");
                    break;
            }
        //yum
        } else {
            CommandMethods.yum(event);
        }
    }
}
