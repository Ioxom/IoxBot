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
import static ca.ioxom.ioxbot.Main.frame;

public class MainListener extends ListenerAdapter {

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
                    frame.logCommand(author, "checked ping", false);
                    break;
                case "help":
                    EmbedBuilder helpEmbed = new EmbedBuilder()
                            .setAuthor("ioxbot")
                            .setColor(config.getEmbedColour())
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
                            .addField("config", "use `" + config.prefix + "cfg help for help", false)
                            .setFooter("ioxbot, powered by ioxcorp™ technology");
                    channel.sendMessage(helpEmbed.build()).queue();
                    frame.logCommand(author, "help", true);
                    break;
                case "coinflip":
                    //we just generate a boolean to see which side
                    boolean tails = Main.random.nextBoolean();
                    EmbedBuilder coinflipEmbed = new EmbedBuilder()
                            .setAuthor("ioxbot")
                            .setColor(config.getEmbedColour())
                            .setTitle("You flipped a coin!")
                            .setThumbnail(tails ? "https://raw.githubusercontent.com/Ioxom/IoxBot/master/src/main/resources/images/coin_tails.jpg" : "https://raw.githubusercontent.com/Ioxom/IoxBot/master/src/main/resources/images/coin_heads.jpg")
                            .setDescription(tails ? "your coin landed on tails!" : "your coin landed on heads!");
                    channel.sendMessage(coinflipEmbed.build()).queue();
                    frame.logCommand(author, "flipped a coin", false);
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
                                .setColor(config.getEmbedColour())
                                .setImage("https://raw.githubusercontent.com/Ioxom/IoxBot/master/src/main/resources/images/belt.png")
                                .setDescription(belter + " gives the belt to " + belted);
                        channel.sendMessage(beltEmbed.build()).queue();
                        frame.logCommand(author, "gave someone the belt", false);
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
                        frame.logCommand(author, "linked to github", false);
                    } catch (Exception e) {
                        channel.sendMessage("incorrect usage of command!\nsyntax: `" + config.prefix + "gh <user> <repository>`").queue();
                    }
                    break;
                case "exit":
                    //ensure the user is specified as an admin in the config
                    if (config.admins.contains(author.getIdLong())) {
                        channel.sendMessage("ending ioxbot process").queue();
                        frame.logMain(author.getAsTag() + " killed process via command");
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            frame.throwError("exception while attempting to pause execution");
                        }
                        System.exit(2);
                    }
                    break;

                case "config":
                case "cfg":
                    switch (messageContent[1]) {
                        case "help":
                            EmbedBuilder cfgHelpEmbed = new EmbedBuilder()
                                    .setAuthor("ioxbot")
                                    .setColor(config.getEmbedColour())
                                    .addField("admins", "options: `add <user id>`, `remove <user id>`, `clear`", false)
                                    .addField("youtubeblacklist", "options: `add <user id>`, `remove <user id>`, `clear`", false)
                                    .addField("prefix", "options: `set <new prefix>`, `reset`", false)
                                    .addField("embedcolour", "options: `set <hex code>`, `reset`", false)
                                    .addField("randomembedcolour", "options: `set <boolean value>`, `reset`", false)
                                    .addField("extralogging", "options: `set <boolean value>`, `reset`", false)
                                    .addField("logcommands", "options: `set <boolean value>`, `reset`", false);
                            channel.sendMessage(cfgHelpEmbed.build()).queue();
                            frame.logCommand(author, "config help", true);
                            break;
                        case "admins":
                        case "admin":
                            if (config.admins.contains(author.getIdLong())) {
                                long idOfNewAdmin = Long.parseLong(messageContent[3]);
                                switch (messageContent[2]) {
                                    case "remove":
                                        config.removeAdmin(idOfNewAdmin);
                                        channel.sendMessage("removed \"" + idOfNewAdmin + "\" from admins").queue();
                                        config.writeCurrentConfig();
                                        break;
                                    case "add":
                                        config.addAdmin(idOfNewAdmin);
                                        channel.sendMessage("added \"" + idOfNewAdmin + "\" to admins").queue();
                                        config.writeCurrentConfig();
                                        break;
                                    case "clear":
                                        config.clearAdmins();
                                        channel.sendMessage("cleared admins").queue();
                                        config.writeCurrentConfig();
                                        break;
                                }
                            } else {
                                channel.sendMessage("insufficient permissions").queue();
                            }
                            break;
                        case "youtubeblacklist":
                        case "ytblacklist":
                            if (config.admins.contains(author.getIdLong())) {
                                long idOfBlacklistedUser = Long.parseLong(messageContent[3]);
                                switch (messageContent[2]) {
                                    case "remove":
                                        config.removeFromYoutubeBlacklist(idOfBlacklistedUser);
                                        channel.sendMessage("removed \"" + idOfBlacklistedUser + "\" from youtube blacklist").queue();
                                        config.writeCurrentConfig();
                                        break;
                                    case "add":
                                        config.addToYoutubeBlacklist(idOfBlacklistedUser);
                                        channel.sendMessage("added \"" + idOfBlacklistedUser + "\" to youtube blacklist").queue();
                                        config.writeCurrentConfig();
                                        break;
                                    case "clear":
                                        config.clearYoutubeBlacklist();
                                        channel.sendMessage("cleared youtube blacklist").queue();
                                        config.writeCurrentConfig();
                                        break;
                                }
                            } else {
                                channel.sendMessage("insufficient permissions").queue();
                            }
                            break;
                        case "prefix":
                            if (config.admins.contains(author.getIdLong())) {
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
                                    channel.sendMessage("set \"prefix\" to [" + prefix + "]").queue();
                                    config.writeCurrentConfig();
                                } else if (messageContent[2].equals("reset")) {
                                    config.setPrefix("-i ");
                                    channel.sendMessage("reset \"prefix\" to [-i ]").queue();
                                    config.writeCurrentConfig();
                                }
                            } else {
                                channel.sendMessage("insufficient permissions").queue();
                            }
                            break;
                        case "randomembedcolour":
                            if (config.admins.contains(author.getIdLong())) {
                                if (messageContent[2].equals("set")) {
                                    boolean b = messageContent[3].equals("true") || messageContent[3].equals("yes");
                                    config.setRandomEmbedColour(b);
                                    channel.sendMessage("set \"randomEmbedColour\" to " + b).queue();
                                    config.writeCurrentConfig();
                                } else if (messageContent[2].equals("reset")) {
                                    config.setRandomEmbedColour(true);
                                    channel.sendMessage("reset \"randomEmbedColour\" to true").queue();
                                    config.writeCurrentConfig();
                                }
                            } else {
                                channel.sendMessage("insufficient permissions").queue();
                            }
                            break;
                        case "embedcolour":
                            if (config.admins.contains(author.getIdLong())) {
                                if (messageContent[2].equals("set")) {
                                    config.setEmbedColour(messageContent[3]);
                                    channel.sendMessage("set \"embedColour\" to " + messageContent[3]).queue();
                                    config.writeCurrentConfig();
                                } else if (messageContent[2].equals("reset")) {
                                    config.setEmbedColour("00FF00");
                                    channel.sendMessage("reset \"embedColour\" to 00FF00 (light green)").queue();
                                    config.writeCurrentConfig();
                                }
                            } else {
                                channel.sendMessage("insufficient permissions").queue();
                            }
                            break;
                        case "extralogging":
                            if (config.admins.contains(author.getIdLong())) {
                                if (messageContent[2].equals("set")) {
                                    boolean b = messageContent[3].equals("true") || messageContent[3].equals("yes");
                                    config.setExtraLogging(b);
                                    channel.sendMessage("set \"extraLogging\" to " + b).queue();
                                    config.writeCurrentConfig();
                                } else if (messageContent[2].equals("reset")) {
                                    config.setExtraLogging(true);
                                    channel.sendMessage("reset \"extraLogging\" to true").queue();
                                    config.writeCurrentConfig();
                                }
                            } else {
                                channel.sendMessage("insufficient permissions").queue();
                            }
                            break;
                        case "logcommands":
                            if (config.admins.contains(author.getIdLong())) {
                                if (messageContent[2].equals("set")) {
                                    boolean b = messageContent[3].equals("true") || messageContent[3].equals("yes");
                                    config.setLogCommands(b);
                                    channel.sendMessage("set \"logCommands\" to " + b).queue();
                                    config.writeCurrentConfig();
                                } else if (messageContent[2].equals("reset")) {
                                    config.setLogCommands(true);
                                    channel.sendMessage("reset \"logCommands\" to true").queue();
                                    config.writeCurrentConfig();
                                }
                            } else {
                                channel.sendMessage("insufficient permissions").queue();
                            }
                            break;
                        case "print":
                        case "current":
                            EmbedBuilder currentConfigEmbed = new EmbedBuilder()
                                    .setAuthor("ioxbot")
                                    .setColor(config.getEmbedColour())
                                    .setDescription(config.toString())
                                    .setFooter("use " + config.prefix + "cfg help for help with config");
                            channel.sendMessage(currentConfigEmbed.build()).queue();
                            break;
                    }
            }
        }
    }
}