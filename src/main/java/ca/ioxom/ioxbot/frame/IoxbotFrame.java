package ca.ioxom.ioxbot.frame;

import ca.ioxom.ioxbot.Main;
import ca.ioxom.ioxbot.config.ReloadConfig;
import net.dv8tion.jda.api.entities.User;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

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
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("images/ioxbot_profile_photo.png");
            if (inputStream != null) {
                Image image = ImageIO.read(inputStream);
                this.frame.setIconImage(image);
                this.logInit("added icon to frame");
            } else {
                this.throwError("failed to add icon to frame; resources may be broken");
            }
        } catch (IOException e) {
            this.throwError("an IOException occurred while reading file \"images/ioxbot_profile_photo.png\"");
        }
        //configure the console, adding a scroll bar and setting the colour
        final Dimension consoleSize = new Dimension(500, 375);
        this.console.setBackground(Color.GRAY);
        this.console.setSize(consoleSize);
        this.console.setEditable(false);
        //create a pane to allow the console to have scrolling
        JScrollPane pane = new JScrollPane(this.console);
        pane.setPreferredSize(consoleSize);
        //configure the main background panel
        this.panel.setPreferredSize(consoleSize);
        this.panel.add(pane);
        this.frame.setContentPane(this.panel);
        this.logInit("added console to frame");
        //add a button for reloading the config
        JButton reloadConfig = new JButton();
        //get the icon
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("images/reload.png");
            if (inputStream != null) {
                ImageIcon reloadIcon = new ImageIcon(ImageIO.read(inputStream));
                reloadConfig.setIcon(reloadIcon);
            } else {
                this.throwError("failed to add icon to reload button; resources may be broken");
            }
        } catch (IOException e) {
            this.throwError("an IOException occurred while reading file \"images/reload.png\"");
        }
        reloadConfig.setPreferredSize(new Dimension(50, 50));
        reloadConfig.setBackground(Color.DARK_GRAY);
        reloadConfig.addActionListener(new ReloadConfig());
        this.panel.add(reloadConfig, BorderLayout.EAST);
        //open the frame
        this.frame.setSize(new Dimension(600, 275));
        this.frame.setVisible(true);
        this.logInit("finished initializing frame");
    }

    //methods for logging
    public void logInit(String message, boolean extra) {
        if (!extra || Main.config.extraLogging) {
            this.console.append("\n[init] " + message);
        }
    }

    public void logInit(String message) {
        this.console.append("\n[init] " + message);
    }

    public void logMain(String message) {
        this.console.append("\n[main] " + message);
    }

    public void logCommand(User user, String command, boolean containsUsed) {
        if (Main.config.logCommands) this.console.append("\n[cmd] " + user.getAsTag() + (containsUsed? " used " : " ")  + command);
    }

    public void throwError(String error, boolean fatal) {
        this.console.append((fatal?"\n[err/FATAL] " : "\n[err] ") + error + (fatal? "; closing ioxbot" : ""));
        if (fatal) {
            //wait for five seconds to allow for reading the error
            try {
                Thread.sleep(5000);
                System.exit(1);
            } catch (Exception e) {
                this.console.append("\n" + e.toString());
            }
        }
    }

    public void throwError(String error) {
        this.console.append("\n[err] " + error);
    }
}
