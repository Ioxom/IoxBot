package ca.ioxom.ioxbot.other;

import ca.ioxom.ioxbot.frame.Main;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class Config {
    public boolean extraLogging;
    public String token;
    public boolean logCommands;
    public String prefix;
    public String formattedPrefix;
    public boolean spaceAfterPrefix;
    public static final HashMap<String, String> configs = new HashMap<>();
    public void configure() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Config readConfig = mapper.readValue(new File("config.json5"), Config.class);
            this.setValues(readConfig);
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

    public void setValues(Config readConfig) {
        try {
            this.extraLogging = readConfig.extraLogging;
        } catch (Exception e) {
            Main.frame.throwError("field \"extraLogging\" of config is missing or invalid", false);
        }
        try {
            this.token = readConfig.token;
        } catch (Exception e) {
            Main.frame.throwError("field \"token\" of config is missing or invalid", false);
        }
        try {
            this.logCommands = readConfig.logCommands;
        } catch (Exception e) {
            Main.frame.throwError("field \"logCommands\" of config is missing or invalid", false);
        }
        try {
            this.prefix = readConfig.prefix;
        } catch (Exception e) {
            Main.frame.throwError("field \"prefix\" of config is missing or invalid", false);
        }
        try {
            this.spaceAfterPrefix = readConfig.spaceAfterPrefix;
        } catch (Exception e) {
            Main.frame.throwError("field \"spaceAfterPrefix\" of config is missing or invalid", false);
        }
        this.formattedPrefix = this.prefix + (this.spaceAfterPrefix? " " : "");
    }
}
