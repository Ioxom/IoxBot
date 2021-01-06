package ca.ioxom.ioxbot.config;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static ca.ioxom.ioxbot.Main.frame;
import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_COMMENTS;

public class ConfigObject {
    private boolean isFirstRun;
    public boolean extraLogging;
    public String token;
    public boolean logCommands;
    public String prefix;
    public String formattedPrefix;
    public boolean spaceAfterPrefix;
    public long[] youtubeBlacklist;
    public String embedColourString;
    public Color embedColour;
    public boolean randomEmbedColour;
    public long[] admins;
    public ConfigObject() {
        this.isFirstRun = true;
    }

    public void configure() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(ALLOW_COMMENTS);
        boolean successfullySetValues = false;
        try {
            ConfigObject readConfigObject = mapper.readValue(new File("config.json5"), ConfigObject.class);
            successfullySetValues = this.setValues(readConfigObject);
        } catch (JsonParseException e) {
            if (this.isFirstRun) {
                frame.throwError("config.json5 does not conform to json standard formatting", true);
            } else {
                frame.throwError("config.json5 does not conform to json standard formatting; not reloading config");
                return;
            }
        } catch (JsonMappingException e) {
            if (this.isFirstRun) {
                frame.throwError("error mapping json values of config", true);
            } else {
                frame.throwError("error mapping json values of config; not reloading config");
                return;
            }
        } catch (FileNotFoundException e) {
            if (this.isFirstRun) {
                frame.throwError("config.json5 not found in target directory", true);
            } else {
                frame.throwError("config.json5 not found in target directory; not reloading config");
                return;
            }
        } catch (IOException e) {
            if (this.isFirstRun) {
                frame.throwError("an IOException has occurred", true);
            } else {
                frame.throwError("an IOException has occurred; not reloading config");
                return;
            }
        }
        if (!successfullySetValues) return;
        frame.logInit(this.isFirstRun? "successfully read configuration file" : "successfully reread configuration file");
        if (this.isFirstRun) this.isFirstRun = false;
    }

    private boolean setValues(ConfigObject readConfigObject) {
        try {
            this.extraLogging = readConfigObject.extraLogging;
        } catch (Exception e) {
            if (this.isFirstRun) {
                frame.throwError("field \"extraLogging\" of config is missing or invalid", true);
            } else {
                frame.throwError("field \"extraLogging\" of config is missing or invalid; not reloading config");
                return false;
            }
        }
        try {
            this.token = readConfigObject.token;
        } catch (Exception e) {
            if (this.isFirstRun) {
                frame.throwError("field \"token\" of config is missing or invalid", true);
            } else {
                frame.throwError("field \"token\" of config is missing or invalid; not reloading config");
                return false;
            }
        }
        try {
            this.logCommands = readConfigObject.logCommands;
        } catch (Exception e) {
            if (this.isFirstRun) {
                frame.throwError("field \"logCommands\" of config is missing or invalid", true);
            } else {
                frame.throwError("field \"logCommands\" of config is missing or invalid; not reloading config");
                return false;
            }
        }

        try {
            this.prefix = readConfigObject.prefix;
        } catch (Exception e) {
            if (this.isFirstRun) {
                frame.throwError("field \"prefix\" of config is missing or invalid", true);
            } else {
                frame.throwError("field \"prefix\" of config is missing or invalid; not reloading config");
                return false;
            }
        }
        try {
            this.spaceAfterPrefix = readConfigObject.spaceAfterPrefix;
        } catch (Exception e) {
            if (this.isFirstRun) {
                frame.throwError("field \"spaceAfterPrefix\" of config is missing or invalid", true);
            } else {
                frame.throwError("field \"spaceAfterPrefix\" of config is missing or invalid; not reloading config");
                return false;
            }
        }

        this.formattedPrefix = this.prefix + (this.spaceAfterPrefix? " " : "");

        try {
           this.youtubeBlacklist = readConfigObject.youtubeBlacklist;
        } catch (Exception e) {
            if (this.isFirstRun) {
                frame.throwError("field \"youtubeBlacklist\" of config is missing or invalid", true);
            } else {
                frame.throwError("field \"youtubeBlacklist\" of config is missing or invalid; not reloading config");
                return false;
            }
        }
        try {
            this.embedColourString = readConfigObject.embedColourString;
            this.embedColour = new Color(Integer.parseUnsignedInt(this.embedColourString, 16));
        } catch (Exception e) {
            if (this.isFirstRun) {
                frame.throwError("field \"embedColourString\" of config is missing or invalid", true);
            } else {
                frame.throwError("field \"embedColourString\" of config is missing or invalid; not reloading config");
                return false;
            }
        }

        try {
            this.randomEmbedColour = readConfigObject.randomEmbedColour;
        } catch (Exception e) {
            if (this.isFirstRun) {
                frame.throwError("field \"randomEmbedColour\" of config is missing or invalid", true);
            } else {
                frame.throwError("field \"randomEmbedColour\" of config is missing or invalid; not reloading config");
                return false;
            }
        }

        try {
            this.admins = readConfigObject.admins;
        } catch (Exception e) {
            if (this.isFirstRun) {
                frame.throwError("field \"admins\" of config is missing or invalid", true);
            } else {
                frame.throwError("field \"admins\" of config is missing or invalid; not reloading config");
                return false;
            }
        }

        return true;
    }
}
