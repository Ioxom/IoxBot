package ca.ioxom.ioxbot.frame;

import ca.ioxom.ioxbot.listeners.MainListener;
import ca.ioxom.ioxbot.config.ConfigObject;
import ca.ioxom.ioxbot.listeners.StatusSetter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.util.Random;

public class Main {
    public static ConfigObject config = new ConfigObject();
    public static final Random random = new Random();
    public static final String VERSION = "0.2.0";
    public static IoxbotFrame frame;
    public static void main(String[] args) {
        //create frame
        frame = new IoxbotFrame();
        frame.init();
        //configure
        config.configure();
        //log in
        JDA api = null;
        try {
            api = JDABuilder.createDefault(config.token).build();
            Main.frame.logInit("successfully logged in JDA", true);
        } catch (LoginException e) {
            frame.throwError("invalid token", true);
        }
        //add event listeners
        if (api != null) {
            api.addEventListener(new MainListener(), new StatusSetter());
            if (!config.extraLogging) {
                frame.logInit("initialized jda; ioxbot is ready to go");
            } else {
                frame.logInit("added event listeners; ioxbot is ready to go");
            }
        }
    }
}
