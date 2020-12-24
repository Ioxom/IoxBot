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
            switch (messageContent.split(" ")[0]) {
                case "ping":
                    //code copied from the JDA discord, don't credit me for this
                    long time = System.currentTimeMillis();
                    channel.sendMessage("calculating ping...").queue( message ->
                            message.editMessageFormat("ioxbot's ping is: %dms", System.currentTimeMillis() - time).queue());
                    Main.frame.logCommand(author, "checked ping", false);
                    break;
                case "help":
                    EmbedBuilder helpEmbed = new EmbedBuilder()
                            .setAuthor("ioxbot")
                            .setColor(new Color(0x00FF00))
                            .addField("ping", "checks the current ping in ms of ioxbot" +
                                    "\nsyntax: `" + Config.prefix + "ping`", false)
                            .addField("coinflip", "flips a coin" +
                                    "\nsyntax: `" + Config.prefix + "coinflip`", false)
                            .addField("belt", "sometimes you just need to give someone the belt" +
                                    "\nsyntax: `" + Config.prefix + "belt <@user>`", false)
                            .setFooter("also includes lots of diverse functions to bully alex with!");
                    channel.sendMessage(helpEmbed.build()).queue();
                    Main.frame.logCommand(author, "help", true);
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
                    Main.frame.logCommand(author, "flipped a coin", false);
                    break;
                case "belt":
                    String belter = author.getAsMention();
                    String belted = event.getMessage().getMentionedUsers().stream().findFirst().toString().split("Optional\\[")[1].split("]")[0];
                    if (belter.equals(belted)) {
                        channel.sendMessage(new EmbedBuilder().setDescription("no thanks mate").build()).queue();
                    } else {
                        EmbedBuilder beltEmbed = new EmbedBuilder()
                                .setAuthor("ioxbot")
                                .setColor(new Color(0x00FF00))
                                .setImage("https://cdn.discordapp.com/attachments/618926084750180363/791440175276228628/IMG_20200807_131425973.jpg")
                                .setDescription(belter + " gives the belt to " + belted);
                        channel.sendMessage(beltEmbed.build()).queue();
                        Main.frame.logCommand(author, "gave someone the belt", false);
                    }
                    break;
            }
        //yum
        } else {
            CommandMethods.yum(event);
        }
    }
}
