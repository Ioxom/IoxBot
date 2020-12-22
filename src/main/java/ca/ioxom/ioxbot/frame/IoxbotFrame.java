package ca.ioxom.ioxbot.frame;

import ca.ioxom.ioxbot.other.Config;
import net.dv8tion.jda.api.entities.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class IoxbotFrame {
    private final JTextArea console;
    private final JFrame frame;
    private final JPanel panel;
    public IoxbotFrame() {
        this.frame = new JFrame("ioxbot v " + Main.VERSION);
        this.console = new JTextArea("[init] loading ioxbot");
        this.panel = new JPanel(new BorderLayout());
    }

    public void init() {
        this.frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //set icon
        try {
            Image image = ImageIO.read(new URL("https://user-images.githubusercontent.com/66223394/102388605-9ce81880-3f97-11eb-85d4-d4841103d47b.png"));
            this.frame.setIconImage(image);
            this.logInit("added icon to frame", false);
        } catch (IOException e) {
            this.throwError("could not find icon, defaulting to java icon", false);
        }
        //configure the console, adding a scroll bar and setting the colour
        this.console.setBackground(Color.GRAY);
        this.console.setSize(new Dimension(500, 375));
        this.console.setEditable(false);
        JScrollPane pane = new JScrollPane();
        pane.add(this.console);
        pane.setPreferredSize(new Dimension(500, 375));
        this.panel.setBackground(new Color(0x00FF00));
        this.panel.setPreferredSize(new Dimension(500, 375));
        this.panel.add(pane);
        this.frame.setContentPane(this.panel);
        this.logInit("added console to frame", false);
        //open the frame
        this.frame.setSize(new Dimension(600, 275));
        this.frame.setVisible(true);
        this.logInit("finished initializing frame", false);
    }

    //methods for logging
    public void logInit(String message, boolean extra) {
        if (!extra || Config.extraLogging) {
            this.console.append("\n[init] " + message);
        }
    }

    public void logCommand(User user, String command, boolean containsUsed) {
        if (Config.logCommands) this.console.append("\n[command] " + user.getAsTag() + (containsUsed? " used " : " ")  + command);
    }

    public void throwError(String error, boolean fatal) {
        this.console.append((fatal?"\n[err/FATAL] " : "\n[err] ") + error + (fatal? ";closing ioxbot" : ""));
        if (fatal) {
            try {
                Thread.sleep(5000);
                System.exit(2);
            } catch (Exception e) {
                this.console.append("\n" + e.toString());
            }
        }
    }
}
