package ca.ioxom.ioxbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.swing.JFrame;
import java.awt.*;

public class Main {
    public static final String TOKEN = "NzIyODM1MjkwNjQ0ODA3NzEx.Xuo20g.gPUYmyX7V8tEFCwPvhhpzcjb0U4";
    public static void main(String[] arguments) throws Exception {
        JDA api = JDABuilder.createDefault(TOKEN).build();
        api.addEventListener(new Commands.Listener());
        JFrame frame = new JFrame("ioxbot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(250, 100);
        frame.setVisible(true);
    }
}
