package ca.ioxom.ioxbot.listeners;

import ca.ioxom.ioxbot.other.CommandMethods;
import ca.ioxom.ioxbot.frame.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

public class MainListener extends ListenerAdapter {

    public Color getRandomColour() {
        return new Color(Integer.parseUnsignedInt(new Object().toString().split("Object@")[1], 16));
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        CommandMethods.bullyAlex(event);

        if (!event.getMessage().getContentRaw().startsWith(Main.config.formattedPrefix)) return;
        User author = event.getAuthor();
        MessageChannel channel = event.getChannel();
        String[] messageContent = event.getMessage().getContentRaw().split(Main.config.formattedPrefix, 2)[1].toLowerCase().strip().split(" ");
        switch (messageContent[0]) {
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
                        .setColor(getRandomColour())
                        .addField("ping", "checks the current ping in ms of ioxbot" +
                                "\nsyntax: `" + Main.config.formattedPrefix + "ping`", false)
                        .addField("coinflip", "flips a coin" +
                                "\nsyntax: `" + Main.config.formattedPrefix + "coinflip`", false)
                        .addField("belt", "sometimes you just need to give someone the belt" +
                                "\nsyntax: `" + Main.config.formattedPrefix + "belt <@user>`", false)
                        .addField("github", "gives a github link to the specified repository" +
                                "\nsyntax: `" + Main.config.formattedPrefix + "gh <user> <repository name>`", false)
                        .setFooter("ioxbot, powered by ioxcorp™ technology");
                channel.sendMessage(helpEmbed.build()).queue();
                Main.frame.logCommand(author, "help", true);
                break;
            case "coinflip":
                //we just generate a boolean to see which side
                boolean tails = Main.random.nextBoolean();
                EmbedBuilder coinflipEmbed = new EmbedBuilder()
                        .setAuthor("ioxbot")
                        .setColor(Main.config.embedColour)
                        .setTitle("You flipped a coin!")
                        .setThumbnail(tails? "https://raw.githubusercontent.com/Ioxom/IoxBot/master/src/main/resources/images/coin_tails.jpg" : "https://raw.githubusercontent.com/Ioxom/IoxBot/master/src/main/resources/images/coin_heads.jpg")
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
                            .setColor(Main.config.embedColour)
                            .setImage("https://raw.githubusercontent.com/Ioxom/IoxBot/master/src/main/resources/images/ioxbot_profile_photo.png")
                            .setDescription(belter + " gives the belt to " + belted);
                    channel.sendMessage(beltEmbed.build()).queue();
                    Main.frame.logCommand(author, "gave someone the belt", false);
                }
                break;
            case "gh":
            case "github":
                try {
                    channel.sendMessage("https://github.com/" + messageContent[1] + "/" + messageContent[2]).queue();
                    Main.frame.logCommand(author, "linked to github", false);
                } catch (Exception e) {
                    channel.sendMessage("incorrect usage of command!\nsyntax: `" + Main.config.formattedPrefix + "gh <user> <repository>`").queue();
                }
                break;
        }
    }
}
