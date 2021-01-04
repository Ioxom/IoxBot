package ca.ioxom.ioxbot.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.impl.SyntaxError;
import ca.ioxom.ioxbot.frame.Main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigObject {
    private boolean isFirstRun;
    public boolean extraLogging;
    public String token;
    public boolean logCommands;
    public String prefix;
    public String formattedPrefix;
    public boolean spaceAfterPrefix;
    public String[] youtubeBlacklist;
    public ConfigObject() {
        this.isFirstRun = true;
    }

    public void configure() {
        boolean successfullySetValues = false;
        try {
            ConfigObject readConfigObject = Jankson.builder().build().fromJson(Files.readString(Paths.get("config.json5")), ConfigObject.class);
            successfullySetValues = this.setValues(readConfigObject);
        } catch (FileNotFoundException e) {
            if (this.isFirstRun) {
                Main.frame.throwError("config.json5 not found in target directory", true);
            } else {
                Main.frame.throwError("config.json5 not found in target directory; not reloading config");
                return;
            }
        } catch (IOException e) {
            if (this.isFirstRun) {
                Main.frame.throwError("an IOException has occurred", true);
            } else {
                Main.frame.throwError("an IOException has occurred; not reloading config");
                return;
            }
        } catch (SyntaxError e) {
            if (this.isFirstRun) {
                Main.frame.throwError("json syntax error in config.json5: " + e.getLineMessage(), true);
            } else {
                Main.frame.throwError("json syntax error in config.json5: " + e.getLineMessage() + ";not reloading config");
            }
        }
        if (!successfullySetValues) {
            this.isFirstRun = false;
            return;
        }
        Main.frame.logInit(this.isFirstRun? "successfully read configuration file" : "successfully reread configuration file");
        if (this.isFirstRun) this.isFirstRun = false;
    }

    public boolean setValues(ConfigObject readConfigObject) {
        try {
            this.extraLogging = readConfigObject.extraLogging;
        } catch (Exception e) {
            if (this.isFirstRun) {
                Main.frame.throwError("field \"extraLogging\" of config is missing or invalid", true);
            } else {
                Main.frame.throwError("field \"extraLogging\" of config is missing or invalid; not reloading config");
                return false;
            }
        }
        try {
            this.token = readConfigObject.token;
        } catch (Exception e) {
            if (this.isFirstRun) {
                Main.frame.throwError("field \"token\" of config is missing or invalid", true);
            } else {
                Main.frame.throwError("field \"token\" of config is missing or invalid; not reloading config");
                return false;
            }
        }
        try {
            this.logCommands = readConfigObject.logCommands;
        } catch (Exception e) {
            if (this.isFirstRun) {
                Main.frame.throwError("field \"logCommands\" of config is missing or invalid", true);
            } else {
                Main.frame.throwError("field \"logCommands\" of config is missing or invalid; not reloading config");
                return false;
            }
        }

        try {
            this.prefix = readConfigObject.prefix;
        } catch (Exception e) {
            if (this.isFirstRun) {
                Main.frame.throwError("field \"prefix\" of config is missing or invalid", true);
            } else {
                Main.frame.throwError("field \"prefix\" of config is missing or invalid; not reloading config");
                return false;
            }
        }
        try {
            this.spaceAfterPrefix = readConfigObject.spaceAfterPrefix;
        } catch (Exception e) {
            if (this.isFirstRun) {
                Main.frame.throwError("field \"spaceAfterPrefix\" of config is missing or invalid", true);
            } else {
                Main.frame.throwError("field \"spaceAfterPrefix\" of config is missing or invalid; not reloading config");
                return false;
            }
        }

        this.formattedPrefix = this.prefix + (this.spaceAfterPrefix? " " : "");

        try {
           this.youtubeBlacklist = readConfigObject.youtubeBlacklist;
        } catch (Exception e) {
            if (this.isFirstRun) {
                Main.frame.throwError("field \"youtubeBlacklist\" of config is missing or invalid", true);
            } else {
                Main.frame.throwError("field \"youtubeBlacklist\" of config is missing or invalid; not reloading config");
                return false;
            }
        }

        return true;
    }
}