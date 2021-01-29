package ca.ioxom.ioxbot;

import ca.ioxom.ioxbot.config.ConfigObject;
import ca.ioxom.ioxbot.frame.IoxbotFrame;
import ca.ioxom.ioxbot.listeners.MainListener;
import ca.ioxom.ioxbot.listeners.StatusSetter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Random;

public class Main {
    public static final ConfigObject config = new ConfigObject();
    public static final Random random = new Random();
    //get version
    public static String VERSION;
    static {
        try {
            Properties properties = new Properties();
            String fileName = "ioxbot.properties";
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("ioxbot.properties");

            //load input stream
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + fileName + "' not found in the classpath");
            }

            VERSION = properties.getProperty("version");
            properties.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static IoxbotFrame frame;
    public static void main(String[] args) {
        //create frame
        frame = new IoxbotFrame();
        frame.init();
        //configure
        config.configure();
        //throw error if version is not found
        if (VERSION == null) {
            VERSION = "0.0.0";
            frame.throwError("could not get version; defaulting to 0.0.0");
        }
        //log in
        JDA api = null;
        try {
            String token = "";
            try {
                token = Files.readString(Paths.get("token.txt"));
            } catch (FileNotFoundException e) {
                frame.throwError("token.txt not found", true);
            } catch (IOException e) {
                frame.throwError("an IOException occurred when reading file: token.txt", true);
            }
            api = JDABuilder.createDefault(token).build();
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
        } else {
            frame.throwError("failed to create JDA object", true);
        }
    }
}
