package ca.ioxom.ioxbot.listeners;

import ca.ioxom.ioxbot.Main;
import ca.ioxom.ioxbot.other.CommandMethods;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import static ca.ioxom.ioxbot.Main.config;

import java.awt.Color;

public class MainListener extends ListenerAdapter {

    public Color getEmbedColour() {
        if (config.randomEmbedColour) {
            return new Color(Integer.parseUnsignedInt(new Object().toString().split("Object@")[1], 16));
        } else {
            return new Color(Integer.parseUnsignedInt(config.embedColour, 16));
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        CommandMethods.bullyAlex(event);

        User author = event.getAuthor();
        if (event.getMessage().getContentRaw().startsWith(config.prefix) && !author.isBot()) {
            MessageChannel channel = event.getChannel();
            String[] messageContent = event.getMessage().getContentRaw().split(config.prefix, 2)[1].toLowerCase().trim().split(" ");
            switch (messageContent[0]) {
                case "ping":
                    //code copied from the JDA discord, don't credit me for this
                    long time = System.currentTimeMillis();
                    channel.sendMessage("calculating ping...").queue(message ->
                            message.editMessageFormat("ioxbot's ping is: %dms", System.currentTimeMillis() - time).queue());
                    Main.frame.logCommand(author, "checked ping", false);
                    break;
                case "help":
                    EmbedBuilder helpEmbed = new EmbedBuilder()
                            .setAuthor("ioxbot")
                            .setColor(getEmbedColour())
                            .addField(
                                    "ping", "checks the current ping in ms of ioxbot" +
                                            "\nsyntax: `" + config.prefix + "ping`",
                                    false)
                            .addField(
                                    "coinflip", "flips a coin" +
                                            "\nsyntax: `" + config.prefix + "coinflip`",
                                    false)
                            .addField(
                                    "belt", "sometimes you just need to give someone the belt" +
                                            "\nsyntax: `" + config.prefix + "belt <@user>`",
                                    false)
                            .addField(
                                    "github", "gives a github link to the specified repository" +
                                            "\nsyntax: `" + config.prefix + "gh <user> <repository name>`",
                                    false)
                            .addField(
                                    "exit", "*only usable by admins*: kills all of ioxbot's processes" +
                                            "\nsyntax: `" + config.prefix + "exit`",
                                    false)
                            .setFooter("ioxbot, powered by ioxcorp™ technology");
                    channel.sendMessage(helpEmbed.build()).queue();
                    Main.frame.logCommand(author, "help", true);
                    break;
                case "coinflip":
                    //we just generate a boolean to see which side
                    boolean tails = Main.random.nextBoolean();
                    EmbedBuilder coinflipEmbed = new EmbedBuilder()
                            .setAuthor("ioxbot")
                            .setColor(getEmbedColour())
                            .setTitle("You flipped a coin!")
                            .setThumbnail(tails ? "https://raw.githubusercontent.com/Ioxom/IoxBot/master/src/main/resources/images/coin_tails.jpg" : "https://raw.githubusercontent.com/Ioxom/IoxBot/master/src/main/resources/images/coin_heads.jpg")
                            .setDescription(tails ? "your coin landed on tails!" : "your coin landed on heads!");
                    channel.sendMessage(coinflipEmbed.build()).queue();
                    Main.frame.logCommand(author, "flipped a coin", false);
                    break;
                case "belt":
                    String belter = author.getAsMention();
                    //mess to get mentioned user in the same format as getAsMention()
                    String belted = event.getMessage().getMentionedUsers().stream().findFirst().toString().split("Optional\\[")[1].split("]")[0];
                    if (belter.equals(belted)) {
                        channel.sendMessage(new EmbedBuilder().setDescription("no thanks mate").build()).queue();
                    } else {
                        EmbedBuilder beltEmbed = new EmbedBuilder()
                                .setAuthor("ioxbot")
                                .setColor(getEmbedColour())
                                .setImage("https://raw.githubusercontent.com/Ioxom/IoxBot/master/src/main/resources/images/belt.png")
                                .setDescription(belter + " gives the belt to " + belted);
                        channel.sendMessage(beltEmbed.build()).queue();
                        Main.frame.logCommand(author, "gave someone the belt", false);
                    }
                    break;
                case "gh":
                case "github":
                    //try/catch to check if all arguments are present
                    try {
                        channel.sendMessage("https://github.com/" + messageContent[1] + "/" + messageContent[2]).queue(message -> {
                            //remove embeds if the argument is present
                            if (messageContent[3].equals("-noembed") || messageContent[3].equals("-n"))
                                message.suppressEmbeds(true).queue();
                        });
                        Main.frame.logCommand(author, "linked to github", false);
                    } catch (Exception e) {
                        channel.sendMessage("incorrect usage of command!\nsyntax: `" + config.prefix + "gh <user> <repository>`").queue();
                    }
                    break;
                case "exit":
                    //ensure the user is specified as an admin in the config
                    for (long id : config.admins) {
                        if (author.getIdLong() == id) {
                            channel.sendMessage("ending ioxbot process").queue();
                            Main.frame.logMain(author.getAsTag() + " killed process via command");
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                Main.frame.throwError("exception while attempting to pause execution");
                            }
                            System.exit(2);
                        }
                    }
                    break;

                case "config":
                case "cfg":
                    if (config.admins.contains(author.getIdLong())) {
                        switch (messageContent[1]) {
                            case "admins":
                            case "admin":
                                long idOfNewAdmin = Long.parseLong(messageContent[3]);
                                if (messageContent[2].equals("remove")) {
                                    config.removeAdmin(idOfNewAdmin);
                                } else if (messageContent[2].equals("add")) {
                                    config.addAdmin(idOfNewAdmin);
                                }
                                break;
                            case "youtubeblacklist":
                            case "ytblacklist":
                                long idOfBlacklistedUser = Long.parseLong(messageContent[3]);
                                if (messageContent[2].equals("remove")) {
                                    config.removeFromYoutubeBlacklist(idOfBlacklistedUser);
                                } else if (messageContent[2].equals("add")) {
                                    config.addToYoutubeBlacklist(idOfBlacklistedUser);
                                }
                                break;
                            case "prefix":
                                if (messageContent[2].equals("set")) {
                                    String prefix = config.prefix;
                                    //cursed: get the prefix
                                    //we can't get it from messageContent because all spaces are removed
                                    if (messageContent[0].equals("config")) {
                                        prefix = event.getMessage().getContentRaw().split(config.prefix + "config prefix set ")[0];
                                    } else if (messageContent[0].equals("cfg")) {
                                        prefix = event.getMessage().getContentRaw().split(config.prefix + "cfg prefix set ")[0];
                                    }
                                    config.setPrefix(prefix);
                                }
                                break;
                            case "randomembedcolour":
                                if (messageContent[2].equals("set")) {
                                    boolean b = false;
                                    if (messageContent[3].equals("true") || messageContent[3].equals("yes")) b = true;
                                    config.setRandomEmbedColour(b);
                                }
                                break;
                            case "embedcolour":
                                if (messageContent[2].equals("set")) {
                                    config.setEmbedColour(messageContent[3]);
                                }
                                break;
                        }
                        config.writeCurrentConfig();
                    } else {
                        channel.sendMessage("insufficient permissions").queue();
                    }
                    break;
            }
        }
    }
}