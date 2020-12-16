package ca.ioxom.ioxbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.security.SecureRandom;

public class Main {
    public static final SecureRandom random = new SecureRandom();
    public static final String VERSION = "0.1.1";
    public static IoxbotFrame frame;
    public static void main(String[] args) {
        //create frame
        frame = new IoxbotFrame();
        frame.init();
        //configure
        Config.configure();
        //log in
        JDA api = null;
        try {
            api = JDABuilder.createDefault(Config.token).build();
        } catch (LoginException e) {
            frame.throwError("invalid token", true);
        }
        if (api != null) {
            api.addEventListener(new Commands.Listener());
            api.addEventListener(new StatusSetter());
            frame.logInit("initialized jda; ioxbot is ready to go");
        }
    }
}
