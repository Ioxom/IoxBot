package ca.ioxom.ioxbot.frame;

import ca.ioxom.ioxbot.other.Config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class ReloadConfig implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        try (Scanner scanner = new Scanner(Paths.get("config.txt"))) {
            //save config to a hashmap, ignoring lines starting with //
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("//") || line.isEmpty()) continue;
                //throw error if a line is entered incorrectly
                try  {
                    String[] splitLine = line.split(" = ");
                    Config.configs.put(splitLine[0], splitLine[1]);
                } catch (Exception exception) {
                    Main.frame.throwError("error reading line \"" + line + "\" of config", true);
                }
            }
        //throw error if config.txt is not found
        } catch (IOException exception) {
            Main.frame.throwError("could not find config.txt in the target directory", true);
        }
        //set all the values to public variables
        Config.setValues();
        Main.frame.logMain("successfully reread configuration file");
    }
}
