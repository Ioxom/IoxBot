package ca.ioxom.ioxbot.config;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

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
    public ArrayList<Long> youtubeBlacklist;
    public String embedColourString;
    public Color embedColour;
    public boolean randomEmbedColour;
    public ArrayList<Long> admins;
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
            this.token = Files.readAllLines(Paths.get("token.txt")).get(0);
        } catch (FileNotFoundException e) {
            if (this.isFirstRun) {
                frame.throwError("token.txt not found", true);
            } else {
                frame.throwError("token.txt not found; using previously saved token");
            }
        } catch (IOException e) {
            if (this.isFirstRun) {
                frame.throwError("an IOException occurred when reading file: token.txt", true);
            } else {
                frame.throwError("an IOException occurred when reading file: token.txt; using previously saved token");
            }
        }

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

    public void writeCurrentConfig() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("config.json5"), this);
            frame.logMain("wrote current configuration to config.json5");
        } catch (IOException e) {
            frame.throwError("failed to write to config");
        }
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix.split(" ")[0];
        this.spaceAfterPrefix = prefix.contains(" ");
        this.formattedPrefix = prefix;
    }

    public void addAdmin(long id) {
        this.admins.add(id);
    }

    public void removeAdmin(long id) {
        this.admins.remove(id);
    }

    public void addToYoutubeBlacklist(long id) {
        this.youtubeBlacklist.add(id);
    }

    public void removeFromYoutubeBlacklist(long id) {
        this.youtubeBlacklist.remove(id);
    }

    public void setRandomEmbedColour(boolean b) {
        this.randomEmbedColour = b;
    }

    public void setEmbedColour(String hex) {
        this.embedColour = new Color(Integer.parseUnsignedInt(hex, 16));
        this.randomEmbedColour = false;
    }
}
