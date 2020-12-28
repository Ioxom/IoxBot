package ca.ioxom.ioxbot.other;

import ca.ioxom.ioxbot.frame.Main;
import com.fasterxml.jackson.core.JsonParseException;
import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_COMMENTS;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ConfigObject {
    public boolean extraLogging;
    public String token;
    public boolean logCommands;
    public String prefix;
    public String formattedPrefix;
    public boolean spaceAfterPrefix;
    public void configure() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(ALLOW_COMMENTS);
        try {
            ConfigObject readConfigObject = mapper.readValue(new File("config.json5"), ConfigObject.class);
            this.setValues(readConfigObject);
        } catch (JsonParseException e) {
            Main.frame.throwError("config.json5 does not conform to json standard formatting", true);
        } catch (JsonMappingException e) {
            Main.frame.throwError("error mapping json values of config", true);
        } catch (FileNotFoundException e) {
            Main.frame.throwError("config.json5 not found in target directory", true);
        } catch (IOException e) {
            Main.frame.throwError("an IOException has occurred", true);
        }
        //set all the values to public variables
        Main.frame.logInit("successfully read configuration file", false);
    }

    public void setValues(ConfigObject readConfigObject) {
        try {
            this.extraLogging = readConfigObject.extraLogging;
        } catch (Exception e) {
            Main.frame.throwError("field \"extraLogging\" of config is missing or invalid", false);
        }
        try {
            this.token = readConfigObject.token;
        } catch (Exception e) {
            Main.frame.throwError("field \"token\" of config is missing or invalid", false);
        }
        try {
            this.logCommands = readConfigObject.logCommands;
        } catch (Exception e) {
            Main.frame.throwError("field \"logCommands\" of config is missing or invalid", false);
        }
        try {
            this.prefix = readConfigObject.prefix;
        } catch (Exception e) {
            Main.frame.throwError("field \"prefix\" of config is missing or invalid", false);
        }
        try {
            this.spaceAfterPrefix = readConfigObject.spaceAfterPrefix;
        } catch (Exception e) {
            Main.frame.throwError("field \"spaceAfterPrefix\" of config is missing or invalid", false);
        }
        this.formattedPrefix = this.prefix + (this.spaceAfterPrefix? " " : "");
    }
}
