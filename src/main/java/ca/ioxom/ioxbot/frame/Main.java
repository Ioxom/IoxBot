package ca.ioxom.ioxbot.frame;

import ca.ioxom.ioxbot.commands.MainListener;
import ca.ioxom.ioxbot.other.Config;
import ca.ioxom.ioxbot.other.StatusSetter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.security.SecureRandom;

public class Main {
    public static Config config = new Config();
    public static final SecureRandom random = new SecureRandom();
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
            api.addEventListener(new MainListener());
            api.addEventListener(new StatusSetter());
            if (!config.extraLogging) {
                frame.logInit("initialized jda; ioxbot is ready to go", false);
            } else {
                frame.logInit("added event listeners; ioxbot is ready to go", false);
            }
        }
    }
}
