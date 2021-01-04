package ca.ioxom.ioxbot.config;

import ca.ioxom.ioxbot.frame.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReloadConfig implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent ae) {
        Main.config.configure();
    }
}