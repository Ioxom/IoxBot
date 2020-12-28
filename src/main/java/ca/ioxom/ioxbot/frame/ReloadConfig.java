package ca.ioxom.ioxbot.frame;

import ca.ioxom.ioxbot.other.Config;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReloadConfig implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent ae) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Config readConfig = mapper.readValue(new File("config.json5"), Config.class);
            try {
                Main.config.extraLogging = readConfig.extraLogging;
            } catch (Exception e) {
                Main.frame.throwError("field \"extraLogging\" of config is missing or invalid; not reloading config", false);
                return;
            }
            try {
                Main.config.token = readConfig.token;
            } catch (Exception e) {
                Main.frame.throwError("field \"token\" of config is missing or invalid; not reloading config", false);
                return;
            }
            try {
                Main.config.logCommands = readConfig.logCommands;
            } catch (Exception e) {
                Main.frame.throwError("field \"logCommands\" of config is missing or invalid; not reloading config", false);
                return;
            }
            try {
                Main.config.prefix = readConfig.prefix;
            } catch (Exception e) {
                Main.frame.throwError("field \"prefix\" of config is missing or invalid; not reloading config", false);
                return;
            }
            try {
                Main.config.spaceAfterPrefix = readConfig.spaceAfterPrefix;
            } catch (Exception e) {
                Main.frame.throwError("field \"spaceAfterPrefix\" of config is missing or invalid; not reloading config", false);
                return;
            }
            Main.config.formattedPrefix = Main.config.prefix + (Main.config.spaceAfterPrefix? " " : "");
        } catch (JsonParseException e) {
            Main.frame.throwError("config.json5 does not conform to json standard formatting; not reloading config", false);
            return;
        } catch (JsonMappingException e) {
            Main.frame.throwError("error mapping json values of config; not reloading config", false);
            return;
        } catch (FileNotFoundException e) {
            Main.frame.throwError("config.json5 not found in target directory; not reloading config", false);
            return;
        } catch (IOException e) {
            Main.frame.throwError("an IOException has occurred; not reloading config", false);
            return;
        }
        Main.frame.logMain("successfully reread configuration file and reloaded values");
    }
}
