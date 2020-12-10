package ca.ioxom.ioxbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Main {
    public static final String VERSION = "0.1.1";
    public static IoxbotFrame frame;
    public static void main(String[] arguments) throws Exception {
        //create frame
        frame = new IoxbotFrame();
        frame.init();
        //configure
        Config.configure();
        //log in
        JDA api = JDABuilder.createDefault(Config.token).build();
        api.addEventListener(new Commands.Listener());
        frame.log("[init] initialized jda; ioxbot is ready to go");
    }
}
