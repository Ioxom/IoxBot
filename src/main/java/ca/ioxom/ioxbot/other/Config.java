package ca.ioxom.ioxbot.other;

import ca.ioxom.ioxbot.frame.Main;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class Config {
    public static boolean extraLogging;
    public static String token;
    public static boolean logCommands;
    public static String prefix;
    private static final HashMap<String, String> configs = new HashMap<>();
    public static void configure() {
        try (Scanner scanner = new Scanner(Paths.get("config.txt"))) {
            //save config to a hashmap, ignoring lines starting with //
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("//") || line.isEmpty()) continue;
                //throw error if a line is entered incorrectly
                try  {
                    String[] splitLine = line.split(" = ");
                    configs.put(splitLine[0], splitLine[1]);
                } catch (Exception e) {
                    Main.frame.throwError("error reading line \"" + line + "\" of config", true);
                }
            }
        //throw error config.txt is not found
        } catch (IOException e) {
            Main.frame.throwError("could not find config.txt in the target directory", true);
        }
        //set all the values to public variables
        setValues();
        Main.frame.logInit("successfully read configuration file", false);
    }

    public static void setValues() {
        try {
            token = configs.get("token");
        } catch (Exception e) {
            Main.frame.throwError("error reading line \"token\" of config", true);
        }
        try {
            prefix = configs.get("prefix") + (configs.get("spaceAfterPrefix").equals("true")? " " : "");
        } catch (Exception e) {
            Main.frame.throwError("error reading line \"prefix\" or \"spaceAfterPrefix\" of config", true);
        }
        try {
            logCommands = configs.get("logCommands").equals("true");
        } catch (Exception e) {
            Main.frame.throwError("error reading line \"logCommands\" of config", true);
        }
        try {
            extraLogging = configs.get("extraLogging").equals("true");
        } catch (Exception e) {
            Main.frame.throwError("error reading line \"extraLogging\" of config", true);
        }
    }
}
