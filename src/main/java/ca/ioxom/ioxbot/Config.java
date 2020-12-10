package ca.ioxom.ioxbot;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class Config {
    public static String token;
    public static boolean logCommands;
    public static String prefix;
    public static void configure() {
        HashMap<String, String> configs = new HashMap<>();
        try (Scanner scanner = new Scanner(Paths.get("config.txt"))) {
            //save config to a hashmap, ignoring lines starting with //
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("//") || line.isEmpty()) continue;
                configs.put((line.split(" = ")[0]), (line.split(" = "))[1]);
            }
        } catch (Exception e) {
            Main.frame.throwError("could not find config.txt in the target directory", true);
        }
        token = configs.get("token");
        prefix = configs.get("prefix");
        logCommands = configs.get("logCommands").equals("true");
        Main.frame.logInit("successfully read configuration file");
    }
}
