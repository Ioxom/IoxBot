package ca.ioxom.ioxbot.config;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static ca.ioxom.ioxbot.Main.config;
import static ca.ioxom.ioxbot.Main.frame;
import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_COMMENTS;

public class ConfigObject {
    private boolean isFirstRun;
    public String prefix;
    public boolean extraLogging;
    public boolean logCommands;
    public String embedColour;
    public boolean randomEmbedColour;
    public ArrayList<Long> youtubeBlacklist;
    public ArrayList<Long> admins;
    public ConfigObject() {
        this.isFirstRun = true;
    }

    public void configure() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(ALLOW_COMMENTS);
        boolean successfullySetValues = false;
        try {
            ConfigObject readConfigObject = mapper.readValue(new File("config.json"), ConfigObject.class);
            successfullySetValues = this.setValues(readConfigObject);
        } catch (JsonParseException e) {
            if (this.isFirstRun) {
                frame.throwError("config.json does not conform to json standard formatting", true);
            } else {
                frame.throwError("config.json does not conform to json standard formatting; not reloading config");
                return;
            }
        } catch (JsonMappingException e) {
            if (this.isFirstRun) {
                e.printStackTrace();
                frame.throwError("error mapping json values of config", true);
            } else {
                frame.throwError("error mapping json values of config; not reloading config");
                return;
            }
        } catch (FileNotFoundException e) {
            if (this.isFirstRun) {
                frame.throwError("config.json not found in target directory", true);
            } else {
                frame.throwError("config.json not found in target directory; not reloading config");
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
            this.embedColour = readConfigObject.embedColour;
        } catch (Exception e) {
            if (this.isFirstRun) {
                frame.throwError("field \"embedColour\" of config is missing or invalid", true);
            } else {
                frame.throwError("field \"embedColour\" of config is missing or invalid; not reloading config");
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

    public String toString() {
        StringBuilder admins = new StringBuilder();
        if (this.admins.isEmpty()) {
            admins.append("none");
        } else {
            for (int i = 0; i < this.admins.size(); i ++) {
                if (i != this.admins.size() - 1) {
                    admins.append(this.admins.get(i)).append(", ");
                } else {
                    admins.append(this.admins.get(i));
                }
            }
        }

        StringBuilder youtubeBlacklist = new StringBuilder();
        if (this.youtubeBlacklist.isEmpty()) {
            youtubeBlacklist.append("none");
        } else {
            for (int i = 0; i < this.youtubeBlacklist.size(); i ++) {
                if (i != this.youtubeBlacklist.size() - 1) {
                    youtubeBlacklist.append(this.youtubeBlacklist.get(i)).append(", ");
                } else {
                    youtubeBlacklist.append(this.youtubeBlacklist.get(i));
                }
            }
        }
        return  "prefix: `" + this.prefix + "`\n" +
                "embed colour: `" + this.embedColour + "`\n" +
                "random embed colour enabled?: `" + this.randomEmbedColour + "`\n" +
                "admins: `" + admins + "`\n" +
                "youtube blacklist: `" + youtubeBlacklist + "`\n" +
                "extra logging enabled?: `" + this.extraLogging + "`\n" +
                "command logging enabled?: `" + this.logCommands + "`";
    }

    public void writeCurrentConfig() {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File("config.json"), this);
            frame.logMain("wrote current configuration to config.json");
            this.configure();
        } catch (IOException e) {
            frame.throwError("failed to write to config");
        }
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void addAdmin(long id) {
        this.admins.add(id);
    }

    public void removeAdmin(long id) {
        this.admins.remove(id);
    }

    public void clearAdmins() {
        this.admins = new ArrayList<>();
    }

    public void addToYoutubeBlacklist(long id) {
        this.youtubeBlacklist.add(id);
    }

    public void removeFromYoutubeBlacklist(long id) {
        this.youtubeBlacklist.remove(id);
    }

    public void clearYoutubeBlacklist() {
        this.youtubeBlacklist = new ArrayList<>();
    }

    public void setRandomEmbedColour(boolean b) {
        this.randomEmbedColour = b;
    }

    public void setEmbedColour(String hex) {
        this.embedColour = hex;
        this.randomEmbedColour = false;
    }

    public void setExtraLogging(boolean b) {
        this.extraLogging = b;
    }

    public void setLogCommands(boolean b) {
        this.extraLogging = b;
    }

    public Color getEmbedColour() {
        if (this.randomEmbedColour) {
            return new Color(Integer.parseUnsignedInt(new Object().toString().split("Object@")[1], 16));
        } else {
            return new Color(Integer.parseUnsignedInt(this.embedColour, 16));
        }
    }
}