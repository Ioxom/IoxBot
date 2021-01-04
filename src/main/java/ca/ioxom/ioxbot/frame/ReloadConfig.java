package ca.ioxom.ioxbot.frame;

import ca.ioxom.ioxbot.other.ConfigObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_COMMENTS;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReloadConfig implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent ae) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(ALLOW_COMMENTS);
        try {
            ConfigObject readConfigObject = mapper.readValue(new File("config.json5"), ConfigObject.class);
            try {
                Main.config.extraLogging = readConfigObject.extraLogging;
            } catch (Exception e) {
                Main.frame.throwError("field \"extraLogging\" of config is missing or invalid; not reloading config");
                return;
            }
            try {
                Main.config.token = readConfigObject.token;
            } catch (Exception e) {
                Main.frame.throwError("field \"token\" of config is missing or invalid; not reloading config");
                return;
            }
            try {
                Main.config.logCommands = readConfigObject.logCommands;
            } catch (Exception e) {
                Main.frame.throwError("field \"logCommands\" of config is missing or invalid; not reloading config");
                return;
            }
            try {
                Main.config.prefix = readConfigObject.prefix;
            } catch (Exception e) {
                Main.frame.throwError("field \"prefix\" of config is missing or invalid; not reloading config");
                return;
            }
            try {
                Main.config.spaceAfterPrefix = readConfigObject.spaceAfterPrefix;
            } catch (Exception e) {
                Main.frame.throwError("field \"spaceAfterPrefix\" of config is missing or invalid; not reloading config");
                return;
            }
            try {
                Main.config.youtubeBlacklist = readConfigObject.youtubeBlacklist;
            } catch (Exception e) {
                Main.frame.throwError("field \"youtubeBlacklist\" of config is missing or invalid");
            }
            try {
                Main.config.embedColourString = readConfigObject.embedColourString;
                Main.config.embedColour = new Color(Integer.parseUnsignedInt(Main.config.embedColourString, 16));
            } catch (Exception e) {
                Main.frame.throwError("field \"embedColour\" of config is missing or invalid");
            }
            Main.config.formattedPrefix = Main.config.prefix + (Main.config.spaceAfterPrefix? " " : "");
        } catch (JsonParseException e) {
            Main.frame.throwError("config.json5 does not conform to json standard formatting; not reloading config");
            return;
        } catch (JsonMappingException e) {
            Main.frame.throwError("error mapping json values of config; not reloading config");
            return;
        } catch (FileNotFoundException e) {
            Main.frame.throwError("config.json5 not found in target directory; not reloading config");
            return;
        } catch (IOException e) {
            Main.frame.throwError("an IOException has occurred; not reloading config");
            return;
        }
        Main.frame.logMain("successfully reread configuration file and reloaded values");
    }
}
