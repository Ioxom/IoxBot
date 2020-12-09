package ca.ioxom.ioxbot;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class Config {
    public static boolean logCommands;
    public static String prefix;
    public static void configure() {
        HashMap<String, String> configs = new HashMap<>();
        try (Scanner scanner = new Scanner(Paths.get("config.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("//")) continue;
                configs.put((line.split(" = ")[0]), (line.split(" = "))[1]);
            }
        } catch (Exception e) {
            Main.frame.log("[err] could not find config.txt in the target directory; closing ioxbot");
            try {
                Thread.sleep(5000);
                System.exit(2);
            } catch (Exception f) {
                Main.frame.log(f.toString());
            }
        }
        prefix = configs.get("prefix");
        logCommands = configs.get("logCommands").equals("true");
        Main.frame.log("[init] successfully read configuration file");
    }
}
