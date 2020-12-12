package ca.ioxom.ioxbot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

public class IoxbotFrame {
    private final JTextArea console;
    private final JFrame frame;
    public IoxbotFrame() {
        this.frame = new JFrame("ioxbot v " + Main.VERSION);
        this.console = new JTextArea("[init] loading ioxbot");
    }

    public void init() {
        this.frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //set icon
        Image image = null;
        try {
            image = ImageIO.read(new URL("https://cdn.discordapp.com/attachments/728781398911221795/786384261821104158/ioxbot_profile_photo.png"));
        } catch (IOException e) {
            this.throwError("could not find icon, defaulting to java icon", false);
        }
        if (image != null) this.frame.setIconImage(image);
        //configure the console
        this.console.setBackground(Color.GRAY);
        this.console.setEditable(false);
        this.frame.add(this.console);
        //open the frame
        this.frame.setSize(500, 250);
        this.frame.setVisible(true);
        this.logInit("initialized frame");
    }

    public void logInit(String message) {
        this.console.append("\n[init] " + message);
    }

    public void logCommand(String command, MessageReceivedEvent event) {
        if (Config.logCommands) this.console.append("\n[command] " + event.getAuthor().getAsTag() + " used " + command);
    }

    public void throwError(String error, boolean fatal) {
        this.console.append((fatal?"\n[err/FATAL] " : "\n[err] ") + error + (fatal? ";closing ioxbot" : ""));
        if (fatal) {
            try {
                Thread.sleep(5000);
                System.exit(2);
            } catch (Exception f) {
                this.console.append("\n" + f.toString());
            }
        }
    }
}
