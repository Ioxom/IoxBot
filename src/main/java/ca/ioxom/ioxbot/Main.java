package ca.ioxom.ioxbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static String TOKEN;
    public static final String VERSION = "0.1.0";
    public static IoxbotFrame frame;
    public static void main(String[] arguments) throws Exception {
        try (Scanner scanner = new Scanner(Paths.get("token.txt"))) {
            TOKEN = scanner.nextLine();
        } catch (Exception e) {
            System.out.println(e);
        }
        frame = new IoxbotFrame();
        frame.init();
        JDA api = JDABuilder.createDefault(TOKEN).build();
        api.addEventListener(new Commands.Listener());
        frame.log("initialized jda; ioxbot is ready to go");
    }
}
