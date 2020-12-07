package ca.ioxom.ioxbot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.Color;

public class IoxbotFrame {
    public JTextArea console;
    public JFrame frame;
    public IoxbotFrame() {
        this.frame = new JFrame("ioxbot v " + Main.VERSION);
        this.console = new JTextArea("loading ioxbot");
    }

    public void init() {
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setBackground(Color.GRAY);
        this.console.setBackground(Color.GRAY);
        this.console.setEditable(false);
        this.frame.add(this.console);
        this.frame.setSize(500, 250);
        this.frame.setVisible(true);
        this.log("initialized frame");
    }

    public void log(String message) {
        this.console.append("\n" + message);
    }

    public void logCommand(String command, MessageReceivedEvent event) {
        this.console.append("\n" + event.getAuthor().getAsTag() + " used " + command);
    }
}
